package com.followermaze.event;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.followermaze.user.UserClientsRegistry;

public class EventSorterTest {

    private EventSorter sorter;

    @Before
    public void setUp() {
        this.sorter = new EventSorter(new EventProcessor(new UserClientsRegistry()));
    }

    @Test
    public void emptyEvent() {
        assertEquals(0, this.sorter.on(Optional.empty()));
    }

    @Test
    public void unexpectedSequence() {
        assertEquals(1, this.sorter.on(eventWithSequence(3)));
    }

    @Test
    public void rightOrder() {
        assertEquals(0, this.sorter.on(eventWithSequence(1)));
    }

    @Test
    public void outOfOrder() {
        assertEquals(1, this.sorter.on(eventWithSequence(2)));
        assertEquals(0, this.sorter.on(eventWithSequence(1)));
    }

    private Optional<BroadcastEvent> eventWithSequence(int seq) {
        return Optional.of(new BroadcastEvent("", seq));
    }
}
