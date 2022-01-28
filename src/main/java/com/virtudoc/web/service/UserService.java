package com.virtudoc.web.service;

import com.virtudoc.web.entity.UserEntity;

public interface UserService {
    void save(UserEntity user);

    UserEntity findByUsername(String username);
}
