package com.followermaze.event;

import com.followermaze.user.UserClientsRegistry;

/**
 * This class will be used for processing each type of events 1) Follow 2)
 * Unfollow 3) Broadcast 4) Private Msg 5) Status Update
 * 
 * @author tirtha
 *
 */
public class EventProcessor {

	private final ClientGraph graph = new ClientGraph();

	private final UserClientsRegistry peerRegistry;

	/**
	 * Constructor which will accept the registry of client and peer
	 * 
	 * @param registry
	 */
	public EventProcessor(UserClientsRegistry registry) {
		this.peerRegistry = registry;
	}

	/**
	 * Following to an event
	 * 
	 * @param event
	 */
	public void on(FollowEvent event) {

		this.graph.addFollower(event.to, event.from);

		this.peerRegistry.write(event.to, event.raw);
	}

	/**
	 * Broadcasting event
	 * 
	 * @param event
	 */
	public void on(BroadcastEvent event) {

		this.peerRegistry.broadcast(event.raw);
	}

	/**
	 * Unfollow to an event
	 * 
	 * @param event
	 */
	public void on(UnfollowEvent event) {

		this.graph.removeFollower(event.to, event.from);
	}

	/**
	 * Processing to a private message
	 * 
	 * @param event
	 */
	public void on(PrivateMessageEvent event) {

		this.peerRegistry.write(event.to, event.raw);
	}

	/**
	 * Updating status for an event
	 * 
	 * @param event
	 */
	public void on(StatusUpdateEvent event) {

		this.graph.forEachFollower(event.from, followerID -> this.peerRegistry.write(followerID, event.raw));
	}
}
