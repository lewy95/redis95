package com.xzxy.lewy.redis95.controller;

import com.xzxy.lewy.redis95.common.controller.BaseController;
import com.xzxy.lewy.redis95.common.util.RedisConstants;
import com.xzxy.lewy.redis95.common.util.RedisUtil;
import com.xzxy.lewy.redis95.common.util.StateParameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/redis")
public class RedisController extends BaseController {

    @Resource
    RedisUtil redisUtil;

    @RequestMapping(value = "getRedis", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getRedis() {
        redisUtil.set("test:01", "这是一条测试数据", RedisConstants.datebase1);
        Long resExpire = redisUtil.expire("test:01", 60, RedisConstants.datebase1);//设置key过期时间
        logger.info("resExpire = " + resExpire);
        String res = redisUtil.get("test:01", RedisConstants.datebase1);
        System.out.println(res);

//        //测试Redis保存对象
//        Player player = new Player();
//        player.setName("lucas");
//        player.setAge(24);
//        player.setNumber(21);
//        //写入
//        redisUtil.set("player:21".getBytes(), SerializeUtil.serialize(player), RedisConstants.datebase1);
//        //读出
//        byte[] p = redisUtil.get("player:21".getBytes(), RedisConstants.datebase1);
//        //反序列化
//        Player newP = (Player) SerializeUtil.unSerialize(p);
//        System.out.println("player=" + newP.toString());

        return getModelMap(StateParameter.SUCCESS, res, "执行成功");
    }

    @RequestMapping(value = "readRedis", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getFromRedis() {
        Jedis jedis = new Jedis("192.168.56.91",6379);
        //jedis.auth("123456");
        jedis.set("ooo","fyy");
        String res = jedis.get("ooo");
        jedis.close();

        return getModelMap(StateParameter.SUCCESS, res, "执行成功");
    }


}
