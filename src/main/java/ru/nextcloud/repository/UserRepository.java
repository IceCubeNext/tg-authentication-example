package ru.nextcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nextcloud.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}