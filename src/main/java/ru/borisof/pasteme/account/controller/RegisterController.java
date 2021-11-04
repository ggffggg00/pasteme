package ru.borisof.pasteme.account.controller;

import javax.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.borisof.pasteme.account.model.dto.UserRegisterRequest;
import ru.borisof.pasteme.account.model.entity.User;
import ru.borisof.pasteme.account.service.AccountService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RegisterController {

  private final AccountService accountService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResult> register(
      @RequestBody @Valid UserRegisterRequest registerDto) {

    User registeredUser = accountService.registerUser(registerDto);
    return ResponseEntity.ok(
        new RegisterResult("ok", registeredUser));
  }

  @Data
  @RequiredArgsConstructor
  static class RegisterResult {

    private final String status;

    private final User userDetails;
  }

}
