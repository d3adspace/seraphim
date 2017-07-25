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

package de.d3adspace.seraphim.server;

import de.d3adspace.seraphim.protocol.SeraphimProtocol;
import de.d3adspace.seraphim.server.cache.ServerCache;
import de.d3adspace.seraphim.server.handler.ServerPacketHandler;
import de.d3adspace.skylla.commons.config.SkyllaConfig;
import de.d3adspace.skylla.commons.protocol.Protocol;
import de.d3adspace.skylla.server.SkyllaServer;
import de.d3adspace.skylla.server.SkyllaServerFactory;

/**
 * @author Felix 'SasukeKawaii' Klauke, Nathalie0hneHerz
 */
public class SimpleSeraphimServer implements SeraphimServer {

    /**
     * The underlying server
     */
    private final SkyllaServer skyllaServer;

    /**
     * Creating a new servers.
     *
     * @param host The host.
     * @param port The port.
     */
    SimpleSeraphimServer(String host, int port) {
        ServerCache serverCache = new ServerCache();

        Protocol protocol = SeraphimProtocol.getInstance();
        protocol.registerListener(new ServerPacketHandler(serverCache));

        SkyllaConfig config = SkyllaConfig.newBuilder()
                .setServerHost(host)
                .setServerPort(port)
                .setProtocol(protocol)
                .createSkyllaConfig();

        this.skyllaServer = SkyllaServerFactory.createSkyllaServer(config);
    }

    @Override
    public void start() {
        this.skyllaServer.start();
    }

    @Override
    public void stop() {
        this.skyllaServer.stop();
    }
}
