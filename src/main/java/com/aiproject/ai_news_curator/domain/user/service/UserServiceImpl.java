package com.aiproject.ai_news_curator.domain.user.service;

import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public Boolean updateUser(UserUpdateDto updateDto, String username) {
        return null;
    }

    @Override
    public UserResDto findUser(String username) {
        return null;
    }

    @Override
    public User verifyUser(String email) {
        return null;
    }

    @Override
    public void duplicateUser(String email) {

    }
}
