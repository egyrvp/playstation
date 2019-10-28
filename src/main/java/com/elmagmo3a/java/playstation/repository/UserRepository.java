package com.elmagmo3a.java.playstation.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.elmagmo3a.java.playstation.entity.User;



@Repository
public interface UserRepository
		extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	
}
