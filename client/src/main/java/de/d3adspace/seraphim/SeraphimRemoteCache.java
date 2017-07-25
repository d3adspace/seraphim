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
import de.d3adspace.seraphim.protocol.packet.*;
import de.d3adspace.skylla.client.SkyllaClient;
import de.d3adspace.skylla.client.SkyllaClientFactory;
import de.d3adspace.skylla.commons.config.SkyllaConfig;
import de.d3adspace.skylla.commons.protocol.Protocol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    SeraphimRemoteCache(String serverHost, int serverPort) {
        if (serverHost == null) {
            throw new IllegalArgumentException("serverHost cannot be null");
        }

        Protocol protocol = SeraphimProtocol.getInstance();
        protocol.registerListener(SeraphimClientPacketHandler.getInstance());

        SkyllaConfig config = SkyllaConfig.newBuilder()
                .setProtocol(protocol)
                .setServerHost(serverHost)
                .setServerPort(serverPort)
                .createSkyllaConfig();

        this.skyllaClient = SkyllaClientFactory.createSkyllaClient(config);
        this.skyllaClient.connect();
    }

    @Override
    public void put(KeyType key, ValueType value) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.put(key, value, -1);
    }

    @Override
    public void put(KeyType key, ValueType value, long timeToLive) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        PacketPut packetPut = new PacketPut(key, value, timeToLive);
        this.skyllaClient.sendPacket(packetPut);
    }

    @Override
    public ValueType get(KeyType key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        int callbackId = Seraphim.getHawkings().incrementAndGetId();

        AtomicReference<PacketGetResponse> atomicReference = new AtomicReference<>(null);
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Consumer<PacketGetResponse> consumer = value -> {
            atomicReference.set(value);
            countDownLatch.countDown();
        };

        Seraphim.getHawkings().registerConsumer(consumer);

        PacketGet packet = new PacketGet(callbackId, key);
        this.skyllaClient.sendPacket(packet);

        try {
            countDownLatch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PacketGetResponse response = atomicReference.get();

        if (response == null) {
            return null;
        }

        return (ValueType) response.getValue();
    }

    @Override
    public void get(KeyType key, Consumer<ValueType> consumer) {
        executorService.execute(() -> {
            int callbackId = Seraphim.getHawkings().incrementAndGetId();
            Seraphim.getHawkings().registerConsumer(new Consumer<PacketGetResponse>() {
                @Override
                public void accept(PacketGetResponse packetGetResponse) {
                    consumer.accept((ValueType) packetGetResponse.getValue());
                }
            });

            PacketGet packet = new PacketGet(callbackId, key);
            skyllaClient.sendPacket(packet);
        });
    }

    @Override
    public boolean isPresent(KeyType key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        return get(key) != null;
    }

    @Override
    public void invalidate(KeyType key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        PacketInvalidate packet = new PacketInvalidate(key);
        this.skyllaClient.sendPacket(packet);
    }

    @Override
    public void invalidateAll() {
        PacketClear packet = new PacketClear();
        this.skyllaClient.sendPacket(packet);
    }
}
