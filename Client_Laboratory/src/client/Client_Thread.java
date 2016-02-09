package client;

import java.net.Socket;

public class Client_Thread extends Thread {
    private Socket socket;

    public Client_Thread(Socket socket) {
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {
        super.run();
    }
}
