package com.xzxy.lewy.redis95.service;

import com.xzxy.lewy.redis95.pojo.Player;

import java.util.List;

/**
 * @author lewy
 */
public interface PlayerService {

    Player save(Player player);

    Player update(Player player);

    Player findById(Integer id);

    Player findByIdAnnotation(Integer id);

    void delete(Player player);

    List<Player> findAll();
}
