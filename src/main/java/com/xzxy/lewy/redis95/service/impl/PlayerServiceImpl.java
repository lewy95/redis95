package com.xzxy.lewy.redis95.service.impl;

import com.xzxy.lewy.redis95.dao.PlayerDao;
import com.xzxy.lewy.redis95.pojo.Player;
import com.xzxy.lewy.redis95.service.PlayerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Resource
    PlayerDao playerDao;

    @Override
    public Player save(Player player) {
        return playerDao.save(player);
    }

    @Override
    public Player findById(String id) {
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
