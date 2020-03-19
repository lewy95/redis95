package com.xzxy.lewy.redis95.service;

import com.xzxy.lewy.redis95.pojo.Player;

import java.util.List;

/**
 * @author lewy
 */
public interface PlayerService {

    Player save(Player player);

    Player findById(Integer id);

    void delete(Player player);

    List<Player> findAll();
}
