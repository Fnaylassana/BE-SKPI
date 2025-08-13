package com.skpijtk.springboot_boilerplate.service;

import com.skpijtk.springboot_boilerplate.model.User;

import java.util.Optional;

import java.util.List;
public interface UserService {
    List<User> findAllUsers();
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    User saveUser(User user);
    void deleteUser(Integer id);
}

