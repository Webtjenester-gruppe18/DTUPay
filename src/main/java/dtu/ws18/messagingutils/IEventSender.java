package dtu.ws18.messagingutils;

import dtu.ws18.models.Event;

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
