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

package de.d3adspace.seraphim.server.handler;

import de.d3adspace.seraphim.cache.CacheEntry;
import de.d3adspace.seraphim.protocol.packet.PacketGet;
import de.d3adspace.seraphim.protocol.packet.PacketGetResponse;
import de.d3adspace.seraphim.protocol.packet.PacketInvalidate;
import de.d3adspace.seraphim.protocol.packet.PacketPut;
import de.d3adspace.seraphim.server.cache.ServerCache;
import de.d3adspace.skylla.commons.connection.SkyllaConnection;
import de.d3adspace.skylla.commons.protocol.handler.PacketHandler;
import de.d3adspace.skylla.commons.protocol.handler.PacketHandlerMethod;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ServerPacketHandler implements PacketHandler {
	
	private final ServerCache cache;
	
	public ServerPacketHandler(ServerCache cache) {
		this.cache = cache;
	}
	
	@PacketHandlerMethod
	public void onPacketPut(SkyllaConnection connection, PacketPut packet) {
		this.cache.put(packet.getKey(), new CacheEntry(packet.getValue(), packet.getExpiry()));
	}
	
	@PacketHandlerMethod
	public void onPacketInvalidate(SkyllaConnection connection, PacketInvalidate packet) {
		this.cache.remove(packet.getKey());
	}
	
	@PacketHandlerMethod
	public void onPacketGet(SkyllaConnection connection, PacketGet packet) {
		PacketGetResponse packetInGetResponse = new PacketGetResponse(packet.getCallbackId(),
			this.cache.get(packet.getKey()));
		connection.sendPackets(packetInGetResponse);
	}
}
