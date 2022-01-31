package com.virtudoc.web.service;

import com.virtudoc.web.entity.UserAccount;

public interface UserService {
    void save(UserAccount user);

    UserAccount findByUsername(String username);
}
