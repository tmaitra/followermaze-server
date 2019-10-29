package com.followermaze.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This class will maintain user clients follower actions
 * 
 * @author tirtha
 *
 */
public class ClientGraph {

	private final HashMap<String, Set<String>> followers = new HashMap<>();

	public void forEachFollower(String to, Consumer<String> f) {

		get(to).ifPresent(followers -> followers.forEach(f));
	}

	/**
	 * Adding follower
	 * 
	 * @param to
	 * @param from
	 */
	public void addFollower(String to, String from) {

		get(to).orElseGet(() -> {
			HashSet<String> followers = new HashSet<>();
			this.followers.put(to, followers);
			return followers;
		}).add(from);
	}

	/**
	 * Removing follower
	 * 
	 * @param to
	 * @param from
	 */
	public void removeFollower(String to, String from) {

		get(to).ifPresent(f -> f.remove(from));
	}

	private Optional<Set<String>> get(String key) {
		return Optional.ofNullable(this.followers.get(key));
	}
}
