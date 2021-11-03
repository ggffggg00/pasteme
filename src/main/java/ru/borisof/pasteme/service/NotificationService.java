package ru.borisof.pasteme.service;

import org.springframework.web.context.request.async.DeferredResult;
import ru.borisof.pasteme.model.dto.Notification;

import javax.naming.LimitExceededException;

public interface NotificationService {

    void registerSubscriber(long userId, DeferredResult<Notification> response) throws LimitExceededException;

    void unregisterSubscriber(long userId);

    void notifySubscriber(long userId, Notification notification);
}
