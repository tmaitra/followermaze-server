package com.followermaze.user;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.followermaze.server.Peer;

/**
 * This class will keep information for each client and their peer
 * 
 * @author tirtha
 *
 */
public class UserClientsRegistry {

	private static final Logger logger = LoggerFactory.getLogger(UserClientsRegistry.class);

	private final ConcurrentHashMap<String, Peer> connectedPeers = new ConcurrentHashMap<>();

	/**
	 * Keeping client and their peer
	 * 
	 * @param clientID
	 * @param peer
	 */
	public void onPeerConnected(String clientID, Peer peer) {

		logger.info("Peer connected {}", clientID);
		this.connectedPeers.put(clientID, peer);
	}

	public void broadcast(String raw) {

		this.connectedPeers.values().forEach(peer -> peer.write(raw));
	}

	public void write(String to, String raw) {

		Peer peer = this.connectedPeers.get(to);
		if (peer != null) {
			peer.write(raw);
		}
	}

	public void closeAll() {

		this.connectedPeers.values().forEach(Peer::close);
		this.connectedPeers.clear();
	}
}
