package com.elmagmo3a.java.playstation.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.elmagmo3a.java.playstation.entity.Store;

@Repository
public interface StoreRepository extends PagingAndSortingRepository<Store, Long>, JpaSpecificationExecutor<Store> {

}
