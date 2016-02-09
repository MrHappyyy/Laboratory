package main;

import dataBase.*;
import server.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Server server;

    public static void main(String args[]) {
        server = new Server();


        /*DataBase db = new DataBase();
        db.createDateBase("Product.db");

        ProductDAO productDAO = new ProductDAO(db.getConnection());
        GroupDAO groupDAO = new GroupDAO(db.getConnection());
        SubGroupDAO subGroupDAO = new SubGroupDAO(db.getConnection());

        List<ProductEntity> listP = productDAO.getAll();
        List<GroupEntity> listG = groupDAO.getAll();
        List<SubGroupEntity> listS = subGroupDAO.getAll();

        System.out.println("id   nameP    nameG    nameS     amount    price ");

        for (int i = 0; i < listP.size(); i++) {
            System.out.print(i + "     |      ");
            System.out.print(listP.get(i).getProductName() + "     |      ");
            String gr = "";
            for (int j = 0; j < listG.size(); j++) {
                if (listP.get(i).getGroupId() == listG.get(j).getGroupId()) {
                    gr = listG.get(j).getGroupName();
                }
            }
            System.out.print(gr + "     |      ");
            String sg = "";
            for (int j = 0; j < listS.size(); j++) {
                if (listP.get(i).getSubGroupId() == listS.get(j).getSubGroupId()) {
                    sg = listS.get(j).getSubGroupName();
                }
            }
            System.out.print(sg + "     |      ");
            System.out.print(listP.get(i).getAmount() + "     |      ");
            System.out.println(listP.get(i).getPrice());
        }*/

    }
}
