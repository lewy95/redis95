package com.xzxy.lewy.redis95.dao;

import com.xzxy.lewy.redis95.pojo.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlayerDao extends PagingAndSortingRepository<Player, Long>, JpaSpecificationExecutor<Player>, JpaRepository<Player, Long> {
    Player findById(String id);
}
