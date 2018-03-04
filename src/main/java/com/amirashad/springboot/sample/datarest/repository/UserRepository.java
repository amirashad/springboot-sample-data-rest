package com.amirashad.springboot.sample.datarest.repository;

import com.amirashad.springboot.sample.datarest.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

}
