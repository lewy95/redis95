package com.xzxy.lewy.redis95.controller;

import com.xzxy.lewy.redis95.common.controller.BaseController;
import com.xzxy.lewy.redis95.common.util.RedisConstants;
import com.xzxy.lewy.redis95.common.util.StateParameter;
import com.xzxy.lewy.redis95.pojo.Player;
import com.xzxy.lewy.redis95.service.PlayerService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 测试类：Redis整合Spring Data Cache注解 @Cacheable / @CachePut / @CacheEvict
 *        一般标注在Service类中方法商
 * @author lewy
 */
public class RedisAnnotationController extends BaseController {

    @Resource
    PlayerService playerService;

    @GetMapping(value = "/setPlayer/{id}")
    @ResponseBody
    public ModelMap setPlayerFromDb(@PathVariable Integer id) {
        try {
            Player player = playerService.findById(id);
            return getModelMap(StateParameter.SUCCESS, player, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }
}
