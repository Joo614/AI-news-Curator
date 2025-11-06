package com.aiproject.ai_news_curator.domain.interaction.controller;

import com.aiproject.ai_news_curator.auth.entity.UserPrincipal;
import com.aiproject.ai_news_curator.domain.interaction.dto.InteractionReqDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiproject.ai_news_curator.domain.interaction.service.InteractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interactions")
@Validated
@RequiredArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;

    @PostMapping("/like")
    public ResponseEntity<Void> toggleLike(@Valid @RequestBody InteractionReqDto interactionReqDto,
                                     @AuthenticationPrincipal UserPrincipal principal) {
        interactionService.toggleLike(interactionReqDto, principal.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/read")
    public ResponseEntity<Void> activateRead(@Valid @RequestBody InteractionReqDto interactionReqDto,
                                           @AuthenticationPrincipal UserPrincipal principal) {
        interactionService.activateRead(interactionReqDto, principal.getUsername());
        return ResponseEntity.ok().build();
    }
}
