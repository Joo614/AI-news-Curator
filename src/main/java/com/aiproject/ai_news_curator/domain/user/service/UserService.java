package com.aiproject.ai_news_curator.domain.user.service;

import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import com.aiproject.ai_news_curator.domain.user.entity.User;

public interface UserService {
    User createUser(User user);

    User verifyUser(String email);

    Boolean updateUser(UserUpdateDto updateDto, String email);

    UserResDto findUser(String email);

    void duplicateUser(String email);
}
