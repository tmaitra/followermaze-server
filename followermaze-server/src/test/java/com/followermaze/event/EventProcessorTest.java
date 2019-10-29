package com.followermaze.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;

import com.followermaze.user.UserClientsRegistry;

public class EventProcessorTest {

    private EventProcessor processor;
    private UserClientsRegistry registry;

    @Before
    public void setUp() {
        this.registry = mock(UserClientsRegistry.class);
        this.processor = new EventProcessor(this.registry);
    }

    @Test
    public void broadcast() {
        this.processor.on(new BroadcastEvent("meh", 88));
        verify(this.registry).broadcast("meh");
    }

    @Test
    public void privateMessage() {
        this.processor.on(new PrivateMessageEvent("bob", 33, "from", "to"));
        verify(this.registry).write("to", "bob");
    }

    @Test
    public void unfollowWontFailIfUserIsNotAFollower() {
        this.processor.on(new UnfollowEvent("raw", 1, "alice", "bob"));
        verifyZeroInteractions(this.registry);
    }

    @Test
    public void network() {

        // bob has no follower, so no one will receive the message
        this.processor.on(new StatusUpdateEvent("first!", 1, "bob"));
        verifyZeroInteractions(this.registry);

        // alice follows bob, that is then notified
        this.processor.on(new FollowEvent("hohoho", 1, "alice", "bob"));
        verify(this.registry).write("bob", "hohoho");

        // bob posts, so alice is notified
        this.processor.on(new StatusUpdateEvent("second!", 1, "bob"));
        verify(this.registry).write("alice", "second!");

        // user is not notified of unfollows
        this.processor.on(new UnfollowEvent("meh", 1, "alice", "bob"));
        verifyNoMoreInteractions(this.registry);

        // nobody will listen to bob this time
        this.processor.on(new StatusUpdateEvent("third", 1, "bob"));
        verifyNoMoreInteractions(this.registry);
    }
}
