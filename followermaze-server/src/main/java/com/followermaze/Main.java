package com.followermaze;

import java.util.concurrent.CompletableFuture;

import com.followermaze.event.EventProcessor;
import com.followermaze.eventsource.EventSourceConnector;
import com.followermaze.server.ServerSocket;
import com.followermaze.user.PeerConnector;
import com.followermaze.user.UserClientsRegistry;

/**
 * Main class to start the server socket for Event sources and User Clients
 * 
 * @author tirtha
 *
 */
public class Main {
	public static void main(String[] args) {

		// keeps all connected peers
		UserClientsRegistry peerRegistry = new UserClientsRegistry();

		// listen for peer connections
		CompletableFuture<Void> peerFuture = CompletableFuture
				.runAsync(() -> ServerSocket.listen(9099, new PeerConnector(peerRegistry)));

		// receives event source events
		ServerSocket.listen(9090, new EventSourceConnector(new EventProcessor(peerRegistry)));

		peerFuture.cancel(false);

		peerRegistry.closeAll();

	}
}
