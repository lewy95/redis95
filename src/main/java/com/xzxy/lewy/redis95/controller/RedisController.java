package com.xzxy.lewy.redis95.controller;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import com.xzxy.lewy.redis95.common.controller.BaseController;
import com.xzxy.lewy.redis95.common.util.RedisConstants;
import com.xzxy.lewy.redis95.common.util.RedisUtil;
import com.xzxy.lewy.redis95.common.util.StateParameter;
import com.xzxy.lewy.redis95.pojo.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    @Resource
    RedisUtil redisUtil;

    /**
     * 测试redis存储&读取
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ModelMap test() {
        try {
            redisUtil.set("redisTemplate", "这是一条测试数据", RedisConstants.datebase2);
            String value = redisUtil.get("redisTemplate", RedisConstants.datebase2).toString();
            logger.info("redisValue=" + value);
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, value, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    @RequestMapping(value = "/setPlayer")
    @ResponseBody
    public ModelMap setUser() {
        try {
            Player player = new Player();
            player.setId(1);
            player.setName("lewy");
            player.setAge(28);
            player.setNumber(9);
            redisUtil.set("player:lewy", player, RedisConstants.datebase1);
            Player res = (Player) redisUtil.get("player:lewy", RedisConstants.datebase1);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

}
