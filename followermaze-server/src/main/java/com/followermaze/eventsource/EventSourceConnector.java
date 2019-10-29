package com.followermaze.eventsource;

import com.followermaze.event.EventParser;
import com.followermaze.event.EventProcessor;
import com.followermaze.event.EventSorter;
import com.followermaze.server.Connector;
import com.followermaze.server.Peer;

/**
 * This class will act as an event source connector.It will parse the event and
 * also sort it
 * 
 * @author tirtha
 *
 */
public class EventSourceConnector implements Connector {

	private final EventSorter sorter;

	public EventSourceConnector(EventProcessor visitor) {
		this.sorter = new EventSorter(visitor);
	}

	@Override
	public Boolean newPeer(Peer peer) {

		peer.read().map(new EventParser()::parse).forEach(this.sorter::on);

		// only one event source will be processed
		return false;
	}
}
