package server;

import dataBase.*;

import java.io.IOException;
import java.util.List;

public class Buyer extends Thread {
    private Boolean stop = true;
    private DataExchange dataExchange;
    private DataBase dataBase;
    private GroupDAO groupDAO;
    private ProductDAO productDAO;
    private SubGroupDAO subGroupDAO;
    private StatisticsDAO statisticsDAO;

    public Buyer(DataExchange dataExchange, DataBase dataBase) {
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
        String client;

        while (stop) {
            client = dataExchange.acceptString();

            switch (client) {
                case "updateTable":
                    updateTable();
                    break;
                case "exit":
                    try {
                        dataExchange.getSocket().close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void updateTable() {
        /*ProductDAO productDAO = new ProductDAO(dataBase.getConnection());
        GroupDAO groupDAO = new GroupDAO(dataBase.getConnection());
        SubGroupDAO subGroupDAO = new SubGroupDAO(dataBase.getConnection());*/

        List<ProductEntity> listProduct = productDAO.getAll();
        dataExchange.transferInt(listProduct.size());

        for (int i = 0; i < listProduct.size(); i++) {
            dataExchange.transferProductEntity(listProduct.get(i));
        }

        List<GroupEntity> listGroup = groupDAO.getAll();
        dataExchange.transferInt(listGroup.size());

        for (int i = 0; i < listGroup.size(); i++) {
            dataExchange.transferGroupEntity(listGroup.get(i));
        }

        List<SubGroupEntity> listSubGroup = subGroupDAO.getAll();
        dataExchange.transferInt(listSubGroup.size());

        for (int i = 0; i < listSubGroup.size(); i++) {
            dataExchange.transferSubGroupEntity(listSubGroup.get(i));
        }
    }
}
