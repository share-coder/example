package com.jcl.example.repository;

import com.jcl.example.entities.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends CrudRepository<Example, Long> {
}
