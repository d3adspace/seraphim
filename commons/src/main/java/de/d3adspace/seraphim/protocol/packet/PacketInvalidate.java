/*
 * Copyright (c) 2017 D3adspace
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.seraphim.protocol.packet;


import de.d3adspace.skylla.commons.buffer.SkyllaBuffer;
import de.d3adspace.skylla.commons.protocol.packet.SkyllaPacket;
import de.d3adspace.skylla.commons.protocol.packet.SkyllaPacketMeta;

/**
 * Providing one of the three basic CRUD Operation. Requesting to invalidate a remote cached
 * object.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
@SkyllaPacketMeta(id = 2)
public class PacketInvalidate extends SkyllaPacket {
	
	/**
	 * They key to invalidate.
	 */
	private Object key;
	
	/**
	 * Create a request.
	 *
	 * @param key The key.
	 */
	public PacketInvalidate(Object key) {
		this.key = key;
	}
	
	/**
	 * Packet Constructor.
	 */
	public PacketInvalidate() {
	}
	
	/**
	 * Get the key of the object to invalidate.
	 *
	 * @return The key.
	 */
	public Object getKey() {
		return key;
	}
	
	public void write(SkyllaBuffer skyllaBuffer) {
		skyllaBuffer.writeObject(key);
	}
	
	public void read(SkyllaBuffer skyllaBuffer) {
		key = skyllaBuffer.readObject();
	}
	
	@Override
	public String toString() {
		return "PacketInvalidate{" +
			"key=" + key +
			'}';
	}
}
