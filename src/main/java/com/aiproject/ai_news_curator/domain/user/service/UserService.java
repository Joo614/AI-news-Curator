package com.aiproject.ai_news_curator.domain.user.service;

import com.aiproject.ai_news_curator.domain.user.dto.NicknameUpdateDto;
import com.aiproject.ai_news_curator.domain.user.dto.PasswordUpdateDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserPostDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.entity.User;

public interface UserService {
    void createUser(UserPostDto userPostDto);

    User verifyUser(String email);

    Boolean updateNickname(NicknameUpdateDto nicknameUpdateDto, String email);
    Boolean updatePassword(PasswordUpdateDto passwordUpdateDto, String email);

    UserResDto findUser(String email);

    void duplicateUser(String email);
}
