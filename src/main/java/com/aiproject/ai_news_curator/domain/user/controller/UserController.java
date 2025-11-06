package com.aiproject.ai_news_curator.domain.user.controller;

import com.aiproject.ai_news_curator.auth.entity.UserPrincipal;
import com.aiproject.ai_news_curator.domain.user.dto.NicknameUpdateDto;
import com.aiproject.ai_news_curator.domain.user.dto.PasswordUpdateDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserPostDto;
import com.aiproject.ai_news_curator.domain.user.dto.UserResDto;
import com.aiproject.ai_news_curator.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserPostDto userPostDto) {
        userService.createUser(userPostDto);
        return ResponseEntity.created(URI.create("/user")).build();
    }

    // 유저 정보 수정 - 닉네임
    @PatchMapping("/me/nickname")
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody NicknameUpdateDto nicknameUpdateDto,
        @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.updateNickname(nicknameUpdateDto, principal.getUsername()));
    }

    // 유저 정보 수정 - 비밀번호
    // TODO: 나중에 현재 비밀번호 확인 후 변경 가능하도록 변경
    @PatchMapping("/me/password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordUpdateDto,
                                               @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.updatePassword(passwordUpdateDto, principal.getUsername()));
    }

    // 유저 정보 조회
   @GetMapping("/me")
       public ResponseEntity<UserResDto> findMe(@AuthenticationPrincipal UserPrincipal principal) {
       return ResponseEntity.ok(userService.findUser(principal.getUsername()));
   }
}
