package com.followermaze.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.PriorityQueue;

/**
 * This class will sort each event.it will add them in a
 * <code>PriorityQueue</code> when it's published and sort them based on their
 * nature
 * 
 * @author tirtha
 *
 */
public class EventSorter {

	private static final Logger logger = LoggerFactory.getLogger(EventSorter.class);

	private final PriorityQueue<BaseEvent> queue = new PriorityQueue<>();

	private final EventProcessor visitor;

	private int expectedSequence = 1;

	public EventSorter(EventProcessor visitor) {
		this.visitor = visitor;
	}

	public int on(Optional<? extends BaseEvent> event) {

		logger.info("Received event {}", event);

		event.ifPresent(queue::add);

		while (processQueue())
			;

		return queue.size();
	}

	/**
	 * Processing the queue for each event
	 * 
	 * @return
	 */
	private boolean processQueue() {

		final BaseEvent head = queue.peek();

		if ((head != null) && (head.sequence == this.expectedSequence)) {

			this.expectedSequence++;
			BaseEvent event = queue.poll();
			event.process(this.visitor);

			logger.info("Processing event {}", event);

			return true;
		}

		return false;
	}
}
