package de.d3adspace.seraphim.server;

public class SeraphimServerBootstrap {

  public static void main(String[] args) {
    SeraphimServer seraphimServer = SeraphimServerFactory.createServer("0.0.0.0", 8080);
    seraphimServer.start();
  }
}
