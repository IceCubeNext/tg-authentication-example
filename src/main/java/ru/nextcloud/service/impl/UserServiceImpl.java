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
    public User addUser(String initData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> data = parseInitData(initData);

//        if (!TelegramAuthVerifier.isValid(data, botToken)) {
//            log.debug("invalid initData");
//            return new User(0L, "нет данных", "нет данных", "нет данных");
//        }

        String userJson = data.get("user");
        User user = mapper.readValue(userJson, User.class);

        if (repository.findById(user.getId()).isPresent()) {
            User oldUser = repository.findById(user.getId()).get();
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            return repository.save(oldUser);
        }

        return repository.save(user);
    }

    private Map<String, String> parseInitData(String initData) {
        Map<String, String> map = new HashMap<>();
        String[] pairs = initData.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }
}
