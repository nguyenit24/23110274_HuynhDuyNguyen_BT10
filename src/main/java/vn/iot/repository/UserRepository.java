package vn.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.iot.enity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}

