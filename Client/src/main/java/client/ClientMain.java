package client;

import database.memory.MemoryGameDAO;

public class ClientMain {

  public static void main(String[] args) {

    Client client = new Client(new MemoryGameDAO());
    client.run();

  }

}
