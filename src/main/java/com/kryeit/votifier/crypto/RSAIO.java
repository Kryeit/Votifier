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

package com.kryeit.votifier.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Static utility methods for saving and loading RSA key pairs.
 * 
 * @author Blake Beaupain
 */
public class RSAIO {

	/**
	 * Saves the key pair to the disk.
	 * 
	 * @param directory
	 *            The directory to save to
	 * @param keyPair
	 *            The key pair to save
	 * @throws Exception
	 *             If an error occurs
	 */
	public static void save(File directory, KeyPair keyPair) throws Exception {
		// Check if the directory exists, if not, create it
		if (!directory.exists()) {
			directory.mkdirs();
		}

		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		// Store the public key
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKey.getEncoded());
		try (FileOutputStream out = new FileOutputStream(new File(directory, "public.key"))) {
			out.write(Base64.getEncoder().encodeToString(publicSpec.getEncoded()).getBytes());
		}

		// Store the private key
		PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
		try (FileOutputStream out = new FileOutputStream(new File(directory, "private.key"))) {
			out.write(Base64.getEncoder().encodeToString(privateSpec.getEncoded()).getBytes());
		}
	}

	/**
	 * Loads an RSA key pair from a directory. The directory must have the files
	 * "public.key" and "private.key".
	 * 
	 * @param directory
	 *            The directory to load from
	 * @return The key pair
	 * @throws Exception
	 *             If an error occurs
	 */
	public static KeyPair load(File directory) throws Exception {
		// Read and decode the public key file.
		File publicKeyFile = new File(directory, "public.key");
		byte[] encodedPublicKey = readFileAsBytes(publicKeyFile);
		encodedPublicKey = Base64.getDecoder().decode(new String(encodedPublicKey, StandardCharsets.UTF_8));

		// Read and decode the private key file.
		File privateKeyFile = new File(directory, "private.key");
		byte[] encodedPrivateKey = readFileAsBytes(privateKeyFile);
		encodedPrivateKey = Base64.getDecoder().decode(new String(encodedPrivateKey, StandardCharsets.UTF_8));

		// Instantiate and return the key pair.
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

	private static byte[] readFileAsBytes(File file) throws Exception {
		try (FileInputStream in = new FileInputStream(file)) {
			byte[] fileBytes = new byte[(int) file.length()];
			in.read(fileBytes);
			return fileBytes;
		}
	}

}
