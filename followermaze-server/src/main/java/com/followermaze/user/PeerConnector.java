package com.followermaze.user;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.followermaze.server.Connector;
import com.followermaze.server.Peer;
/**
 * This class will connect all the peer from user clients
 * @author tirtha
 *
 */
public class PeerConnector implements Connector {

    private static final Logger logger = LoggerFactory.getLogger(PeerConnector.class);

    private final UserClientsRegistry registry;

    public PeerConnector(UserClientsRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Boolean newPeer(Peer peer) {

        Optional<String> id = peer.read().findFirst();
        if(id.isPresent()) {
            this.registry.onPeerConnected(id.get(), peer);
        } else {
            logger.warn("Unable to get client ID");
        }

        // receives next peer
        return true;
    }
}
