package com.aiproject.ai_news_curator.domain.user.service;

import com.aiproject.ai_news_curator.auth.utils.AuthoritiesUtils;
import com.aiproject.ai_news_curator.domain.user.dto.UserPostDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import com.aiproject.ai_news_curator.domain.user.entity.User;
import com.aiproject.ai_news_curator.domain.user.enums.ProviderType;
import com.aiproject.ai_news_curator.domain.user.repository.JpaUserRepository;
import com.aiproject.ai_news_curator.global.exception.CustomLogicException;
import com.aiproject.ai_news_curator.global.exception.ExceptionCode;
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
    private final AuthoritiesUtils authoritiesUtils;

    @Override
    public User createUser(UserPostDto userPostDto) {
        duplicateUser(userPostDto.getEmail());
        User user = User.builder()
            .email(userPostDto.getEmail())
            .password(passwordEncoder.encode(userPostDto.getPassword()))
            .nickname(userPostDto.getNickname())
            .providerType(ProviderType.NATIVE)
            .build();
        user.setRoles(authoritiesUtils.createAuthorities(user));

        return userRepository.save(user);
    }

    @Override
    public Boolean updateUser(UserUpdateDto updateDto, String email) {
        User user = verifyUser(email);
        user.update(updateDto);
        return true;
    }

    @Override
    public UserResDto findUser(String email) {
        User user = verifyUser(email);
        return UserResDto.builder().user(user).build();
    }

    @Override
    public User verifyUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomLogicException(ExceptionCode.USER_NONE));
    }

    @Override
    public void duplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new CustomLogicException(ExceptionCode.USER_DUPLICATED);
                });
    }
}
