package dtu.ws18.messagingutils;

import dtu.ws18.models.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}
