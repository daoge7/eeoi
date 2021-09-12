package cn.ccsit.eeoi.ship.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.ship.entity.GcClientCateRela;


@Repository("gcClientCateRelaRepository")
public interface GcClientCateRelaRepository extends PagingAndSortingRepository<GcClientCateRela,String> {

}
