package server;

import dataBase.DataBase;

import java.io.IOException;
import java.net.*;

public class Server_Thread extends Thread {
    private Socket socket;
    private DataExchange dataExchange;
    private boolean stop = true;
    private int user;
    private boolean incorrectPassword = false;
    DataBase dataBase;

    public Server_Thread(Socket socket, DataBase dataBase) {
        this.socket = socket;
        this.dataBase = dataBase;
        dataExchange = new DataExchange(socket);
        this.start();
    }

    @Override
    public void run() {

        while (stop) {
            switch (dataExchange.acceptString()) {
                case "Exit":
                    close();
                    break;
                case "InitPassword":
                    initPassword();
                    break;
            }
        }

        switch (user) {
            case 0:
                new Buyer(dataExchange, dataBase);
                break;
            case 1:
                new Seller(dataExchange, dataBase);
                break;
            case 2:
                new Administrator(dataExchange, dataBase);
                break;
        }
    }

    private void initPassword() {
        user = dataExchange.acceptInt();

        if (user != 0) {

            switch (user) {
                case 1:

                    if (dataExchange.acceptString().equals("123")) {
                        incorrectPassword = true;
                        stop = false;

                    } else {
                        incorrectPassword = false;
                    }
                    break;
                case 2:

                    if (dataExchange.acceptString().equals("321")) {
                        incorrectPassword = true;
                        stop = false;

                    } else {
                        incorrectPassword = false;
                    }
                    break;
            }
        } else {
            incorrectPassword = true;
            stop = false;

        }
        dataExchange.transferBoolean(incorrectPassword);
    }

    private void close() {
        try {
            dataExchange.getSocket().close();
            stop = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}