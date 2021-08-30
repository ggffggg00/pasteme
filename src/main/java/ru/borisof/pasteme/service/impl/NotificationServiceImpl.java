package ru.borisof.pasteme.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import ru.borisof.pasteme.dto.Notification;
import ru.borisof.pasteme.service.AccountService;
import ru.borisof.pasteme.service.NotificationService;

import javax.annotation.PostConstruct;
import javax.naming.LimitExceededException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    final int LIMIT = 600;

    private final AccountService accountService;

    private Map<Long, DeferredResult<Notification>> subscribers;

    @PostConstruct
    private void init(){
        subscribers = new HashMap<>();
    }

    @Override
    public void registerSubscriber(long userId, DeferredResult<Notification> response)
            throws LimitExceededException {

        if(!accountService.userExists(userId))
            throw new ValidationException("User doesn't exists");

        if (!canAddSubscriber())
            throw new LimitExceededException("Pool of subscribers is full");

        if(subscribers.containsKey(userId))
            throw new LimitExceededException("User already subscribed");

        subscribers.put(userId, response);

    }

    @Override
    public void unregisterSubscriber(long userId) {
        if(subscribers.containsKey(userId))
            subscribers.remove(userId);
    }

    @Override
    public void notifySubscriber(long userId, Notification notification) {

        if(!subscribers.containsKey(userId))
            throw new IllegalArgumentException("User not subscribed");

        DeferredResult<Notification> result = subscribers.get(userId);

        result.setResult(notification);

    }

    private boolean canAddSubscriber(){
        return subscribers.size() <= LIMIT;
    }

}
