package com.virtudoc.web.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String username, String password);
}