package client;

import windows.Entrance;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private int PORT = 8448;

    public Client() {
        try {
            InetAddress address = InetAddress.getByName(null);
            socket = new Socket(address, PORT);

            new Client_Thread(socket);
            new Entrance(socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
