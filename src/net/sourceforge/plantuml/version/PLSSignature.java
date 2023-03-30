/* ========================================================================
 * PlantUML : a free UML diagram generator
 * ========================================================================
 *
 * (C) Copyright 2009-2024, Arnaud Roques
 *
 * Project Info:  https://plantuml.com
 * 
 * If you like this project or if you find it useful, you can support us at:
 * 
 * https://plantuml.com/patreon (only 1$ per month!)
 * https://plantuml.com/paypal
 * 
 * This file is part of PlantUML.
 *
 * PlantUML is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlantUML distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 *
 * Original Author:  Arnaud Roques
 *
 */
package net.sourceforge.plantuml.version;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Random;

import net.sourceforge.plantuml.FileUtils;
import net.sourceforge.plantuml.OptionPrint;
import net.sourceforge.plantuml.log.Logme;
import net.sourceforge.plantuml.utils.SignatureUtils;

public class PLSSignature {
	// ::remove file when __CORE__

	private final int type;
	private final byte[] sha;
	private final long now;
	private final long exp;
	private final String owner;
	private final String context;

	public PLSSignature(int type, byte[] sha, long now, long exp, String owner, String context) {
		this.type = type;
		this.sha = sha;
		this.now = now;
		this.exp = exp;
		this.owner = owner;
		this.context = context;
	}

	public static byte[] retrieveDistributorImageSignature() throws IOException, NoSuchAlgorithmException {
		final InputStream dis = PSystemVersion.class.getResourceAsStream("/distributor.png");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileUtils.copyToStream(dis, baos);
		return SignatureUtils.getSHA512raw(baos.toByteArray());
	}

	public static PLSSignature fromRaw512(byte[] data) throws NoSuchAlgorithmException, IOException {
		if (data.length != 512) {
			throw new IllegalArgumentException();
		}

		final byte resultA[] = new byte[64];
		final byte resultB[] = new byte[512 - 64];
		System.arraycopy(data, 0, resultA, 0, resultA.length);
		System.arraycopy(data, 64, resultB, 0, resultB.length);

		final byte[] sig = SignatureUtils.getSHA512raw(resultB);
		if (SignatureUtils.toHexString(resultA).equals(SignatureUtils.toHexString(sig)) == false) {
			return null;
			// throw new IOException();
		}

		final ByteArrayInputStream bais = new ByteArrayInputStream(resultB);
		final int type = bais.read();
		if (type == 0) {
			final int version = bais.read();
			final byte sha[] = readBytes(bais, 64);
			final long now = readLong(bais);
			final long exp = readLong(bais);
			final String owner = readString(bais);
			return new PLSSignature(type, sha, now, exp, owner, null);
		}
		if (type == 2) {
			final int version = bais.read();
			final byte sha[] = readBytes(bais, 64);
			final long now = readLong(bais);
			final long exp = readLong(bais);
			final String owner = readString(bais);
			final String context = readString(bais);
			return new PLSSignature(type, sha, now, exp, owner, context);
		}
		return null;
	}

	// public static byte shrink(byte data[]) {
	// byte result = 42;
	// for (byte b : data) {
	// result ^= b;
	// }
	// return result;
	// }

	private static byte[] readBytes(ByteArrayInputStream bais, int size) throws IOException {
		byte[] result = new byte[size];
		final int read = bais.read(result);
		if (read != size) {
			throw new IOException();
		}
		return result;
	}

	private static String readString(ByteArrayInputStream bais) throws IOException {
		final int size = bais.read();
		if (size > 80) {
			throw new IOException();
		}
		byte[] result = new byte[size];
		final int read = bais.read(result);
		if (read != size) {
			throw new IOException();
		}
		return new String(result, UTF_8);
	}

	private static long readLong(ByteArrayInputStream bais) throws IOException {
		final byte[] result = new byte[8];
		final int read = bais.read(result);
		if (read != 8) {
			throw new IOException();
		}
		long ll = 0;
		for (int i = 7; i >= 0; i--) {
			final long mask = ((long) (result[i] & 0xFF)) << (8 * (7 - i));
			ll = ll | mask;
		}
		return ll;
	}

	public static byte[] getSalt(final String signature) throws UnsupportedEncodingException {
		final Random rnd = new Random(getSeed(signature.getBytes(UTF_8)));
		final byte salt[] = new byte[512];
		rnd.nextBytes(salt);
		return salt;
	}

	private static long getSeed(byte[] bytes) {
		long result = 19;
		for (byte b : bytes) {
			result = result * 41 + b;
		}
		return result;
	}

	public static byte[] signature() throws IOException {
		final String signature = OptionPrint.getHostName() + getMacAddress();
		try {
			return SignatureUtils.getSHA512raw(SignatureUtils.salting(signature, getSalt(signature)));
		} catch (Exception e) {
			Logme.error(e);
			throw new IOException();
		}
	}

	private static String getMacAddress() throws IOException {

		final Enumeration<NetworkInterface> net = NetworkInterface.getNetworkInterfaces();
		final StringBuilder result = new StringBuilder();
		while (net.hasMoreElements()) {
			final NetworkInterface element = net.nextElement();
			byte[] mac = element.getHardwareAddress();
			if (mac != null) {
				for (byte b : mac) {
					result.append(String.format("%02x", b));
				}
			}
		}
		return result.toString();
	}

}
