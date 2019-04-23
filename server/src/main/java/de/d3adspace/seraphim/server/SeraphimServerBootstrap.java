package de.d3adspace.seraphim.server;

public class SeraphimServerBootstrap {

    public static void main(String[] args) {
        SimpleSeraphimServer simpleSeraphimServer = new SimpleSeraphimServer("0.0.0.0", 8080);
        simpleSeraphimServer.start();
    }
}
