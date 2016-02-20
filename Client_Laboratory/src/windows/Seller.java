package windows;

import client.DataExchange;
import data_base.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Seller implements Runnable {
    private DataExchange dataExchange;
    private JFrame frame;
    private JPanel clarificationPanel, mainPanel, basketPanel;
    private CardLayout layout;

    private ArrayList<ProductEntity> listProduct;
    private ArrayList<GroupEntity> listGroup;
    private ArrayList<SubGroupEntity> listSubGroup;
    private ArrayList<ProductTableEntity> listProductTable;
    private ArrayList<ProductTableEntity> listSearchTable = null;
    private ArrayList<ProductEntity> basketList = new ArrayList<ProductEntity>();
    private ArrayList<Integer> idProductList = new ArrayList<Integer>();

    /*mainPanel*/
    private JLabel enterAmountProduct, incorrectAmount;
    private JButton basket, addBasket, searchButton, updateTable;
    private JTextField searchField, amountProductSell;
    private ProductTableModel productTableModel;
    private JTable table;
    private JScrollPane scroll;

    /*basketPanel*/
    private JButton delProductInBasket, sellProduct;
    private JLabel sellPrice;
    private BasketProductTableModel basketProductTableModel;
    private JTable basketTable;
    private JScrollPane basketScroll;


    public Seller(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        this.run();
    }

    private void createGUI() {
        frame = new JFrame("Продавець");
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
        initBasketPanels();
    }

    private void initClarificationPanel() {
        clarificationPanel = new JPanel(new CardLayout());
        layout = (CardLayout) clarificationPanel.getLayout();
        frame.add(clarificationPanel);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(null);

        searchField = new JTextField();
        searchField.setBounds(200, 50, 680, 20);
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
        scroll.setBounds(200, 85, 680, 450);

        basket = new JButton("Корзина(0)");
        basket.setBounds(25, 50, 150, 20);
        basket.addActionListener(new Action());

        enterAmountProduct = new JLabel("Введіть кількість:");
        enterAmountProduct.setBounds(25, 90, 150, 20);
        enterAmountProduct.setHorizontalAlignment(SwingConstants.LEFT);

        amountProductSell = new JTextField();
        amountProductSell.setBounds(50, 130, 100, 20);
        amountProductSell.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextFieldIsDouble(amountProductSell);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                checkTextFieldIsDouble(amountProductSell);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkTextFieldIsDouble(amountProductSell);
            }
        });
        amountProductSell.addActionListener(new Action());

        addBasket = new JButton("до корзини");
        addBasket.setBounds(25, 170, 150, 20);
        addBasket.addActionListener(new Action());

        incorrectAmount = new JLabel();
        incorrectAmount.setBounds(0, 210, 200, 40);
        incorrectAmount.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(searchField);
        mainPanel.add(searchButton);
        mainPanel.add(updateTable);
        mainPanel.add(scroll);
        mainPanel.add(basket);
        mainPanel.add(enterAmountProduct);
        mainPanel.add(amountProductSell);
        mainPanel.add(addBasket);
        mainPanel.add(incorrectAmount);

        clarificationPanel.add(mainPanel, "mainPanel");
        updateTable();
        layout.show(clarificationPanel, "mainPanel");
    }

    private void initBasketPanels() {
        basketPanel = new JPanel(null);

        JButton back = new JButton("<-Назад");
        back.setBounds(25, 25, 100, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
                table.setVisible(false);
                table.setVisible(true);
            }
        });

        delProductInBasket = new JButton("Видалити з корзини");
        delProductInBasket.setBounds(200, 50, 200, 20);
        delProductInBasket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (basketProductTableModel.getRowCount() != 0 & basketTable.getSelectedRow() != -1) {
                    boolean b = true;

                    for (int i = 0; i < productTableModel.getRowCount(); i++) {

                        if (basketList.get(basketTable.getSelectedRow()).getProductName().equals(productTableModel.getValueAt(i, 1))) {
                            double price = Double.parseDouble(productTableModel.getValueAt(i, 4)) + Double.parseDouble(basketProductTableModel.getValueAt(basketTable.getSelectedRow(), 2));
                            productTableModel.setValueAt(price, i, 4);
                            b =false;
                            break;
                        }
                    }

                    if (b) {
                        String[] row = new String[productTableModel.getColumnCount()];
                        ProductEntity entity = listProduct.get(idProductList.get(basketTable.getSelectedRow()));
                        row[0] = String.valueOf(productTableModel.getRowCount() + 2);
                        row[1] = entity.getProductName();
                        for (int i = 0; i < listGroup.size(); i++) {
                            if (listGroup.get(i).getGroupId() == entity.getGroupId()) {
                                row[2] = listGroup.get(i).getGroupName();
                            }
                        }
                        for (int i = 0; i < listSubGroup.size(); i++) {
                            if (listSubGroup.get(i).getSubGroupId() == entity.getSubGroupId()) {
                                row[3] = listSubGroup.get(i).getSubGroupName();
                                break;
                            }
                        }
                        row[4] = String.valueOf(entity.getAmount());
                        row[5] = String.valueOf(entity.getPrice());
                        productTableModel.addDate(row);
                    }
                    basketList.remove(basketTable.getSelectedRow());
                    basketProductTableModel.removeIsRows(basketTable.getSelectedRow());
                    idProductList.remove(basketTable.getSelectedRow());
                    String s = "Корзина(" + basketList.size() + ")";
                    basket.setText(s);

                    double overallSellPrice = 0.0;

                    for (int i = 0; i < basketProductTableModel.getRowCount(); i++) {
                        overallSellPrice += Double.parseDouble(basketProductTableModel.getValueAt(i, 3));
                    }
                    sellPrice.setText("Загальна ціна: " + overallSellPrice);
                    basketPanel.setVisible(false);
                    basketPanel.setVisible(true);
                }
            }
        });

        sellPrice = new JLabel();
        sellPrice.setBounds(420, 50, 200, 20);

        sellProduct = new JButton("Продати");
        sellProduct.setBounds(640, 50, 200, 20);
        sellProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("sell");
                dataExchange.transferInt(basketList.size());

                for (int i = 0; i < basketList.size(); i++) {

                    for (int k = 0; k < listProduct.size(); k++) {

                        if (listProduct.get(k).getProductName().equals(basketList.get(i).getProductName())) {
                            dataExchange.transferInt(listProduct.get(k).getProductId());
                            break;
                        }
                    }
                    dataExchange.transferProductEntity(basketList.get(i));
                }

                dataExchange.transferInt(basketProductTableModel.getRowCount());

                for (int i = 0; i < basketProductTableModel.getRowCount(); i++) {
                    StatisticsEntity entity = new StatisticsEntity();
                    entity.setName(basketProductTableModel.getValueAt(i, 1));
                    entity.setAmountSell(Double.parseDouble(basketProductTableModel.getValueAt(i, 2)));
                    entity.setPrice(listProduct.get(idProductList.get(i)).getPrice());
                    entity.setPriceSell(Double.parseDouble(basketProductTableModel.getValueAt(i, 3)));

                    for (int j = 0; j < listGroup.size(); j++) {

                        if (basketList.get(i).getGroupId() == listGroup.get(j).getGroupId()) {
                            entity.setGroup(listGroup.get(j).getGroupName());
                            break;
                        }
                    }
                    for (int j = 0; j < listSubGroup.size(); j++) {

                        if (basketList.get(i).getSubGroupId() == listSubGroup.get(j).getSubGroupId()) {
                            entity.setSubGroup(listSubGroup.get(j).getSubGroupName());
                            break;
                        }
                    }
                    Locale local = new Locale("ru","RU");
                    DateFormat df = DateFormat.getDateInstance(DateFormat.LONG , local);
                    Date date = new Date();
                    entity.setData(df.format(date));
                    dataExchange.transferStatisticsEntity(entity);
                }
                idProductList = new ArrayList<Integer>();

                for (int i = 0; i < productTableModel.getRowCount(); i++) {

                    if (Double.parseDouble(productTableModel.getValueAt(i, 4)) == 0.0) {

                        for (int k = 0; k < listProduct.size(); k++) {

                            if (listProduct.get(k).getProductName().equals(productTableModel.getValueAt(i, 1))) {
                                idProductList.add(listProduct.get(k).getProductId());
                                break;
                            }
                        }
                    }
                }
                dataExchange.transferInt(idProductList.size());

                for (int i = 0; i < idProductList.size(); i++) {
                    dataExchange.transferInt(idProductList.get(i));
                }
                basketList = new ArrayList<ProductEntity>();
                idProductList = new ArrayList<Integer>();
                basketProductTableModel.removeIsAll();
                updateTable();
                String s = "Корзина(" + basketList.size() + ")";
                basket.setText(s);
                layout.show(clarificationPanel, "mainPanel");
            }
        });

        basketProductTableModel = new BasketProductTableModel();
        basketTable = new JTable(basketProductTableModel);
        basketScroll = new JScrollPane(basketTable);
        basketScroll.setBounds(100, 100, 800, 400);

        basketPanel.add(back);
        basketPanel.add(delProductInBasket);
        basketPanel.add(sellPrice);
        basketPanel.add(sellProduct);
        basketPanel.add(basketScroll);

        clarificationPanel.add(basketPanel, "basketPanel");
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

    private void checkTextFieldIsDouble(JTextField textField) {
        String allowedCharacters = "0123456789";
        String getText = textField.getText();
        String setText = "";
        boolean isDote = true;

        for (int i = 0; i < getText.length(); i++) {
            String s = String.valueOf(getText.charAt(i));
            if (s.equals(".") && isDote && !setText.equals("")) {
                setText += s;
                isDote = false;
            } else {
                for (int j = 0; j < allowedCharacters.length(); j++) {
                    if (s.equals(String.valueOf(allowedCharacters.charAt(j)))) {
                        setText += s;
                        break;
                    }
                }
            }
        }
        textField.setText(setText);
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
                basketList = new ArrayList<ProductEntity>();
                String b = "Корзина(" + basketList.size() + ")";
                basket.setText(b);
                updateTable();
            } else if (e.getSource() == basket) {
                dataExchange.transferString("basket");
                basketProductTableModel.removeIsAll();

                for (int i = 0; i < basketList.size(); i++) {
                    String row[] = new String[basketProductTableModel.getColumnCount()];
                    row[0] = String.valueOf(i + 1);
                    row[1] = basketList.get(i).getProductName();
                    double amount = listProduct.get(idProductList.get(i)).getAmount() - basketList.get(i).getAmount();
                    row[2] = String.valueOf(amount);
                    double price = amount * basketList.get(i).getPrice();
                    row[3] = String.valueOf(price);
                    basketProductTableModel.addDate(row);
                }
                double overallSellPrice = 0.0;

                for (int i = 0; i < basketProductTableModel.getRowCount(); i++) {
                    overallSellPrice += Double.parseDouble(basketProductTableModel.getValueAt(i, 3));
                }
                sellPrice.setText("Загальна ціна: " + overallSellPrice);
                basketTable.setVisible(false);
                basketTable.setVisible(true);
                layout.show(clarificationPanel, "basketPanel");
            } else if (e.getSource() == addBasket || e.getSource() == amountProductSell) {
                incorrectAmount.setText(null);

                if (!amountProductSell.getText().equals("")) {

                    if (table.getSelectedRow() != -1 && productTableModel.getRowCount() != 0) {

                        if (Double.parseDouble(amountProductSell.getText()) != 0.0) {
                            if (Double.parseDouble(amountProductSell.getText()) <= Double.parseDouble(productTableModel.getValueAt(table.getSelectedRow(), 4))) {

                                for (int i = 0; i < listProduct.size(); i++) {

                                    if (productTableModel.getValueAt(table.getSelectedRow(), 1).equals(listProduct.get(i).getProductName())) {
                                        boolean isAdd = true;

                                        for (int k = 0; k < basketList.size(); k++) {

                                            if (basketList.get(k).getProductName().equals(listProduct.get(i).getProductName())) {
                                                isAdd = false;
                                                double am = basketList.get(k).getAmount();
                                                basketList.get(k).setAmount(am - Double.parseDouble(amountProductSell.getText()));
                                                productTableModel.setValueAt(String.valueOf(am - Double.parseDouble(amountProductSell.getText())), table.getSelectedRow(), 4);
                                                table.setVisible(false);
                                                table.setVisible(true);
                                                break;
                                            }
                                        }

                                        if (isAdd) {
                                            ProductEntity entity = new ProductEntity();
                                            entity.setProductName(listProduct.get(i).getProductName());
                                            entity.setPrice(listProduct.get(i).getPrice());
                                            entity.setAmount(listProduct.get(i).getAmount() - Double.parseDouble(amountProductSell.getText()));
                                            productTableModel.setValueAt(String.valueOf(listProduct.get(i).getAmount() - Double.parseDouble(amountProductSell.getText())), table.getSelectedRow(), 4);
                                            entity.setGroupId(listProduct.get(i).getGroupId());
                                            entity.setSubGroupId(listProduct.get(i).getSubGroupId());
                                            entity.setProductId(listProduct.get(i).getProductId());
                                            basketList.add(entity);
                                            String b = "Корзина(" + basketList.size() + ")";
                                            basket.setText(b);
                                            table.setVisible(false);
                                            table.setVisible(true);
                                            idProductList.add(i);
                                        }

                                        if (Double.parseDouble(productTableModel.getValueAt(table.getSelectedRow(), 4)) == 0.0) {
                                            productTableModel.removeIsRows(table.getSelectedRow());
                                        }
                                        amountProductSell.setText(null);
                                        break;
                                    }
                                }
                            } else {
                                incorrectAmount.setText("не вистачає товару");
                            }
                        } else {
                            incorrectAmount.setText("ви ввели 0");
                        }
                    } else {
                        incorrectAmount.setText("виберіть товар");
                    }

                } else {
                    incorrectAmount.setText("поле повино бути заповнене");
                }
            }
        }
    }
}
