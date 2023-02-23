package com.example.demo.repository;

import com.example.demo.entity.verifySms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifySmsRepository extends CrudRepository<verifySms, String> {
}
