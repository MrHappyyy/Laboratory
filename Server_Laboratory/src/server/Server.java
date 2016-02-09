package server;

import dataBase.DataBase;

import java.io.IOException;
import java.net.*;

public class Server extends Thread {
    public boolean stop = true;
    public final int PORT = 8448;

    public Server() {
        this.start();
    }

    @Override
    public void run() {
        DataBase dataBase = new DataBase();
        dataBase.createDateBase("Product.db");

        try {
            ServerSocket server = new ServerSocket(PORT);

            while (stop) {
                Socket socket = server.accept();
                new Server_Thread(socket, dataBase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
