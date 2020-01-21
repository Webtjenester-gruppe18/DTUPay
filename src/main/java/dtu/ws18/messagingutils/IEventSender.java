package dtu.ws18.messagingutils;

import dtu.ws18.models.Event;

/**
 * @author Emil Glimø Vinkel - s175107
 */

public interface IEventSender {
    void sendEvent(Event event) throws Exception;
}
