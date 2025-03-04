/*
 * Copyright (C) 2011 Vex Software LLC
 * This file is part of Votifier.
 * 
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kryeit.votifier.model.listeners;

import com.kryeit.votifier.model.Vote;
import com.kryeit.votifier.model.VotifierEvent;
import com.kryeit.votifier.utils.Utils;

import static com.kryeit.votifier.Votifier.LOGGER;
import static com.kryeit.votifier.config.ConfigReader.COMMAND;

/**
 * A basic vote listener for demonstration purposes.
 * 
 * @author Blake Beaupain
 */
public class BasicVoteListener implements VotifierEvent {

	@Override
	public void onVoteReceived(Vote vote) {
		LOGGER.info("Received: " + vote);

		String command = COMMAND.replace("%player%", vote.getUsername());

		if (!command.equals(""))
			Utils.executeCommandAsServer(command);
	}
}
