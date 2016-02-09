package server;

import dataBase.*;

import java.io.IOException;
import java.util.List;

public class Administrator extends Thread {
    private Boolean stop = true;
    private DataExchange dataExchange;
    private DataBase dataBase;
    private GroupDAO groupDAO;
    private ProductDAO productDAO;
    private SubGroupDAO subGroupDAO;
    private StatisticsDAO statisticsDAO;

    public Administrator(DataExchange dataExchange, DataBase dataBase) {
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

        while (stop) {

            switch (dataExchange.acceptString()) {
                case "updateTable":
                    updateTable();
                    break;
                case "addProduct":
                    addProduct();
                    break;
                case "delProduct":
                    delProduct();
                    break;
                case "editProduct":
                    editProduct();
                    break;
                case "addGroup":
                    addGroup();
                    break;
                case "delGroup":
                    delGroup();
                    break;
                case "editGroup":
                    editGroup();
                    break;
                case "addSubGroup":
                    addSubGroup();
                    break;
                case "delSubGroup":
                    delSubGroup();
                    break;
                case "editSubGroup":
                    editSubGroup();
                    break;
                case "statistics":
                    statistics();
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

    private void addProduct() {

        while (true) {
            String s = dataExchange.acceptString();
            if (s.equals("back")) {
                return;
            } else if (s.equals("add")) {
                ProductEntity entity = dataExchange.acceptProductEntity();
                productDAO.add(entity);
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void delProduct() {

        while (true) {
            String s = dataExchange.acceptString();
            if (s.equals("back")) {
                return;
            } else if (s.equals("del")) {
                productDAO.delete(dataExchange.acceptInt());
                return;
            }  else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void editProduct() {

        while (true) {
            String s = dataExchange.acceptString();
            if (s.equals("back")) {
                return;
            } else if (s.equals("edit")) {
                ProductEntity entity = dataExchange.acceptProductEntity();
                productDAO.update(dataExchange.acceptInt(), entity);
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addGroup() {

        while (true) {
            String s = dataExchange.acceptString();
            if (s.equals("back")) {
                return;
            } else if (s.equals("add")) {
                GroupEntity entity = new GroupEntity();
                entity.setGroupName(dataExchange.acceptString());
                GroupDAO groupDAO = new GroupDAO(dataBase.getConnection());
                groupDAO.add(entity);
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void delGroup() {

        while (true) {
            String s = dataExchange.acceptString();

            if (s.equals("back")) {
                return;
            } else if (s.equals("del")) {
                groupDAO.delete(dataExchange.acceptInt());
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void editGroup() {

        while (true) {
            String s = dataExchange.acceptString();

            if (s.equals("back")) {
                return;
            } else if (s.equals("edit")) {
                GroupEntity entity = new GroupEntity();
                entity.setGroupName(dataExchange.acceptString());
                groupDAO.update(dataExchange.acceptInt(), entity);
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addSubGroup() {

        while (true) {
            String s = dataExchange.acceptString();

            if (s.equals("back")) {
                return;
            } else if (s.equals("add")) {
                SubGroupEntity entity = dataExchange.acceptSubGroupEntity();
                subGroupDAO.add(entity);
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void delSubGroup() {

        while (true) {
            String s = dataExchange.acceptString();

            if (s.equals("back")) {
                return;
            } else if (s.equals("del")) {
                subGroupDAO.delete(dataExchange.acceptInt());
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void editSubGroup() {

        while (true) {
            String s = dataExchange.acceptString();

            if (s.equals("back")) {
                return;
            } else if (s.equals("edit")) {
                int id = dataExchange.acceptInt();
                SubGroupEntity entity = dataExchange.acceptSubGroupEntity();
                subGroupDAO.update(id, entity);

                if (dataExchange.acceptBoolean()) {
                    int size = dataExchange.acceptInt();

                    for (int i = 0; i < size; i++) {
                        ProductEntity productEntity = dataExchange.acceptProductEntity();
                        productDAO.update(productEntity.getProductId(), productEntity);
                    }
                }
                return;
            } else  if (s.equals("exit")) {
                try {
                    dataExchange.getSocket().close();
                    stop = false;
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private void statistics() {
        List<StatisticsEntity> statisticsEntityList = statisticsDAO.getAll();
        dataExchange.transferInt(statisticsEntityList.size());

        for (int i = 0; i < statisticsEntityList.size(); i++) {
            dataExchange.transferStatisticsEntity(statisticsEntityList.get(i));
        }
    }
}
