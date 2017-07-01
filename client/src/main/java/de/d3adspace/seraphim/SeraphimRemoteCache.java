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

package de.d3adspace.seraphim;

import de.d3adspace.seraphim.cache.Cache;
import de.d3adspace.seraphim.handler.SeraphimClientPacketHandler;
import de.d3adspace.seraphim.protocol.SeraphimProtocol;
import de.d3adspace.seraphim.protocol.packet.PacketInGetResponse;
import de.d3adspace.seraphim.protocol.packet.PacketOutGet;
import de.d3adspace.seraphim.protocol.packet.PacketOutInvalidate;
import de.d3adspace.seraphim.protocol.packet.PacketOutPut;
import de.d3adspace.skylla.client.SkyllaClient;
import de.d3adspace.skylla.client.SkyllaClientFactory;
import de.d3adspace.skylla.commons.config.SkyllaConfig;
import de.d3adspace.skylla.commons.config.SkyllaConfigBuilder;
import de.d3adspace.skylla.commons.protocol.Protocol;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SeraphimRemoteCache<KeyType, ValueType> implements Cache<KeyType, ValueType> {
	
	/**
	 * The underlying skylla client.
	 */
	private final SkyllaClient skyllaClient;
	
	SeraphimRemoteCache(String serverHost, int serverPort) {
		Protocol protocol = new SeraphimProtocol();
		protocol.registerListener(new SeraphimClientPacketHandler());
		
		SkyllaConfig config = new SkyllaConfigBuilder()
			.setProtocol(protocol)
			.setServerHost(serverHost)
			.setServerPort(serverPort)
			.createSkyllaConfig();
		
		this.skyllaClient = SkyllaClientFactory.createSkyllaClient(config);
		this.skyllaClient.connect();
	}
	
	@Override
	public void put(KeyType key, ValueType value) {
		this.put(key, value, -1);
	}
	
	@Override
	public void put(KeyType key, ValueType value, long timeToLive) {
		PacketOutPut packetPut = new PacketOutPut(key, value, timeToLive);
		this.skyllaClient.sendPacket(packetPut);
	}
	
	@Override
	public ValueType get(KeyType key) {
		AtomicReference<PacketInGetResponse> atomicReference = new AtomicReference<>(null);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		int callbackId = Seraphim.getHawkings().incrementAndGetId();
		
		Consumer<PacketInGetResponse> consumer = value -> {
			atomicReference.set(value);
			countDownLatch.countDown();
		};
		
		Seraphim.getHawkings().registerConsumer(consumer);
		
		PacketOutGet packet = new PacketOutGet(callbackId, key);
		this.skyllaClient.sendPacket(packet);
		
		try {
			countDownLatch.await(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return (ValueType) atomicReference.get().getValue();
	}
	
	@Override
	public boolean isPresent(KeyType key) {
		return get(key) != null;
	}
	
	@Override
	public void invalidate(KeyType key) {
		PacketOutInvalidate packet = new PacketOutInvalidate(key);
		this.skyllaClient.sendPacket(packet);
	}
}
