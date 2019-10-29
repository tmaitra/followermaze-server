package com.followermaze.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.followermaze.eventsource.EventSourceConnector;

/**
 * Server socket class provide the communication mechanism between event source
 * and user clients. It will listen to port 9099 for user clients & 9090 event
 * source.
 * 
 * @author tirtha
 *
 */
public class ServerSocket {

	private static final Logger logger = LoggerFactory.getLogger(EventSourceConnector.class);

	/**
	 * Method to listen to a port for event source and user clients
	 * 
	 * @param port
	 *            Attempts to create a server socket bound to the specified
	 *            port. An exception occurs if the port is already bound by
	 *            another application.
	 * @param connector
	 */
	public static void listen(final int port, final Connector connector) {

		try (java.net.ServerSocket socket = new java.net.ServerSocket(port)) {

			logger.info("Server Socket listening on port {}", port);

			while (connector.newPeer(new Peer(socket.accept())))
				;

		} catch (Exception e) {
			logger.error("Error on connector, port " + port, e);
		} finally {
			logger.info("Connector on port {} finished.", port);
		}
	}
}
