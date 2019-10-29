package com.followermaze.server;
/**
 * 
 * @author tirtha
 *
 */
@FunctionalInterface
public interface Connector {

    /**
     * Process a new peer.
     *
     * @param peer
     * @return it should accept more peers
     */
    Boolean newPeer(Peer peer);
}
