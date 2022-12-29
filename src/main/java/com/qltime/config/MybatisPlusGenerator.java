package com.qltime.config;

/**
 * @Auther: liweijian
 * @Date: 2019/10/9 13:14
 * @Description:
 */
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {
    private static MybatisPlusGenerator single = null;

    private MybatisPlusGenerator() {
        super();
    }

    private static MybatisPlusGenerator getSingle() {
        if(single == null) {
            single =new MybatisPlusGenerator();
        }
        return single;
    }

    public void autoGeneration() {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://39.106.156.28:3306/qltime?serverTimezone=GMT%2B8";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude("tb_weight");
        config.setActiveRecord(false)
                .setEnableCache(false)
                .setAuthor("liweijian")
                //指定输出文件夹位置
                .setOutputDir("F:\\Code\\generator")
                .setFileOverride(true)
                .setServiceName("%sService");
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent("com.qltime")
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MybatisPlusGenerator generator = MybatisPlusGenerator.getSingle();
        generator.autoGeneration();
    }

}
