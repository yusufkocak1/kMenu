package com.kocak.kmenuserver.repository;

import com.kocak.kmenuserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
