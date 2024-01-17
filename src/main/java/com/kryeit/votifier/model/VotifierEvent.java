package com.kryeit.votifier.model;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * {@code VotifierEvent} is a custom Bukkit event class that is sent
 * synchronously to CraftBukkit's main thread allowing other plugins to listener
 * for votes.
 * 
 * @author frelling
 * 
 */
public interface VotifierEvent {
	// Event instance
	Event<VotifierEvent> EVENT = EventFactory.createArrayBacked(VotifierEvent.class, (listeners) -> (vote) -> {
		for (VotifierEvent listener : listeners) {
			listener.onVoteReceived(vote);
		}
	});

	// Method called when a vote is received
	void onVoteReceived(Vote vote);
}
