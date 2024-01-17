/*
 * Copyright (C) 2012 Vex Software LLC
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

package com.kryeit.votifier;

import com.kryeit.votifier.config.ConfigReader;
import com.kryeit.votifier.crypto.RSAIO;
import com.kryeit.votifier.crypto.RSAKeygen;
import com.kryeit.votifier.model.ListenerLoader;
import com.kryeit.votifier.model.VoteListener;
import com.kryeit.votifier.model.VotifierEvent;
import com.kryeit.votifier.model.listeners.BasicVoteListener;
import com.kryeit.votifier.net.VoteReceiver;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * The main Votifier plugin class.
 * 
 * @author Blake Beaupain
 * @author Kramer Campbell
 */
public class Votifier implements DedicatedServerModInitializer {

	/** The logger instance. */
	public static final Logger LOGGER = LoggerFactory.getLogger(Votifier.class);

	/** Log entry prefix */
	private static final String logPrefix = "[Votifier] ";

	/** The Votifier instance. */
	private static Votifier instance;

	/** The current Votifier version. */
	private String version;

	/** The vote listeners. */
	private final List<VoteListener> listeners = new ArrayList<VoteListener>();

	/** The vote receiver. */
	private VoteReceiver voteReceiver;

	/** The RSA key pair. */
	private KeyPair keyPair;

	/** Debug mode flag */
	private boolean debug;

	@Override
	public void onInitializeServer() {
		Votifier.instance = this;

		// Set the plugin version.
		version = "1.0";

		// Handle configuration.
		try {
			LOGGER.info("Reading config file...");
			ConfigReader.readFile(Path.of("config/votifier"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		File rsaDirectory = new File("mods/votifier/rsa");
		// Replace to remove a bug with Windows paths - SmilingDevil
		String listenerDirectory = "mods/votifier/listeners";
		
		/*
		 * Create RSA directory and keys if it does not exist; otherwise, read
		 * keys.
		 */
		try {
			if (!rsaDirectory.exists()) {
				rsaDirectory.mkdir();
				new File(listenerDirectory).mkdir();
				keyPair = RSAKeygen.generate(2048);
				RSAIO.save(rsaDirectory, keyPair);
			} else {
				keyPair = RSAIO.load(rsaDirectory);
			}
		} catch (Exception ex) {
			LOGGER.warn("Error reading configuration file or RSA keys", ex);
			gracefulExit();
			return;
		}

		// Load the vote listeners.
		listenerDirectory = ConfigReader.LISTENER_FOLDER;
		listeners.addAll(ListenerLoader.load(listenerDirectory));

		// Initialize the receiver.
		String host = ConfigReader.HOST;
		int port = ConfigReader.PORT;
		debug = ConfigReader.DEBUG;
		if (debug)
			LOGGER.info("DEBUG mode enabled!");

		try {
			voteReceiver = new VoteReceiver(this, host, port);
			voteReceiver.start();

			LOGGER.info("Votifier enabled.");
		} catch (Exception ex) {
			gracefulExit();
			return;
		}

		VotifierEvent.EVENT.register(new BasicVoteListener());
		registerDisableEvent();
	}

	public void registerDisableEvent() {
		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			// Interrupt the vote receiver.
			if (voteReceiver != null) {
				voteReceiver.shutdown();
			}
			LOGGER.info("Votifier disabled.");
		});
	}

	private void gracefulExit() {
		LOGGER.warn("Votifier did not initialize properly!");
	}

	/**
	 * Gets the instance.
	 * 
	 * @return The instance
	 */
	public static Votifier getInstance() {
		return instance;
	}

	/**
	 * Gets the version.
	 * 
	 * @return The version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Gets the listeners.
	 * 
	 * @return The listeners
	 */
	public List<VoteListener> getListeners() {
		return listeners;
	}

	/**
	 * Gets the vote receiver.
	 * 
	 * @return The vote receiver
	 */
	public VoteReceiver getVoteReceiver() {
		return voteReceiver;
	}

	/**
	 * Gets the keyPair.
	 * 
	 * @return The keyPair
	 */
	public KeyPair getKeyPair() {
		return keyPair;
	}

	public boolean isDebug() {
		return debug;
	}

}
