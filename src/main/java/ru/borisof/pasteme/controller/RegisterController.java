package ru.borisof.pasteme.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.pasteme.model.dto.UserRegisterRequest;
import ru.borisof.pasteme.service.AccountService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterController {

  private final AccountService accountService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResult> register(@RequestBody UserRegisterRequest registerDto) {

    accountService.registerUser(registerDto);
    return ResponseEntity.ok(new RegisterResult("ok"));
  }

  @Data
  @RequiredArgsConstructor
  static class RegisterResult {

    private final String status;
  }

}
