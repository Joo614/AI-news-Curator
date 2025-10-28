package com.aiproject.ai_news_curator.domain.user.controller;

import java.net.URI;

import com.aiproject.ai_news_curator.domain.user.dto.UserPostDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserUpdateDto;
import com.aiproject.ai_news_curator.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody UserPostDto userPostDto) {
        userService.createUser(userPostDto);
        return ResponseEntity.created(URI.create("/user")).build();
    }

    // 유저 정보 수정 - 닉네임
    @PostMapping()
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        // 수정 됐는지 아닌지만 boolean으로 res
        return null;
    }

    // 유저 정보 조회
//    @GetMapping("/me")
//        public ResponseEntity<UserResDto> findMe(@AuthenticationPrincipal UserPrincipal principal) {
//        return null;
//    }
}
