package com.example.websocketdemo.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service("UserManager")
public class UserManagerImpl implements IUserManager {
    private Map<String, Set<String>> map;

    public UserManagerImpl() {
        this.map = new HashMap<>();
    }

    @Override
    public void set(String username, String sessionId) {
        get(username).add(sessionId);
    }

    @Override
    public Set<String> get(String username) {
        checkAndInit(username);
        return map.get(username);
    }

    @Override
    public void remove(String username, String sessionId) {
        get(username).remove(sessionId);
    }

    @Override
    public boolean isOnline(String username) {
        return get(username).size() != 0;
    }

    private void checkAndInit(String username) {
        if (!map.containsKey(username)) {
            map.put(username, new HashSet<>());
        }
    }
}
