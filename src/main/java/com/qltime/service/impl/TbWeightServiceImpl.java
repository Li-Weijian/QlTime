package com.qltime.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qltime.dto.CharNode;
import com.qltime.entity.TbWeight;
import com.qltime.mapper.TbWeightMapper;
import com.qltime.service.TbUserService;
import com.qltime.service.TbWeightService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.qltime.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liweijian123
 * @since 2020-05-22
 */
@Service
public class TbWeightServiceImpl extends ServiceImpl<TbWeightMapper, TbWeight> implements TbWeightService {

    @Autowired
    private TbWeightMapper weightMapper;

    @Autowired
    private TbUserService userService;

    @Override
    public List<CharNode> getChart(String day) {
        List<TbWeight> weightList = this.getWeightList(day);
        Date date = DateUtil.offsetDay(new Date(), -Integer.parseInt(day));
        weightList = completionNode(weightList, date, new Date());
        List<CharNode> charNodeList = tranCharNode(weightList);

        return charNodeList;
    }

    @Override
    public List<CharNode> tranCharNode(List<TbWeight> weightList){

        List<CharNode> charNodeList = new ArrayList<>();
        for (TbWeight weight : weightList) {
            CharNode charNode = new CharNode();
            charNode.setDate(DateUtil.formatDate(weight.getCreated()));
            charNode.setValue(weight.getWeight());
            charNode.setType(userService.selectUserName(weight.getUserId()));
            charNodeList.add(charNode);
        }
        return charNodeList;
    }
    @Override
    public void addWeight(Double weight, Integer loginUserId) {
        List<TbWeight> existList = weightMapper.selectList(new EntityWrapper<TbWeight>().eq("created", DateUtil.today())
                .eq("userId",new RequestUtils().getLoginUserId()).eq("isDelete","0"));

        //删除已存在的
        if (existList.size() > 0){
            for (TbWeight w:existList) {
                w.setIsDelete("1");
                weightMapper.updateById(w);
            }
        }

        TbWeight obj = new TbWeight();
        obj.setUserId(loginUserId);
        obj.setWeight(weight);

        //插入最新的
        weightMapper.insert(obj);
    }

    @Override
    public List<TbWeight> getWeightList(String day) {
        Date date = DateUtil.offsetDay(new Date(), -Integer.parseInt(day));
        List<TbWeight> weightList = weightMapper.selectList(new EntityWrapper<TbWeight>().eq("isDelete", "0")
                .between("created", date, new Date()).orderBy("created", false));
        return weightList;
    }


    /**
     * 补全日期
     * @param nodeList
     * @param begin
     * @param end
     * @return
     */
    private List<TbWeight> completionNode(List<TbWeight> nodeList, Date begin, Date end){
        long between = DateUtil.between(begin, end, DateUnit.DAY);

        for (int i = 1; i <= between; i++) {
            Date currDate = DateUtil.offsetDay(begin, i);
            for (int j = 1; j <=2 ; j++){
                //从lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量
                int finalJ = j;
                //对比用户和当前遍历的时间是否存在数据，不存在则添加一条
                if (nodeList.stream().filter(d -> (DateUtil.format(currDate, "yyyy-MM-dd").equals(DateUtil.format(d.getCreated(), "yyyy-MM-dd"))
                        && d.getUserId().equals(finalJ))).collect(Collectors.toList()).size() == 0){
                    TbWeight weight = new TbWeight();
                    weight.setCreated(currDate);
                    weight.setId(null);
                    weight.setUserId(finalJ);
                    weight.setWeight(0.0);
                    nodeList.add(weight);
                }
            }
        }

        //排序
        return nodeList.stream().sorted(Comparator.comparing(TbWeight::getCreated)).collect(Collectors.toList());
    }
}
