package ru.borisof.pasteme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import ru.borisof.pasteme.model.dto.Notification;
import ru.borisof.pasteme.service.NotificationService;

import javax.naming.LimitExceededException;

@RestController
@RequiredArgsConstructor
@RequestMapping("longpoll")
public class LongPollController {

    final NotificationService notificationService;

    @GetMapping
    public DeferredResult<Notification> longpoll(@RequestParam long userId)
            throws LimitExceededException {

        DeferredResult<Notification> response = new DeferredResult<>(
                60000L);

        notificationService.registerSubscriber(userId, response);
        return response;
    }

}
