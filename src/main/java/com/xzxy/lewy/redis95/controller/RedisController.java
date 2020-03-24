package com.xzxy.lewy.redis95.controller;

import com.xzxy.lewy.redis95.common.controller.BaseController;
import com.xzxy.lewy.redis95.common.util.RedisConstants;
import com.xzxy.lewy.redis95.common.util.RedisUtil;
import com.xzxy.lewy.redis95.common.util.StateParameter;
import com.xzxy.lewy.redis95.pojo.Player;
import com.xzxy.lewy.redis95.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 测试类：基于对RedisTemplate封装的RedisUtil操作
 * @author lewy
 */
@Controller
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    @Resource
    RedisUtil redisUtil;

    @Resource
    PlayerService playerService;

    /**
     * 测试redis存储&读取
     */
    @RequestMapping(value = "/test")
    @ResponseBody
    public ModelMap test() {
        try {
            redisUtil.set("redisTemplate", "这是一条测试数据", RedisConstants.DATEBASE2);
            String value = redisUtil.get("redisTemplate", RedisConstants.DATEBASE2).toString();
            logger.info("redisValue=" + value);
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, value, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    /**
     * 测试redis存储&读取，保存对象
     */
    @RequestMapping(value = "/setPlayer")
    @ResponseBody
    public ModelMap setPlayer() {
        try {
            Player player = new Player();
            player.setId(1);
            player.setName("lewy");
            player.setAge(28);
            player.setNumber(9);
            redisUtil.set("player:lewy", player, RedisConstants.DATEBASE1);
            redisUtil.expire("player:lewy", 300);
            // 读取一个对象
            Player res = (Player) redisUtil.get("player:lewy", RedisConstants.DATEBASE1);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    /**
     * 测试redis存储&读取，从数据库中读取对象并保存对象
     */
    @GetMapping(value = "/setPlayer/{id}")
    @ResponseBody
    public ModelMap setPlayerFromDb(@PathVariable Integer id) {
        try {
            Player player = playerService.findById(id);
            String key = "player-" + id;
            redisUtil.set(key, player, RedisConstants.DATEBASE1);
            redisUtil.expire(key, 300);
            // 读取一个对象
            Player res = (Player) redisUtil.get(key, RedisConstants.DATEBASE1);
            logger.info("res=" + res.toString());
            logger.info("读取redis成功");
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

}
