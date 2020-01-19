package com.dziedzic.warehouse.repository;


import com.dziedzic.warehouse.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByGuid(String guid);
}
