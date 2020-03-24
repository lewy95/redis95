package com.xzxy.lewy.redis95.service.impl;

import com.xzxy.lewy.redis95.dao.PlayerDao;
import com.xzxy.lewy.redis95.pojo.Player;
import com.xzxy.lewy.redis95.service.PlayerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @author lewy
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Resource
    PlayerDao playerDao;

    @Override
    public Player save(Player player) {
        return playerDao.save(player);
    }

    /**
     * 非注解形式
     */
    @Override
    public Player findById(Integer id) {
        return playerDao.findById(id);
    }

    /**
     * 基于注解形式
     */
    @Override
    //@Cacheable(value = "user_details", key = "#id", unless="#result == null")
    //@Cacheable
    public Player findByIdAnnotation(Integer id) {
        return playerDao.findById(id);
    }

    @Override
    public void delete(Player player) {
        playerDao.delete(player);
    }

    @Override
    public List<Player> findAll() {
        return playerDao.findAll();
    }
}
