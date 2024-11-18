package com.nt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nt.model.Notify;

public interface INotificationRepo extends CrudRepository<Notify, Integer>{
	 // Custom query to find records where productId and userId match
    @Query("SELECT n FROM Notify n WHERE n.productId = :productId AND n.userId = :userId")
    List<Notify> findNotificationsByProductAndUser(@Param("productId") Integer productId, @Param("userId") Integer userId);
}
