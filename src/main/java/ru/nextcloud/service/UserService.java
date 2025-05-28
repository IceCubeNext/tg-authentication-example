package ru.nextcloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.nextcloud.model.User;

public interface UserService {
    User addUser(String user) throws JsonProcessingException;
}
