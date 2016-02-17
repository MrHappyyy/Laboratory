package windows;

import client.DataExchange;
import data_base.GroupEntity;
import data_base.ProductEntity;
import data_base.ProductTableEntity;
import data_base.SubGroupEntity;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Buyer implements Runnable {
    private DataExchange dataExchange;
    private JFrame frame;
    private JPanel clarificationPanel, mainPanel;
    private CardLayout layout;

    private ArrayList<ProductEntity> listProduct;
    private ArrayList<GroupEntity> listGroup;
    private ArrayList<SubGroupEntity> listSubGroup;
    private ArrayList<ProductTableEntity> listProductTable;
    private ArrayList<ProductTableEntity> listSearchTable = null;

    /*mainPanel*/
    private JButton searchButton, updateTable;
    private JTextField searchField;
    private ProductTableModel productTableModel;
    private JTable table;
    private JScrollPane scroll;

    public Buyer(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        this.run();
    }

    private void createGUI() {
        frame = new JFrame("Покупець");
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        initPanels();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dataExchange.transferString("exit");
            }
        });
        frame.setVisible(true);
    }

    private void initPanels() {
        initClarificationPanel();
        initMainPanel();
    }

    private void initClarificationPanel() {
        clarificationPanel = new JPanel(new CardLayout());
        layout = (CardLayout) clarificationPanel.getLayout();
        frame.add(clarificationPanel);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(null);

        searchField = new JTextField();
        searchField.setBounds(50, 50, 830, 20);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listSearchTable = null;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchField.addActionListener(new Action());

        searchButton = new JButton();
        searchButton.setBounds(900, 35, 50, 50);
        searchButton.addActionListener(new Action());

        updateTable = new JButton();
        updateTable.setBounds(900, 100, 50, 50);
        updateTable.addActionListener(new Action());

        productTableModel = new ProductTableModel();
        table = new JTable(productTableModel);
        scroll = new JScrollPane(table);
        scroll.setBounds(50, 85, 830, 450);

        mainPanel.add(searchField);
        mainPanel.add(searchButton);
        mainPanel.add(updateTable);
        mainPanel.add(scroll);

        clarificationPanel.add(mainPanel, "mainPanel");
        updateTable();
        layout.show(clarificationPanel, "mainPanel");
    }

    private void updateTable() {
        dataExchange.transferString("updateTable");
        listProduct = new ArrayList<ProductEntity>();
        int size = dataExchange.acceptInt();

        for (int i = 0; i < size; i++) {
            listProduct.add(dataExchange.acceptProductEntity());
        }

        listGroup = new ArrayList<GroupEntity>();
        size = dataExchange.acceptInt();

        for (int i = 0; i < size; i++) {
            listGroup.add(dataExchange.acceptGroupEntity());
        }

        listSubGroup = new ArrayList<SubGroupEntity>();
        size = dataExchange.acceptInt();

        for (int i = 0; i < size; i++) {
            listSubGroup.add(dataExchange.acceptSubGroupEntity());
        }

        listProductTable = new ArrayList<ProductTableEntity>();

        for (int i = 0; i < listProduct.size(); i++) {
            ProductTableEntity entity = new ProductTableEntity();

            for (int j = 0; j < listGroup.size(); j++) {

                if (listProduct.get(i).getGroupId() == listGroup.get(j).getGroupId()) {
                    entity.setGroupName(listGroup.get(j).getGroupName());
                    break;
                }
            }

            for (int j = 0; j < listSubGroup.size(); j++) {

                if (listProduct.get(i).getSubGroupId() == listSubGroup.get(j).getSubGroupId()) {
                    entity.setSubGroupName(listSubGroup.get(j).getSubGroupName());
                    break;
                }
            }
            entity.setProductId(i + 1);
            entity.setProductName(listProduct.get(i).getProductName());
            entity.setAmount(listProduct.get(i).getAmount());
            entity.setPrice(listProduct.get(i).getPrice());
            listProductTable.add(entity);
        }
        productTableModel.removeIsAll();

        for (int i = 0; i < listProductTable.size(); i++) {
            String[] table = {String.valueOf(listProductTable.get(i).getProductId()),
                    listProductTable.get(i).getProductName(),
                    listProductTable.get(i).getGroupName(),
                    listProductTable.get(i).getSubGroupName(),
                    String.valueOf(listProductTable.get(i).getAmount()),
                    String.valueOf(listProductTable.get(i).getPrice())};
            productTableModel.addDate(table);
        }
        table.setVisible(false);
        table.setVisible(true);
    }

    @Override
    public void run() {
        createGUI();
    }

    public class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

           if (e.getSource() == searchField || e.getSource() == searchButton) {

                if (!searchField.getText().equals("")) {

                    if (listSearchTable == null) {
                        listSearchTable = listProductTable;
                    }
                    String searchText = searchField.getText().toLowerCase();
                    ArrayList<ProductTableEntity> list = new ArrayList<ProductTableEntity>();

                    for (int i = 0; i < listSearchTable.size(); i++) {
                        boolean isSearchProductName = true;
                        String productName = listSearchTable.get(i).getProductName().toLowerCase();

                        for (int j = 0; j < searchText.length(); j++) {

                            if (isSearchProductName) {
                                for (int k = 0; k < productName.length(); k++) {

                                    if (searchText.charAt(j) == productName.charAt(k)) {
                                        isSearchProductName = true;
                                        break;
                                    } else {
                                        isSearchProductName = false;
                                    }
                                }
                            }
                        }

                        if (isSearchProductName) {
                            list.add(listSearchTable.get(i));
                        }
                    }
                    listSearchTable = list;
                    productTableModel.removeIsAll();

                    for (int i = 0; i < listSearchTable.size(); i++) {
                        String[] table = {String.valueOf(listSearchTable.get(i).getProductId()),
                                listSearchTable.get(i).getProductName(),
                                listSearchTable.get(i).getGroupName(),
                                listSearchTable.get(i).getSubGroupName(),
                                String.valueOf(listSearchTable.get(i).getAmount()),
                                String.valueOf(listSearchTable.get(i).getPrice())};
                        productTableModel.addDate(table);
                    }
                    table.setVisible(false);
                    table.setVisible(true);
                } else {
                    searchField.setText(null);
                    updateTable();
                }
           } else if (e.getSource() == updateTable) {
               searchField.setText(null);
               updateTable();
           }
        }
    }
}
