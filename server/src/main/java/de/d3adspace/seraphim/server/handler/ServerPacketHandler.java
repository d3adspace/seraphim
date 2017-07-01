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

import de.d3adspace.seraphim.protocol.packet.PacketInGetResponse;
import de.d3adspace.seraphim.protocol.packet.PacketOutGet;
import de.d3adspace.seraphim.protocol.packet.PacketOutInvalidate;
import de.d3adspace.seraphim.protocol.packet.PacketOutPut;
import de.d3adspace.skylla.commons.connection.SkyllaConnection;
import de.d3adspace.skylla.commons.protocol.handler.PacketHandler;
import de.d3adspace.skylla.commons.protocol.handler.PacketHandlerMethod;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class ServerPacketHandler implements PacketHandler {
	
	private final Map<Object, Object> map = new HashMap<Object, Object>();
	
	@PacketHandlerMethod
	public void onPacketPut(SkyllaConnection connection, PacketOutPut packet) {
		System.out.println("Putting: " + packet);
		
		this.map.put(packet.getKey(), packet.getValue());
	}
	
	@PacketHandlerMethod
	public void onPacketInvalidate(SkyllaConnection connection, PacketOutInvalidate packet) {
		System.out.println("Invalidating: " + packet);
		
		this.map.remove(packet.getKey());
	}
	
	@PacketHandlerMethod
	public void onPacketGet(SkyllaConnection connection, PacketOutGet packet) {
		System.out.println("Getting: " + packet);
		
		PacketInGetResponse packetInGetResponse = new PacketInGetResponse(packet.getCallbackId(),
			map.get(packet.getKey()));
		connection.sendPackets(packetInGetResponse);
	}
}
