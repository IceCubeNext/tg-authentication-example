package ru.nextcloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.nextcloud.model.User;

import java.util.Map;

public interface UserService {
    User addUser(Map<String, String> initData) throws JsonProcessingException;
}
