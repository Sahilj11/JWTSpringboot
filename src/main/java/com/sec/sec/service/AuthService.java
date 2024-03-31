package com.sec.sec.service;

/**
 * AuthService
 */
public interface AuthService {

    String login(String username, String password);

    String signup(String name, String username, String password);

    
}
