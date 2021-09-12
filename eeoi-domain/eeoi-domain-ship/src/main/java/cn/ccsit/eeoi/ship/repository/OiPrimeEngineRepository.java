package cn.ccsit.eeoi.ship.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import cn.ccsit.eeoi.ship.entity.OiPrimeEngine;


@Repository("oiPrimeEngineRepository")
public interface OiPrimeEngineRepository extends PagingAndSortingRepository<OiPrimeEngine,String> {

}
