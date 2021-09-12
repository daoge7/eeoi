package cn.ccsit.eeoi.ship.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.ship.entity.GcClientCate;


@Repository("gcClientCateRepository")
public interface GcClientCateRepository extends PagingAndSortingRepository<GcClientCate,String> {

}
