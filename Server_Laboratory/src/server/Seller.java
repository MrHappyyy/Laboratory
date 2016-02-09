package server;

import dataBase.*;

public class Seller extends Thread {
    private Boolean stop = true;
    private DataExchange dataExchange;
    private DataBase dataBase;
    private GroupDAO groupDAO;
    private ProductDAO productDAO;
    private SubGroupDAO subGroupDAO;
    private StatisticsDAO statisticsDAO;

    public Seller(DataExchange dataExchange, DataBase dataBase) {
        this.dataExchange = dataExchange;
        this.dataBase = dataBase;
        groupDAO = new GroupDAO(dataBase.getConnection());
        productDAO = new ProductDAO(dataBase.getConnection());
        subGroupDAO = new SubGroupDAO(dataBase.getConnection());
        statisticsDAO = new StatisticsDAO(dataBase.getConnection());
        this.start();
    }

    @Override
    public void run() {
        super.run();
    }
}
