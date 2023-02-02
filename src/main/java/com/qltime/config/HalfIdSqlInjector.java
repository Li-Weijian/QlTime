package com.qltime.config;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.qltime.utils.DataScopeHelper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * 添加另一半用户ID SQL注入器
 * @author liweijian
 * @date 2023/2/1 19:26
 */
@Component
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class HalfIdSqlInjector implements Interceptor {

    private final CCJSqlParserManager parserManager = new CCJSqlParserManager();


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Integer halfId = DataScopeHelper.getDataScope();
        if (null == halfId){
            // 无另一半Id标识，跳过SQL增强
            return invocation.proceed();
        }

        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // 先判断是不是SELECT操作 不是直接过滤
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return invocation.proceed();
        }

        appendHalfId(metaObject, halfId);
        return invocation.proceed();
    }

    /**
     * 生成拦截对象的代理
     *
     * @param target 目标对象
     * @return 代理对象
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * mybatis配置的属性
     *
     * @param properties mybatis配置的属性
     */
    @Override
    public void setProperties(Properties properties) {
    }

    private void appendHalfId(MetaObject metaObject, Integer half) {
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        // 执行的SQL语句
        String originalSql = boundSql.getSql();
        log.info("原始SQL: {}", originalSql);

        try {
            // 增强SQL
            Select select = (Select) parserManager.parse(new StringReader(originalSql));
            SelectBody selectBody = select.getSelectBody();

            if (selectBody instanceof PlainSelect) {
                this.setWhere((PlainSelect) selectBody, half);
            } else if (selectBody instanceof SetOperationList) {
                SetOperationList setOperationList = (SetOperationList) selectBody;
                List<SelectBody> selectBodyList = setOperationList.getSelects();
                for (SelectBody body : selectBodyList) {
                    this.setWhere((PlainSelect) body, half);
                }
            }

            String dataScopeSelect = select.toString();
            log.info("增强后SQL: {}", dataScopeSelect);
            metaObject.setValue("delegate.boundSql.sql", dataScopeSelect);
            DataScopeHelper.cleanDataScopes();
        } catch (JSQLParserException e) {
            log.error("[增强SQL] 出现异常, SQL: {}", originalSql, e);
        }

    }

    private void setWhere(PlainSelect plainSelect, Integer half) throws JSQLParserException {
        if (null == half){
            return;
        }

        Expression where = plainSelect.getWhere();
        if (where.toString().contains("userId = ?")){
            String replaceSql = where.toString().replaceAll("userId = [?]", "(userId = ? OR userId = " + half + ")");
            plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression(replaceSql));
        }

    }

}
