package ru.nextcloud.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nextcloud.model.User;
import ru.nextcloud.repository.UserRepository;
import ru.nextcloud.service.UserService;
import ru.nextcloud.utils.TelegramAuthVerifier;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${telegram.bot-token}")
    private String botToken;
    private final UserRepository repository;

    @Override
    @Transactional
    public User addUser(Map<String, String> initData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = parseInitData(initData.get("initData"));

        if (!TelegramAuthVerifier.isValid(data, botToken)) {
            log.info("invalid initData");
            return new User(0L, "нет данных", "нет данных", "нет данных");
        }

        String userJson = data.get("user");
        User user = mapper.readValue(userJson, User.class);

        if (repository.findById(user.getId()).isPresent()) {
            User oldUser = repository.findById(user.getId()).get();
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setUsername(user.getUsername());
            return repository.save(oldUser);
        }

        return repository.save(user);
    }

    private static Map<String, String> parseInitData(String initData) {
        Map<String, String> result = new HashMap<>();
        String[] params = initData.split("&");
        for (String param : params) {
            String[] pair = param.split("=");
            String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
            String value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
            result.put(key, value);
        }
        return result;
    }
}
