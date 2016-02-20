package windows;

import client.DataExchange;
import data_base.StatisticsEntity;
import data_base.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Administrator implements Runnable {
    private DataExchange dataExchange;
    private JFrame frame;
    private JPanel addGroupPanel, delGroupPanel, editGroupPanel,
                   addSubGroupPanel, delSubGroupPanel, editSubGroupPanel,
                   addProductPanel, delProductPanel, editProductPanel,
                   clarificationPanel, mainPanel, statisticsPanel;
    private CardLayout layout;

    private ArrayList<StatisticsEntity> statisticsList;
    private ArrayList<StatisticsEntity> statisticsSearchList = null;
    private ArrayList<ProductEntity> listProduct;
    private ArrayList<GroupEntity> listGroup;
    private ArrayList<SubGroupEntity> listSubGroup;
    private ArrayList<ProductTableEntity> listProductTable;
    private ArrayList<ProductTableEntity> listSearchTable = null;

    /*mainPanel*/
    private JButton addGroup, delGroup, editGroup,
            addSubGroup, delSubGroup, editSubGroup,
            addProduct, delProduct, editProduct,
            statistics, searchButton, updateTable;
    private JTextField searchField;
    private JLabel productLabel, groupLabel, subGroupLabel;
    private ProductTableModel productTableModel;
    private JTable table;
    private JScrollPane scroll;

    /*addProduct*/
    private JComboBox groupBoxAP, subGroupBoxAP;
    private DefaultComboBoxModel groupBoxModelAP, subGroupBoxModelAP;
    private JTextField productNameAP, amountTextFieldAP, priceTextFieldAP;
    private JLabel groupLabelAP, subGroupLabelAP, productNameLabelAP, amountLabelAP,
            priceLabelAP, incorrectNameLabelAP, incorrectEmptyFieldsLabelAP;
    private JButton addAP;

    /*delProduct*/
    private JLabel groupLabelDP, subGroupLabelDP, productLabelDP;
    private DefaultComboBoxModel groupBoxModelDP, subGroupBoxModelDP, productBoxModelDP;
    private JComboBox groupBoxDP, subGroupBoxDP, productBoxDP;
    private JButton delDP;

    /*editProduct*/
    private JLabel groupLabelEP, subGroupLabelEP, productLabelEP, newGroupLabelEP, newSubGroupLabelEP,
            newProductNameLabelEP, newAmountEP, newPriceEP, incorrectNameLabelEP, incorrectEmptyFieldsLabelEP;
    private DefaultComboBoxModel groupBoxModelEP, subGroupBoxModelEP, productBoxModelEP, newGroupBoxModelEP, newSubGroupBoxModelEP;
    private JComboBox groupBoxEP, subGroupBoxEP, productBoxEP, newGroupBoxEP, newSubGroupBoxEP;
    private JTextField newProductNameFieldEP, newProductAmountFieldEP, newProductPriceFieldEP;
    private JButton editProductEP;

    /*addGroup*/
    private JLabel groupNameAG, incorrectGroupNameAG;
    private JTextField groupNameFieldAG;
    private JButton addGroupNameAG;

    /*delGroup*/
    private JLabel groupNameDG, incorrectGroupNameDG;
    private JComboBox groupDG;
    private DefaultComboBoxModel groupComboBoxModelDG;
    private JButton delGroupDG;

    /*editGroup*/
    private JLabel enterGroupEG, enterNewGroupNameEG, incorrectGroupNameEG;
    private DefaultComboBoxModel groupComboBoxModelEG;
    private JComboBox groupEG;
    private JTextField newGroupNameTextFieldEG;
    private JButton enterNewGroupNameButtonEG;

    /*addSubGroupPanel*/
    private JLabel enterGroupASG, enterSubGroupNameASG, incorrectSubGroupNameASG;
    private DefaultComboBoxModel groupComboBoxModelASG;
    private JComboBox groupASG;
    private JTextField subGroupNameFieldASG;
    private JButton addSubGroupASG;

    /*delSubGroup*/
    private JLabel enterGroupDSG, enterSubGroupDSG, incorrectDelSubGroupDSG;
    private DefaultComboBoxModel groupComboBoxModelDSG, subGroupComboBoxModelDSG;
    private JComboBox groupComboBoxDSG, subGroupComboBoxDSG;
    private JButton delSubGroupButtonDSG;

    /*editSubGroup*/
    private JLabel enterGroupESG, enterSubGroupESG, enterNewGroupESG,enterNewNameSubGroupESG, incorrectNewNameSubGroupESG;
    private DefaultComboBoxModel groupComboBoxModelESG, subGroupComboBoxModelESG, newGroupComboBoxModelESG;
    private JComboBox groupComboBoxESG, subGroupComboBoxESG, newGroupComboBoxESG;
    private JTextField newNameSubGroupESG;
    private JButton editSubGroupESG;

    /*statistics*/
    private StatisticsTableModel statisticsTableModel;
    private JTable statisticsTable;
    private JScrollPane statisticsScroll;
    private JTextField statisticsSearchField;
    private JButton statisticsSearchButton;

    private ImageIcon iconAdd;
    private ImageIcon iconDel;
    private ImageIcon iconEdit;
    private ImageIcon iconSearch;
    private ImageIcon iconRefresh;


    public Administrator(DataExchange dataExchange) {
        this.dataExchange = dataExchange;
        this.run();
    }

    private void createGUI() {
        frame = new JFrame("Адміністратор");
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
        initAddProductPanel();
        initDelProductPanel();
        initEditProductPanel();
        initAddGroupPanel();
        initDelGroupPanel();
        initEditGroupPanel();
        initAddSubGroupPanel();
        initDelSubGroupPanel();
        initEditSubGroupPanel();
        initStatisticsPanel();
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

    private void initClarificationPanel() {
        clarificationPanel = new JPanel(new CardLayout());
        layout = (CardLayout) clarificationPanel.getLayout();
        frame.add(clarificationPanel);
    }

    private void initMainPanel() {
        mainPanel = new JPanel(null);

        iconAdd = new ImageIcon("/home/mrhappyyy/Programing/Java/Project/Laboratory/Client_Laboratory/src/windows/add.png");
        iconDel = new ImageIcon("/home/mrhappyyy/Programing/Java/Project/Laboratory/Client_Laboratory/src/windows/del.png");
        iconEdit = new ImageIcon("/home/mrhappyyy/Programing/Java/Project/Laboratory/Client_Laboratory/src/windows/edit.png");
        iconSearch = new ImageIcon("/home/mrhappyyy/Programing/Java/Project/Laboratory/Client_Laboratory/src/windows/search.png");
        iconRefresh = new ImageIcon("/home/mrhappyyy/Programing/Java/Project/Laboratory/Client_Laboratory/src/windows/refresh.png");

        productLabel = new JLabel("Товари");
        productLabel.setHorizontalAlignment(JLabel.CENTER);
        productLabel.setBounds(50, 50, 190, 20);

        addProduct = new JButton(iconAdd);
        addProduct.setBounds(50, 90, 50, 50);
        addProduct.addActionListener(new Action());
        delProduct = new JButton(iconDel);
        delProduct.setBounds(120, 90, 50, 50);
        delProduct.addActionListener(new Action());
        editProduct = new JButton(iconEdit);
        editProduct.setBounds(190, 90, 50, 50);
        editProduct.addActionListener(new Action());

        groupLabel = new JLabel("Групи");
        groupLabel.setHorizontalAlignment(JLabel.CENTER);
        groupLabel.setBounds(50, 170, 190, 20);

        addGroup = new JButton(iconAdd);
        addGroup.setBounds(50, 210, 50, 50);
        addGroup.addActionListener(new Action());
        delGroup = new JButton(iconDel);
        delGroup.setBounds(120, 210, 50, 50);
        delGroup.addActionListener(new Action());
        editGroup = new JButton(iconEdit);
        editGroup.setBounds(190, 210, 50, 50);
        editGroup.addActionListener(new Action());

        subGroupLabel = new JLabel("Підгрупи");
        subGroupLabel.setHorizontalAlignment(JLabel.CENTER);
        subGroupLabel.setBounds(50, 290, 190, 20);

        addSubGroup = new JButton(iconAdd);
        addSubGroup.setBounds(50, 330, 50, 50);
        addSubGroup.addActionListener(new Action());
        delSubGroup = new JButton(iconDel);
        delSubGroup.setBounds(120, 330, 50, 50);
        delSubGroup.addActionListener(new Action());
        editSubGroup = new JButton(iconEdit);
        editSubGroup.setBounds(190, 330, 50, 50);
        editSubGroup.addActionListener(new Action());

        statistics = new JButton("Статистика");
        statistics.setBounds(75, 450, 140, 30);
        statistics.addActionListener(new Action());

        searchField = new JTextField();
        searchField.setBounds(260, 50, 645, 25);
        searchField.addActionListener(new Action());
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

        searchButton = new JButton(iconSearch);
        searchButton.setBounds(920, 37, 50, 50);
        searchButton.addActionListener(new Action());

        updateTable = new JButton(iconRefresh);
        updateTable.setBounds(920, 117, 50, 50);
        updateTable.addActionListener(new Action());

        productTableModel = new ProductTableModel();
        table = new JTable(productTableModel);
        scroll = new JScrollPane(table);
        scroll.setBounds(260, 100, 645, 400);
        table.setFillsViewportHeight(true );
        updateTable();

        mainPanel.add(productLabel);
        mainPanel.add(addProduct);
        mainPanel.add(delProduct);
        mainPanel.add(editProduct);

        mainPanel.add(groupLabel);
        mainPanel.add(addGroup);
        mainPanel.add(delGroup);
        mainPanel.add(editGroup);

        mainPanel.add(subGroupLabel);
        mainPanel.add(addSubGroup);
        mainPanel.add(delSubGroup);
        mainPanel.add(editSubGroup);

        mainPanel.add(statistics);

        mainPanel.add(searchField);
        mainPanel.add(searchButton);

        mainPanel.add(updateTable);

        mainPanel.add(scroll);

        clarificationPanel.add("mainPanel", mainPanel);
    }

    private void initAddProductPanel() {
        addProductPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        addProductPanel.add(back);

        groupLabelAP = new JLabel("Виберіть групу");
        groupLabelAP.setBounds(350, 150, 300, 20);
        groupLabelAP.setHorizontalAlignment(SwingConstants.CENTER);

        groupBoxModelAP = new DefaultComboBoxModel();
        groupBoxAP = new JComboBox(groupBoxModelAP);
        groupBoxAP.setBounds(350, 190, 300, 20);
        groupBoxAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subGroupLabelAP.setVisible(true);
                subGroupBoxAP.setVisible(true);
                productNameLabelAP.setVisible(true);
                productNameAP.setVisible(true);
                amountLabelAP.setVisible(true);
                amountTextFieldAP.setVisible(true);
                priceLabelAP.setVisible(true);
                priceTextFieldAP.setVisible(true);
                addAP.setVisible(true);

                subGroupBoxModelAP.removeAllElements();

                boolean isVisible = false;
                int groupId = -1;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (listGroup.get(i).getGroupName().equals(groupBoxAP.getSelectedItem())) {
                        groupId = listGroup.get(i).getGroupId();

                        for (int j = 0; j < listSubGroup.size(); j++) {

                            if (groupId == listSubGroup.get(j).getGroupId()) {
                                subGroupBoxModelAP.addElement(listSubGroup.get(j).getSubGroupName());
                                isVisible = true;
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    subGroupLabelAP.setVisible(false);
                    subGroupBoxAP.setVisible(false);
                    productNameLabelAP.setVisible(false);
                    productNameAP.setVisible(false);
                    amountLabelAP.setVisible(false);
                    amountTextFieldAP.setVisible(false);
                    priceLabelAP.setVisible(false);
                    priceTextFieldAP.setVisible(false);
                    addAP.setVisible(false);
                    incorrectNameLabelAP.setVisible(false);
                    incorrectEmptyFieldsLabelAP.setVisible(false);
                }
            }
        });

        subGroupLabelAP = new JLabel("Виберіть підгрупу");
        subGroupLabelAP.setBounds(350, 230, 300, 20);
        subGroupLabelAP.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupBoxModelAP = new DefaultComboBoxModel();
        subGroupBoxAP = new JComboBox(subGroupBoxModelAP);
        subGroupBoxAP.setBounds(350, 270, 300, 20);

        productNameLabelAP = new JLabel("Введіть ім’я товару");
        productNameLabelAP.setBounds(350, 310, 300, 20);
        productNameLabelAP.setHorizontalAlignment(SwingConstants.CENTER);

        productNameAP = new JTextField();
        productNameAP.setBounds(350 , 350, 300, 20);

        amountLabelAP = new JLabel("кількість");
        amountLabelAP.setBounds(350, 390, 140, 20);
        amountLabelAP.setHorizontalAlignment(SwingConstants.CENTER);

        amountTextFieldAP = new JTextField();
        amountTextFieldAP.setBounds(350, 430, 140, 20);
        amountTextFieldAP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextFieldIsDouble(amountTextFieldAP);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                checkTextFieldIsDouble(amountTextFieldAP);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkTextFieldIsDouble(amountTextFieldAP);
            }
        });


        priceLabelAP = new JLabel("ціна");
        priceLabelAP.setBounds(490, 390, 140, 20);
        priceLabelAP.setHorizontalAlignment(SwingConstants.CENTER);

        priceTextFieldAP = new JTextField();
        priceTextFieldAP.setBounds(490, 430, 140, 20);
        priceTextFieldAP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextFieldIsDouble(priceTextFieldAP);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                checkTextFieldIsDouble(priceTextFieldAP);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkTextFieldIsDouble(priceTextFieldAP);
            }
        });

        addAP = new JButton("додати");
        addAP.setBounds(350, 470, 300, 20);
        addAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectEmptyFieldsLabelAP.setVisible(false);
                incorrectNameLabelAP.setVisible(false);

                if (!priceTextFieldAP.getText().equals("") & !amountTextFieldAP.getText().equals("") & !productNameAP.getText().equals("")) {
                    boolean isAdd = true;
                    String productName = productNameAP.getText();
                    double amount = Double.parseDouble(amountTextFieldAP.getText());
                    double price = Double.parseDouble(priceTextFieldAP.getText());
                    int groupId = -1;
                    int subGroupId = -1;

                    for (int i = 0; i < listProduct.size(); i++) {

                        if (listProduct.get(i).getProductName().equals(productName)) {
                            isAdd = false;
                            incorrectNameLabelAP.setVisible(true);
                            return;
                        }
                    }

                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (listSubGroup.get(i).getSubGroupName().equals(subGroupBoxAP.getSelectedItem())) {
                            subGroupId = listSubGroup.get(i).getSubGroupId();
                            groupId = listSubGroup.get(i).getGroupId();
                            break;
                        }
                    }

                    System.out.println(isAdd +", " + groupId +", " + subGroupId);
                    if (isAdd & groupId != -1 & subGroupId != -1) {
                        ProductEntity entity = new ProductEntity();
                        entity.setProductName(productName);
                        entity.setGroupId(groupId);
                        entity.setSubGroupId(subGroupId);
                        entity.setAmount(amount);
                        entity.setPrice(price);
                        dataExchange.transferString("add");
                        dataExchange.transferProductEntity(entity);
                        layout.show(clarificationPanel, "mainPanel");
                        updateTable();
                        priceTextFieldAP.setText(null);
                        amountTextFieldAP.setText(null);
                        productNameAP.setText(null);
                        return;
                    }

                } else {
                    incorrectEmptyFieldsLabelAP.setVisible(true);
                }
            }
        });

        incorrectNameLabelAP = new JLabel("даний товар вже існує!");
        incorrectNameLabelAP.setBounds(350, 510, 300, 20);
        incorrectNameLabelAP.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectNameLabelAP.setVisible(false);

        incorrectEmptyFieldsLabelAP = new JLabel("всі поля повині бути заповнені");
        incorrectEmptyFieldsLabelAP.setBounds(350, 510, 300, 20);
        incorrectEmptyFieldsLabelAP.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectEmptyFieldsLabelAP.setVisible(false);


        addProductPanel.add(groupBoxAP);
        addProductPanel.add(subGroupBoxAP);
        addProductPanel.add(productNameAP);
        addProductPanel.add(amountTextFieldAP);
        addProductPanel.add(priceTextFieldAP);
        addProductPanel.add(groupLabelAP);
        addProductPanel.add(subGroupLabelAP);
        addProductPanel.add(productNameLabelAP);
        addProductPanel.add(amountLabelAP);
        addProductPanel.add(priceLabelAP);
        addProductPanel.add(incorrectNameLabelAP);
        addProductPanel.add(incorrectEmptyFieldsLabelAP);
        addProductPanel.add(addAP);

        clarificationPanel.add("addProductPanel", addProductPanel);
    }

    private void initDelProductPanel() {
        delProductPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        delProductPanel.add(back);

        groupLabelDP = new JLabel("Виберіть групу");
        groupLabelDP.setBounds(350, 200, 300, 20);
        groupLabelDP.setHorizontalAlignment(SwingConstants.CENTER);

        groupBoxModelDP = new DefaultComboBoxModel();
        groupBoxDP = new JComboBox(groupBoxModelDP);
        groupBoxDP.setBounds(350, 240, 300, 20);
        groupBoxDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subGroupBoxModelDP.removeAllElements();
                subGroupLabelDP.setVisible(true);
                subGroupBoxDP.setVisible(true);
                productLabelDP.setVisible(true);
                productBoxDP.setVisible(true);
                delDP.setVisible(true);

                boolean isVisible = false;
                int groupId = -1;

                for (int j = 0; j < listGroup.size(); j++) {

                    if (String.valueOf(groupBoxDP.getSelectedItem()).equals(listGroup.get(j).getGroupName())) {
                        groupId = listGroup.get(j).getGroupId();

                        for (int i = 0; i < listSubGroup.size(); i++) {

                            if (groupId == listSubGroup.get(i).getGroupId()) {
                                subGroupBoxModelDP.addElement(listSubGroup.get(i).getSubGroupName());
                                isVisible = true;
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    subGroupLabelDP.setVisible(false);
                    subGroupBoxDP.setVisible(false);
                    productLabelDP.setVisible(false);
                    productBoxDP.setVisible(false);
                    delDP.setVisible(false);
                }
            }
        });

        subGroupLabelDP = new JLabel("Виберіть підгрупу");
        subGroupLabelDP.setBounds(350, 280, 300, 20);
        subGroupLabelDP.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupBoxModelDP = new DefaultComboBoxModel();
        subGroupBoxDP = new JComboBox(subGroupBoxModelDP);
        subGroupBoxDP.setBounds(350, 320, 300, 20);
        subGroupBoxDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productBoxModelDP.removeAllElements();
                productLabelDP.setVisible(true);
                productBoxDP.setVisible(true);
                delDP.setVisible(true);

                boolean isVisible = false;
                int groupId = -1;
                int subGroupId = -1;
                String groupName = String.valueOf(groupBoxDP.getSelectedItem());
                String subGroupName = String.valueOf(subGroupBoxDP.getSelectedItem());

                for (int j = 0; j < listGroup.size(); j++) {

                    if (groupName.equals(listGroup.get(j).getGroupName())) {
                        groupId = listGroup.get(j).getGroupId();

                        for (int i = 0; i < listSubGroup.size(); i++) {

                            if (subGroupName.equals(listSubGroup.get(i).getSubGroupName())) {
                                subGroupId = listSubGroup.get(i).getSubGroupId();

                                for (int k = 0; k < listProduct.size(); k++) {

                                    if (listProduct.get(k).getGroupId() == groupId & listProduct.get(k).getSubGroupId() == subGroupId) {
                                        productBoxModelDP.addElement(listProduct.get(k).getProductName());
                                        isVisible = true;
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    productLabelDP.setVisible(false);
                    productBoxDP.setVisible(false);
                    delDP.setVisible(false);
                }
            }
        });

        productLabelDP = new JLabel("Виберіть товар для видалення");
        productLabelDP.setBounds(350, 360, 300, 20);
        productLabelDP.setHorizontalAlignment(SwingConstants.CENTER);

        productBoxModelDP = new DefaultComboBoxModel();
        productBoxDP = new JComboBox(productBoxModelDP);
        productBoxDP.setBounds(350, 400, 300, 20);

        delDP = new JButton("видалити");
        delDP.setBounds(350, 440, 300, 20);
        delDP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = String.valueOf(productBoxDP.getSelectedItem());

                for (int i = 0; i < listProduct.size(); i++) {

                    if (productName.equals(listProduct.get(i).getProductName())) {
                        dataExchange.transferString("del");
                        dataExchange.transferInt(listProduct.get(i).getProductId());
                        layout.show(clarificationPanel, "mainPanel");
                        updateTable();
                        return;
                    }
                }
            }
        });

        delProductPanel.add(groupLabelDP);
        delProductPanel.add(groupBoxDP);
        delProductPanel.add(subGroupLabelDP);
        delProductPanel.add(subGroupBoxDP);
        delProductPanel.add(productLabelDP);
        delProductPanel.add(productBoxDP);
        delProductPanel.add(delDP);

        clarificationPanel.add(delProductPanel, "delProductPanel");
    }

    private void initEditProductPanel() {
        editProductPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });

        editProductPanel.add(back);

        groupLabelEP = new JLabel("Виберіть групу");
        groupLabelEP.setBounds(350, 50, 300, 20);
        groupLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        groupBoxModelEP = new DefaultComboBoxModel();
        groupBoxEP = new JComboBox(groupBoxModelEP);
        groupBoxEP.setBounds(350, 80, 300, 20);
        groupBoxEP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subGroupBoxModelEP.removeAllElements();
                subGroupLabelEP.setVisible(true);
                subGroupBoxEP.setVisible(true);
                productLabelEP.setVisible(true);
                productBoxEP.setVisible(true);
                newGroupLabelEP.setVisible(true);
                newGroupBoxEP.setVisible(true);
                newSubGroupLabelEP.setVisible(true);
                newSubGroupBoxEP.setVisible(true);
                newProductNameLabelEP.setVisible(true);
                newProductNameFieldEP.setVisible(true);
                newAmountEP.setVisible(true);
                newProductAmountFieldEP.setVisible(true);
                newPriceEP.setVisible(true);
                newProductPriceFieldEP.setVisible(true);
                editProductEP.setVisible(true);

                boolean isVisible = false;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (String.valueOf(groupBoxEP.getSelectedItem()).equals(listGroup.get(i).getGroupName())) {

                        for (int j = 0; j < listSubGroup.size(); j++) {

                            if (listSubGroup.get(j).getGroupId() == listGroup.get(i).getGroupId()) {
                                subGroupBoxModelEP.addElement(listSubGroup.get(j).getSubGroupName());
                                isVisible = true;
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    subGroupLabelEP.setVisible(false);
                    subGroupBoxEP.setVisible(false);
                    productLabelEP.setVisible(false);
                    productBoxEP.setVisible(false);
                    newGroupLabelEP.setVisible(false);
                    newGroupBoxEP.setVisible(false);
                    newSubGroupLabelEP.setVisible(false);
                    newSubGroupBoxEP.setVisible(false);
                    newProductNameLabelEP.setVisible(false);
                    newProductNameFieldEP.setVisible(false);
                    newAmountEP.setVisible(false);
                    newProductAmountFieldEP.setVisible(false);
                    newPriceEP.setVisible(false);
                    newProductPriceFieldEP.setVisible(false);
                    editProductEP.setVisible(false);
                    incorrectNameLabelEP.setVisible(false);
                    incorrectEmptyFieldsLabelEP.setVisible(false);
                }
            }
        });

        subGroupLabelEP = new JLabel("Виберіть підгрупу");
        subGroupLabelEP.setBounds(350, 110, 300, 20);
        subGroupLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupBoxModelEP = new DefaultComboBoxModel();
        subGroupBoxEP = new JComboBox(subGroupBoxModelEP);
        subGroupBoxEP.setBounds(350, 140, 300, 20);
        subGroupBoxEP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subGroupLabelEP.setVisible(true);
                subGroupBoxEP.setVisible(true);
                productLabelEP.setVisible(true);
                productBoxEP.setVisible(true);
                newGroupLabelEP.setVisible(true);
                newGroupBoxEP.setVisible(true);
                newSubGroupLabelEP.setVisible(true);
                newSubGroupBoxEP.setVisible(true);
                newProductNameLabelEP.setVisible(true);
                newProductNameFieldEP.setVisible(true);
                newAmountEP.setVisible(true);
                newProductAmountFieldEP.setVisible(true);
                newPriceEP.setVisible(true);
                newProductPriceFieldEP.setVisible(true);
                editProductEP.setVisible(true);

                boolean isVisible = false;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (String.valueOf(groupBoxEP.getSelectedItem()).equals(listGroup.get(i).getGroupName())) {

                        for (int j = 0; j < listSubGroup.size(); j++) {

                            if (String.valueOf(subGroupBoxEP.getSelectedItem()).equals(listSubGroup.get(j).getSubGroupName())) {

                                for (int k = 0; k < listProduct.size(); k++) {

                                    if (listProduct.get(k).getSubGroupId() == listSubGroup.get(j).getSubGroupId() & listProduct.get(k).getGroupId() == listGroup.get(i).getGroupId()) {
                                        productBoxModelEP.addElement(listProduct.get(k).getProductName());
                                        isVisible = true;
                                    }
                                }
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    productLabelEP.setVisible(false);
                    productBoxEP.setVisible(false);
                    newGroupLabelEP.setVisible(false);
                    newGroupBoxEP.setVisible(false);
                    newSubGroupLabelEP.setVisible(false);
                    newSubGroupBoxEP.setVisible(false);
                    newProductNameLabelEP.setVisible(false);
                    newProductNameFieldEP.setVisible(false);
                    newAmountEP.setVisible(false);
                    newProductAmountFieldEP.setVisible(false);
                    newPriceEP.setVisible(false);
                    newProductPriceFieldEP.setVisible(false);
                    editProductEP.setVisible(false);
                    incorrectNameLabelEP.setVisible(false);
                    incorrectEmptyFieldsLabelEP.setVisible(false);
                }
            }
        });

        productLabelEP = new JLabel("Виберіть товар для змін");
        productLabelEP.setBounds(350, 170, 300, 20);
        productLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        productBoxModelEP = new DefaultComboBoxModel();
        productBoxEP = new JComboBox(productBoxModelEP);
        productBoxEP.setBounds(350, 200, 300, 20);

        newGroupLabelEP = new JLabel("Виберіть нову групу для товару");
        newGroupLabelEP.setBounds(350, 230, 300, 20);
        newGroupLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        newGroupBoxModelEP = new DefaultComboBoxModel();
        newGroupBoxEP = new JComboBox(newGroupBoxModelEP);
        newGroupBoxEP.setBounds(350, 260, 300, 20);
        newGroupBoxEP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newSubGroupBoxModelEP.removeAllElements();
                subGroupLabelEP.setVisible(true);
                subGroupBoxEP.setVisible(true);
                productLabelEP.setVisible(true);
                productBoxEP.setVisible(true);
                newGroupLabelEP.setVisible(true);
                newGroupBoxEP.setVisible(true);
                newSubGroupLabelEP.setVisible(true);
                newSubGroupBoxEP.setVisible(true);
                newProductNameLabelEP.setVisible(true);
                newProductNameFieldEP.setVisible(true);
                newAmountEP.setVisible(true);
                newProductAmountFieldEP.setVisible(true);
                newPriceEP.setVisible(true);
                newProductPriceFieldEP.setVisible(true);
                editProductEP.setVisible(true);

                boolean isVisible = false;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (String.valueOf(newGroupBoxEP.getSelectedItem()).equals(listGroup.get(i).getGroupName())) {

                        for (int j = 0; j < listSubGroup.size(); j++) {

                            if (listSubGroup.get(j).getGroupId() == listGroup.get(i).getGroupId()) {
                                newSubGroupBoxModelEP.addElement(listSubGroup.get(j).getSubGroupName());
                                isVisible = true;
                            }
                        }
                        break;
                    }
                }

                if (!isVisible) {
                    newSubGroupLabelEP.setVisible(false);
                    newSubGroupBoxEP.setVisible(false);
                    newProductNameLabelEP.setVisible(false);
                    newProductNameFieldEP.setVisible(false);
                    newAmountEP.setVisible(false);
                    newProductAmountFieldEP.setVisible(false);
                    newPriceEP.setVisible(false);
                    newProductPriceFieldEP.setVisible(false);
                    editProductEP.setVisible(false);
                    incorrectNameLabelEP.setVisible(false);
                    incorrectEmptyFieldsLabelEP.setVisible(false);
                }
            }
        });

        newSubGroupLabelEP = new JLabel("Виберіть нову підгрупу для товару");
        newSubGroupLabelEP.setBounds(350, 290, 300, 20);
        newSubGroupLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        newSubGroupBoxModelEP = new DefaultComboBoxModel();
        newSubGroupBoxEP = new JComboBox(newSubGroupBoxModelEP);
        newSubGroupBoxEP.setBounds(350, 320, 300, 20);
        newSubGroupBoxEP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(productBoxEP.getSelectedItem()) instanceof String) {
                    productLabelEP.setVisible(false);
                    productBoxEP.setVisible(false);
                    newGroupLabelEP.setVisible(false);
                    newGroupBoxEP.setVisible(false);
                    newSubGroupLabelEP.setVisible(false);
                    newSubGroupBoxEP.setVisible(false);
                    newProductNameLabelEP.setVisible(false);
                    newProductNameFieldEP.setVisible(false);
                    newAmountEP.setVisible(false);
                    newProductAmountFieldEP.setVisible(false);
                    newPriceEP.setVisible(false);
                    newProductPriceFieldEP.setVisible(false);
                    editProductEP.setVisible(false);
                } else {
                    productLabelEP.setVisible(true);
                    productBoxEP.setVisible(true);
                    newGroupLabelEP.setVisible(true);
                    newGroupBoxEP.setVisible(true);
                    newSubGroupLabelEP.setVisible(true);
                    newSubGroupBoxEP.setVisible(true);
                    newProductNameLabelEP.setVisible(true);
                    newProductNameFieldEP.setVisible(true);
                    newAmountEP.setVisible(true);
                    newProductAmountFieldEP.setVisible(true);
                    newPriceEP.setVisible(true);
                    newProductPriceFieldEP.setVisible(true);
                    editProductEP.setVisible(true);
                }
            }
        });

        newProductNameLabelEP = new JLabel("Введіть нову назву товару");
        newProductNameLabelEP.setBounds(350, 350, 300, 20);
        newProductNameLabelEP.setHorizontalAlignment(SwingConstants.CENTER);

        newProductNameFieldEP = new JTextField();
        newProductNameFieldEP.setBounds(350, 380, 300, 20);


        newAmountEP = new JLabel("нову кількість");
        newAmountEP.setBounds(350, 410, 140, 20);
        newAmountEP.setHorizontalAlignment(SwingConstants.CENTER);

        newProductAmountFieldEP = new JTextField();
        newProductAmountFieldEP.setBounds(350, 440, 140, 20);
        newProductAmountFieldEP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextFieldIsDouble(newProductAmountFieldEP);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                checkTextFieldIsDouble(newProductAmountFieldEP);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkTextFieldIsDouble(newProductAmountFieldEP);
            }
        });

        newPriceEP = new JLabel("нову ціну");
        newPriceEP.setBounds(510, 410, 140, 20);
        newPriceEP.setHorizontalAlignment(SwingConstants.CENTER);

        newProductPriceFieldEP = new JTextField();
        newProductPriceFieldEP.setBounds(510, 440, 140, 20);
        newProductPriceFieldEP.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                checkTextFieldIsDouble(newProductPriceFieldEP);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                checkTextFieldIsDouble(newProductPriceFieldEP);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                checkTextFieldIsDouble(newProductPriceFieldEP);
            }
        });

        editProductEP = new JButton("редагувати");
        editProductEP.setBounds(350, 470, 300, 20);
        editProductEP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //!!!!!!
                boolean isEdit = false;

                if (!newProductNameFieldEP.getText().equals("") &  !newProductAmountFieldEP.getText().equals("") & !newProductPriceFieldEP.getText().equals("")) {
                    incorrectEmptyFieldsLabelEP.setVisible(false);

                    for (int i = 0; i < listProduct.size(); i++) {

                        if (newProductNameFieldEP.getText().equals(listProduct.get(i).getProductName())) {
                            isEdit = false;
                            break;
                        } else {
                            isEdit = true;
                        }
                    }

                    if (isEdit) {
                        incorrectNameLabelEP.setVisible(false);

                        for (int i = 0; i < listSubGroup.size(); i++) {

                            if (String.valueOf(newSubGroupBoxEP.getSelectedItem()).equals(listSubGroup.get(i).getSubGroupName())) {

                                for (int k = 0; k < listProduct.size(); k++) {

                                    if (String.valueOf(productBoxEP.getSelectedItem()).equals(listProduct.get(k).getProductName())) {
                                        ProductEntity entity = new ProductEntity();
                                        entity.setProductName(newProductNameFieldEP.getText());
                                        entity.setSubGroupId(listSubGroup.get(i).getSubGroupId());
                                        entity.setGroupId(listSubGroup.get(i).getGroupId());
                                        entity.setAmount(Double.parseDouble(newProductAmountFieldEP.getText()));
                                        entity.setPrice(Double.parseDouble(newProductPriceFieldEP.getText()));

                                        dataExchange.transferString("edit");
                                        dataExchange.transferProductEntity(entity);
                                        dataExchange.transferInt(listProduct.get(k).getProductId());
                                        newProductNameFieldEP.setText(null);
                                        newProductAmountFieldEP.setText(null);
                                        newProductPriceFieldEP.setText(null);
                                        layout.show(clarificationPanel, "mainPanel");
                                        updateTable();
                                        return;
                                    }
                                }
                            }
                        }
                    } else {
                        incorrectNameLabelEP.setVisible(true);
                    }
                } else {
                    incorrectEmptyFieldsLabelEP.setVisible(true);
                }
            }
        });

        incorrectNameLabelEP = new JLabel("Такий товар вже існує!");
        incorrectNameLabelEP.setBounds(350, 500, 300, 20);
        incorrectNameLabelEP.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectNameLabelEP.setVisible(false);

        incorrectEmptyFieldsLabelEP = new JLabel("Всі поля повині бути заповненими!");
        incorrectEmptyFieldsLabelEP.setBounds(350, 500, 300, 20);
        incorrectEmptyFieldsLabelEP.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectEmptyFieldsLabelEP.setVisible(false);

        editProductPanel.add(groupLabelEP);
        editProductPanel.add(subGroupLabelEP);
        editProductPanel.add(productLabelEP);
        editProductPanel.add(newGroupLabelEP);
        editProductPanel.add(newSubGroupLabelEP);
        editProductPanel.add(newProductNameLabelEP);
        editProductPanel.add(newAmountEP);
        editProductPanel.add(newPriceEP);
        editProductPanel.add(incorrectNameLabelEP);
        editProductPanel.add(incorrectEmptyFieldsLabelEP);
        editProductPanel.add(groupBoxEP);
        editProductPanel.add(subGroupBoxEP);
        editProductPanel.add(productBoxEP);
        editProductPanel.add(newGroupBoxEP);
        editProductPanel.add(newSubGroupBoxEP);
        editProductPanel.add(newProductNameFieldEP);
        editProductPanel.add(newProductAmountFieldEP);
        editProductPanel.add(newProductPriceFieldEP);
        editProductPanel.add(editProductEP);

        clarificationPanel.add(editProductPanel, "editProductPanel");
    }

    private void initAddGroupPanel() {
        addGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        addGroupPanel.add(back);

        groupNameAG = new JLabel("Введіть ім’я групи");
        groupNameAG.setBounds(350, 225, 300, 20);
        groupNameAG.setHorizontalAlignment(SwingConstants.CENTER);

        groupNameFieldAG = new JTextField();
        groupNameFieldAG.setBounds(350, 265, 300, 20);
        groupNameFieldAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!groupNameFieldAG.getText().equals(null)) {
                    incorrectGroupNameAG.setVisible(false);
                    boolean add = true;

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(groupNameFieldAG.getText())) {
                            add = false;
                            incorrectGroupNameAG.setVisible(true);
                        }
                    }

                    if (add) {
                        dataExchange.transferString("add");
                        dataExchange.transferString(groupNameFieldAG.getText());
                        layout.show(clarificationPanel, "mainPanel");
                        updateTable();
                    }
                    groupNameFieldAG.setText(null);
                }
            }
        });

        addGroupNameAG = new JButton("Добавити групу");
        addGroupNameAG.setBounds(350, 305, 300, 20);
        addGroupNameAG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!groupNameFieldAG.getText().equals(null)) {
                    incorrectGroupNameAG.setVisible(false);
                    boolean add = true;

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(groupNameFieldAG.getText())) {
                            add = false;
                            incorrectGroupNameAG.setVisible(true);
                        }
                    }

                    if (add) {
                        dataExchange.transferString("add");
                        dataExchange.transferString(groupNameFieldAG.getText());
                        layout.show(clarificationPanel, "mainPanel");
                        updateTable();
                    }
                    groupNameFieldAG.setText(null);
                }
            }
        });

        incorrectGroupNameAG = new JLabel("Така група вже існує");
        incorrectGroupNameAG.setBounds(350, 345, 300, 20);
        incorrectGroupNameAG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectGroupNameAG.setVisible(false);

        addGroupPanel.add(groupNameAG);
        addGroupPanel.add(groupNameFieldAG);
        addGroupPanel.add(addGroupNameAG);
        addGroupPanel.add(incorrectGroupNameAG);

        clarificationPanel.add("addGroupPanel", addGroupPanel);
    }

    private void initDelGroupPanel() {
        delGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        delGroupPanel.add(back);

        groupNameDG = new JLabel("Виберіть групу");
        groupNameDG.setBounds(350, 225, 300, 20);
        groupNameDG.setHorizontalAlignment(SwingConstants.CENTER);

        incorrectGroupNameDG = new JLabel("Ця група використовується, її не можливо видалити!");
        incorrectGroupNameDG.setBounds(300, 305, 400, 20);
        incorrectGroupNameDG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectGroupNameDG.setVisible(false);

        delGroupDG = new JButton("видалити");
        delGroupDG.setBounds(350, 305, 300, 20);
        delGroupDG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textBox = (String) groupDG.getSelectedItem();

                for (int i = 0; i < listGroup.size(); i++) {

                    if (textBox.equals(listGroup.get(i).getGroupName())) {
                        dataExchange.transferString("del");
                        dataExchange.transferInt(listGroup.get(i).getGroupId());
                        layout.show(clarificationPanel, "mainPanel");
                        updateTable();
                        return;
                    }
                }
                dataExchange.transferString("back");
            }
        });
        delGroupDG.setVisible(false);

        groupComboBoxModelDG = new DefaultComboBoxModel();

        groupDG = new JComboBox(groupComboBoxModelDG);
        groupDG.setBounds(350, 265, 300, 20);
        groupDG.setModel(groupComboBoxModelDG);
        groupDG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textBox = (String) groupDG.getSelectedItem();
                boolean del = true;

                for (int i = 0; i < listProductTable.size(); i++) {

                    if (textBox == null) {
                        break;
                    } else if (textBox.equals(listProductTable.get(i).getGroupName())) {
                        del = false;
                    }
                }

                if (del) {
                    incorrectGroupNameDG.setVisible(false);
                    delGroupDG.setVisible(true);
                } else {
                    incorrectGroupNameDG.setVisible(true);
                    delGroupDG.setVisible(false);
                }
            }
        });

        delGroupPanel.add(groupNameDG);
        delGroupPanel.add(delGroupDG);
        delGroupPanel.add(incorrectGroupNameDG);
        delGroupPanel.add(groupDG);

        clarificationPanel.add(delGroupPanel, "delGroup");
    }

    private void initEditGroupPanel() {
        editGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        editGroupPanel.add(back);

        enterGroupEG = new JLabel("Виберіть групу для редагування");
        enterGroupEG.setBounds(300, 225, 400, 20);
        enterGroupEG.setHorizontalAlignment(SwingConstants.CENTER);

        groupComboBoxModelEG = new DefaultComboBoxModel();
        groupEG = new JComboBox(groupComboBoxModelEG);
        groupEG.setBounds(350, 265, 300, 20);

        enterNewGroupNameEG = new JLabel("Введіть нову назву");
        enterNewGroupNameEG.setBounds(350, 305, 300, 20);
        enterNewGroupNameEG.setHorizontalAlignment(SwingConstants.CENTER);

        newGroupNameTextFieldEG = new JTextField();
        newGroupNameTextFieldEG.setBounds(350, 345, 300, 20);
        newGroupNameTextFieldEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectGroupNameEG.setVisible(false);

                if (!newGroupNameTextFieldEG.getText().equals(null)) {
                    String editGroupName = (String) groupEG.getSelectedItem();
                    boolean isEdit = false;

                    for (int j = 0; j < listGroup.size(); j++) {

                        if (newGroupNameTextFieldEG.getText().equals(listGroup.get(j).getGroupName())) {
                            isEdit = false;
                            break;
                        } else {
                            isEdit = true;
                        }
                    }

                    if (!isEdit) {
                        incorrectGroupNameEG.setVisible(true);
                        newGroupNameTextFieldEG.setText(null);
                    } else {

                        for (int i = 0; i < listGroup.size(); i++) {

                            if (editGroupName.equals(listGroup.get(i).getGroupName())) {
                                dataExchange.transferString("edit");
                                dataExchange.transferString(newGroupNameTextFieldEG.getText());
                                dataExchange.transferInt(listGroup.get(i).getGroupId());
                                updateTable();
                                newGroupNameTextFieldEG.setText(null);
                                layout.show(clarificationPanel, "mainPanel");
                                return;
                            }
                        }
                    }
                }
            }
        });

        enterNewGroupNameButtonEG = new JButton("редагувати");
        enterNewGroupNameButtonEG.setBounds(350, 385, 300, 20);
        enterNewGroupNameButtonEG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectGroupNameEG.setVisible(false);

                if (!newGroupNameTextFieldEG.getText().equals(null)) {
                    String editGroupName = (String) groupEG.getSelectedItem();
                    boolean isEdit = false;

                    for (int j = 0; j < listGroup.size(); j++) {

                        if (newGroupNameTextFieldEG.getText().equals(listGroup.get(j).getGroupName())) {
                            isEdit = false;
                            break;
                        } else {
                            isEdit = true;
                        }
                    }

                    if (!isEdit) {
                        incorrectGroupNameEG.setVisible(true);
                        newGroupNameTextFieldEG.setText(null);
                    } else {

                        for (int i = 0; i < listGroup.size(); i++) {

                            if (editGroupName.equals(listGroup.get(i).getGroupName())) {
                                dataExchange.transferString("edit");
                                dataExchange.transferString(newGroupNameTextFieldEG.getText());
                                dataExchange.transferInt(listGroup.get(i).getGroupId());
                                updateTable();
                                newGroupNameTextFieldEG.setText(null);
                                layout.show(clarificationPanel, "mainPanel");
                                return;
                            }
                        }
                    }
                }
            }
        });

        incorrectGroupNameEG = new JLabel("Така група вже існує!");
        incorrectGroupNameEG.setBounds(350, 425, 300, 20);
        incorrectGroupNameEG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectGroupNameEG.setVisible(false);

        editGroupPanel.add(enterGroupEG);
        editGroupPanel.add(groupEG);
        editGroupPanel.add(enterNewGroupNameEG);
        editGroupPanel.add(newGroupNameTextFieldEG);
        editGroupPanel.add(enterNewGroupNameButtonEG);
        editGroupPanel.add(incorrectGroupNameEG);

        clarificationPanel.add(editGroupPanel, "editGroupPanel");
    }

    private void initAddSubGroupPanel() {
        addSubGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
                incorrectSubGroupNameASG.setVisible(false);
            }
        });
        addSubGroupPanel.add(back);

        enterGroupASG = new JLabel("Виберіть групу, до якої хочете добавити підгрупу");
        enterGroupASG.setBounds(250, 225, 500, 20);
        enterGroupASG.setHorizontalAlignment(SwingConstants.CENTER);

        groupComboBoxModelASG = new DefaultComboBoxModel();
        groupASG = new JComboBox(groupComboBoxModelASG);
        groupASG.setBounds(350, 265, 300, 20);

        enterSubGroupNameASG = new JLabel("Введіть ім’я підгрупи");
        enterSubGroupNameASG.setBounds(350, 305, 300, 20);
        enterSubGroupNameASG.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupNameFieldASG = new JTextField();
        subGroupNameFieldASG.setBounds(350, 345, 300, 20);
        subGroupNameFieldASG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectSubGroupNameASG.setVisible(false);
                String groupName = (String) groupASG.getSelectedItem();
                int groupId = -1;
                String subGroupName = subGroupNameFieldASG.getText();
                boolean isAdd = false;

                if (!groupName.equals("") && !subGroupName.equals("")) {

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(groupName)) {
                            isAdd = true;
                            groupId = listGroup.get(i).getGroupId();
                            break;
                        } else {
                            isAdd = false;
                        }
                    }
                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (listSubGroup.get(i).getSubGroupName().equals(subGroupName)) {
                            isAdd = false;
                            break;
                        } else if (!listSubGroup.get(i).getSubGroupName().equals(subGroupName)) {
                            isAdd = true;
                        }
                    }
                    if (isAdd) {
                        dataExchange.transferString("add");
                        SubGroupEntity entity = new SubGroupEntity();
                        entity.setSubGroupName(subGroupName);
                        entity.setGroupId(groupId);
                        dataExchange.transferSubGroupEntity(entity);
                        subGroupNameFieldASG.setText(null);
                        updateTable();
                        layout.show(clarificationPanel, "mainPanel");
                        return;
                    } else {
                        subGroupNameFieldASG.setText(null);
                        incorrectSubGroupNameASG.setVisible(true);
                    }
                }
            }
        });

        addSubGroupASG = new JButton("добавити");
        addSubGroupASG.setBounds(350, 385, 300, 20);
        addSubGroupASG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectSubGroupNameASG.setVisible(false);
                String groupName = (String) groupASG.getSelectedItem();
                int groupId = -1;
                String subGroupName = subGroupNameFieldASG.getText();
                boolean isAdd = false;

                if (!groupName.equals("") && !subGroupName.equals("")) {

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(groupName)) {
                            isAdd = true;
                            groupId = listGroup.get(i).getGroupId();
                            break;
                        } else {
                            isAdd = false;
                        }
                    }
                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (listSubGroup.get(i).getSubGroupName().equals(subGroupName)) {
                            isAdd = false;
                            break;
                        } else if (!listSubGroup.get(i).getSubGroupName().equals(subGroupName)) {
                            isAdd = true;
                        }
                    }
                    if (isAdd) {
                        dataExchange.transferString("add");
                        SubGroupEntity entity = new SubGroupEntity();
                        entity.setSubGroupName(subGroupName);
                        entity.setGroupId(groupId);
                        dataExchange.transferSubGroupEntity(entity);
                        subGroupNameFieldASG.setText(null);
                        updateTable();
                        layout.show(clarificationPanel, "mainPanel");
                        return;
                    } else {
                        subGroupNameFieldASG.setText(null);
                        incorrectSubGroupNameASG.setVisible(true);
                    }
                }
            }
        });

        incorrectSubGroupNameASG = new JLabel("Ця підгрупа вже існує");
        incorrectSubGroupNameASG.setBounds(350, 425, 300, 20);
        incorrectSubGroupNameASG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectSubGroupNameASG.setVisible(false);

        addSubGroupPanel.add(enterGroupASG);
        addSubGroupPanel.add(enterSubGroupNameASG);
        addSubGroupPanel.add(incorrectSubGroupNameASG);
        addSubGroupPanel.add(groupASG);
        addSubGroupPanel.add(subGroupNameFieldASG);
        addSubGroupPanel.add(addSubGroupASG);

        clarificationPanel.add(addSubGroupPanel, "addSubGroupPanel");
    }

    private void initDelSubGroupPanel() {
        delSubGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        delSubGroupPanel.add(back);

        enterGroupDSG = new JLabel("Виберіть групу");
        enterGroupDSG.setBounds(350, 225, 300, 20);
        enterGroupDSG.setHorizontalAlignment(SwingConstants.CENTER);

        groupComboBoxModelDSG = new DefaultComboBoxModel();
        groupComboBoxDSG = new JComboBox(groupComboBoxModelDSG);
        groupComboBoxDSG.setBounds(350, 265, 300, 20);
        groupComboBoxDSG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subGroupComboBoxModelDSG.removeAllElements();
                int groupId = -1;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (listGroup.get(i).getGroupName().equals(groupComboBoxDSG.getSelectedItem())) {
                        groupId = listGroup.get(i).getGroupId();
                        break;
                    }
                }
                if (groupId != -1) {

                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (groupId == listSubGroup.get(i).getGroupId()) {
                            subGroupComboBoxModelDSG.addElement(listSubGroup.get(i).getSubGroupName());
                        }
                    }
                }
            }
        });

        enterSubGroupDSG = new JLabel("Виберіть підгрупу");
        enterSubGroupDSG.setBounds(350, 305, 300, 20);
        enterSubGroupDSG.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupComboBoxModelDSG = new DefaultComboBoxModel();
        subGroupComboBoxDSG = new JComboBox(subGroupComboBoxModelDSG);
        subGroupComboBoxDSG.setBounds(350, 345, 300, 20);
        subGroupComboBoxDSG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isDel = false;
                String comboBoxSubGroupName = (String) subGroupComboBoxDSG.getSelectedItem();
                String comboBoxGroupName = (String) groupComboBoxDSG.getSelectedItem();

                if (comboBoxSubGroupName instanceof String) {

                    for (int i = 0; i < listProductTable.size(); i++) {
                        String listSubGroupName = listProductTable.get(i).getSubGroupName();
                        String listGroupName = listProductTable.get(i).getGroupName();

                        if (listSubGroupName instanceof String && listGroupName instanceof String) {

                            if (comboBoxSubGroupName.equals(listSubGroupName) && comboBoxGroupName.equals(listGroupName)) {
                                isDel = false;
                                break;
                            } else {
                                isDel = true;
                            }
                        } else {
                            isDel = false;
                            break;
                        }
                    }
                } else {
                    incorrectDelSubGroupDSG.setVisible(false);
                    delSubGroupButtonDSG.setVisible(false);
                }
                if (isDel) {
                    incorrectDelSubGroupDSG.setVisible(false);
                    delSubGroupButtonDSG.setVisible(true);
                } else {
                    incorrectDelSubGroupDSG.setVisible(true);
                    delSubGroupButtonDSG.setVisible(false);
                }
            }
        });

        incorrectDelSubGroupDSG = new JLabel("Ця під група використовується, її не можливо видалити!");
        incorrectDelSubGroupDSG.setBounds(300, 385, 400, 20);
        incorrectDelSubGroupDSG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectDelSubGroupDSG.setVisible(false);

        delSubGroupButtonDSG = new JButton("видалити");
        delSubGroupButtonDSG.setBounds(350, 385, 300, 20);
        delSubGroupButtonDSG.setVisible(false);
        delSubGroupButtonDSG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subGroup = String.valueOf(subGroupComboBoxDSG.getSelectedItem());

                for (int i = 0; i < listSubGroup.size(); i++) {

                    if (listSubGroup.get(i).getSubGroupName().equals(subGroup)) {
                        dataExchange.transferString("del");
                        dataExchange.transferInt(listSubGroup.get(i).getSubGroupId());
                        updateTable();
                        layout.show(clarificationPanel, "mainPanel");
                        return;
                    }
                }
            }
        });

        delSubGroupPanel.add(enterGroupDSG);
        delSubGroupPanel.add(enterSubGroupDSG);
        delSubGroupPanel.add(incorrectDelSubGroupDSG);
        delSubGroupPanel.add(groupComboBoxDSG);
        delSubGroupPanel.add(subGroupComboBoxDSG);
        delSubGroupPanel.add(delSubGroupButtonDSG);

        clarificationPanel.add(delSubGroupPanel, "delSubGroupPanel");
    }

    private void initEditSubGroupPanel() {
        editSubGroupPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataExchange.transferString("back");
                layout.show(clarificationPanel, "mainPanel");
            }
        });
        editSubGroupPanel.add(back);

        enterGroupESG = new JLabel("Виберіть групу");
        enterGroupESG.setBounds(350, 125, 300, 20);
        enterGroupESG.setHorizontalAlignment(SwingConstants.CENTER);

        groupComboBoxModelESG = new DefaultComboBoxModel();
        groupComboBoxESG = new JComboBox(groupComboBoxModelESG);
        groupComboBoxESG.setBounds(350, 165, 300, 20);
        groupComboBoxESG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterSubGroupESG.setVisible(true);
                subGroupComboBoxESG.setVisible(true);
                enterNewGroupESG.setVisible(true);
                newGroupComboBoxESG.setVisible(true);
                enterNewNameSubGroupESG.setVisible(true);
                newNameSubGroupESG.setVisible(true);
                editSubGroupESG.setVisible(true);

                subGroupComboBoxModelESG.removeAllElements();
                int groupId = -1;

                for (int i = 0; i < listGroup.size(); i++) {

                    if (listGroup.get(i).getGroupName().equals(groupComboBoxESG.getSelectedItem())) {
                        groupId = listGroup.get(i).getGroupId();
                        break;
                    }
                }
                boolean isVisible = false;

                if (groupId != -1) {

                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (groupId == listSubGroup.get(i).getGroupId()) {
                            subGroupComboBoxModelESG.addElement(listSubGroup.get(i).getSubGroupName());
                            isVisible = true;
                        }
                    }
                }
                if (!isVisible) {
                    enterSubGroupESG.setVisible(false);
                    subGroupComboBoxESG.setVisible(false);
                    enterNewGroupESG.setVisible(false);
                    newGroupComboBoxESG.setVisible(false);
                    enterNewNameSubGroupESG.setVisible(false);
                    newNameSubGroupESG.setVisible(false);
                    editSubGroupESG.setVisible(false);
                }
            }
        });

        enterSubGroupESG = new JLabel("Виберіть підгрупу для змін");
        enterSubGroupESG.setBounds(350, 205, 300, 20);
        enterSubGroupESG.setHorizontalAlignment(SwingConstants.CENTER);

        subGroupComboBoxModelESG = new DefaultComboBoxModel();
        subGroupComboBoxESG = new JComboBox(subGroupComboBoxModelESG);
        subGroupComboBoxESG.setBounds(350, 245, 300, 20);

        enterNewGroupESG = new JLabel("Виберіть групу, до якої хочете перемістити цю підгрупу");
        enterNewGroupESG.setBounds(250, 285, 500, 20);
        enterNewGroupESG.setHorizontalAlignment(SwingConstants.CENTER);

        newGroupComboBoxModelESG = new DefaultComboBoxModel();
        newGroupComboBoxESG = new JComboBox(newGroupComboBoxModelESG);
        newGroupComboBoxESG.setBounds(350, 325, 300, 20);


        enterNewNameSubGroupESG = new JLabel("Введіть нову назву підгрупи");
        enterNewNameSubGroupESG.setBounds(350, 365, 300, 20);
        enterNewNameSubGroupESG.setHorizontalAlignment(SwingConstants.CENTER);

        newNameSubGroupESG = new JTextField();
        newNameSubGroupESG.setBounds(350, 405, 300, 20);
        newNameSubGroupESG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectNewNameSubGroupESG.setVisible(false);
                boolean isIncorrectName = true;
                int subGroupId = -1;

                for (int i = 0; i < listSubGroup.size(); i++) {

                    if (listSubGroup.get(i).getSubGroupName().equals(newNameSubGroupESG.getText())) {
                        isIncorrectName = false;
                        incorrectNewNameSubGroupESG.setVisible(true);
                        break;
                    }
                }

                if (isIncorrectName & !newNameSubGroupESG.getText().equals("")) {
                    SubGroupEntity entity = new SubGroupEntity();
                    entity.setSubGroupName(newNameSubGroupESG.getText());

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(newGroupComboBoxESG.getSelectedItem())) {
                            entity.setGroupId(listGroup.get(i).getGroupId());
                            break;
                        }
                    }

                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (listSubGroup.get(i).getSubGroupName().equals(subGroupComboBoxESG.getSelectedItem())) {
                            subGroupId = listSubGroup.get(i).getSubGroupId();
                            break;
                        }
                    }

                    if ((subGroupId != -1) & (entity.getSubGroupName() instanceof String) & ((Integer) entity.getGroupId() instanceof  Integer)) {
                        dataExchange.transferString("edit");
                        dataExchange.transferInt(subGroupId);
                        dataExchange.transferSubGroupEntity(entity);
                        updateTable();
                        layout.show(clarificationPanel, "mainPanel");
                    }
                }
                newNameSubGroupESG.setText(null);
            }
        });

        editSubGroupESG = new JButton("редагувати");
        editSubGroupESG.setBounds(350, 445, 300, 20);
        editSubGroupESG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incorrectNewNameSubGroupESG.setVisible(false);
                boolean isIncorrectName = true;
                int subGroupId = -1;

                for (int i = 0; i < listSubGroup.size(); i++) {

                    if (listSubGroup.get(i).getSubGroupName().equals(newNameSubGroupESG.getText())) {
                        isIncorrectName = false;
                        incorrectNewNameSubGroupESG.setVisible(true);
                        break;
                    }
                }

                if (isIncorrectName & !newNameSubGroupESG.getText().equals("")) {
                    SubGroupEntity entity = new SubGroupEntity();
                    entity.setSubGroupName(newNameSubGroupESG.getText());

                    for (int i = 0; i < listGroup.size(); i++) {

                        if (listGroup.get(i).getGroupName().equals(newGroupComboBoxESG.getSelectedItem())) {
                            entity.setGroupId(listGroup.get(i).getGroupId());
                            break;
                        }
                    }

                    for (int i = 0; i < listSubGroup.size(); i++) {

                        if (listSubGroup.get(i).getSubGroupName().equals(subGroupComboBoxESG.getSelectedItem())) {
                            subGroupId = listSubGroup.get(i).getSubGroupId();
                            break;
                        }
                    }

                    if ((subGroupId != -1) & (entity.getSubGroupName() instanceof String) & ((Integer) entity.getGroupId() instanceof  Integer)) {
                        dataExchange.transferString("edit");
                        dataExchange.transferInt(subGroupId);
                        dataExchange.transferSubGroupEntity(entity);
                        ArrayList<ProductEntity> productEntities = new ArrayList<ProductEntity>();
                        boolean isEditProduct = false;

                        for (int i = 0; i < listProduct.size(); i++) {

                            if (listProduct.get(i).getSubGroupId() == subGroupId) {
                                ProductEntity productEntity = new ProductEntity();
                                productEntity.setProductName(listProduct.get(i).getProductName());
                                productEntity.setGroupId(entity.getGroupId());
                                productEntity.setSubGroupId(subGroupId);
                                productEntity.setAmount(listProduct.get(i).getAmount());
                                productEntity.setPrice(listProduct.get(i).getPrice());
                                productEntity.setProductId(listProduct.get(i).getProductId());
                                productEntities.add(productEntity);
                                isEditProduct = true;
                            }
                        }
                        dataExchange.transferBoolean(isEditProduct);

                        if (isEditProduct) {
                            dataExchange.transferInt(productEntities.size());

                            for (int i = 0; i < productEntities.size(); i++) {
                                dataExchange.transferProductEntity(productEntities.get(i));
                            }
                        }
                        updateTable();
                        layout.show(clarificationPanel, "mainPanel");
                    }
                }
                newNameSubGroupESG.setText(null);
            }
        });

        incorrectNewNameSubGroupESG = new JLabel("Така підгрупа вже використовується!");
        incorrectNewNameSubGroupESG.setBounds(350, 485, 300, 20);
        incorrectNewNameSubGroupESG.setHorizontalAlignment(SwingConstants.CENTER);
        incorrectNewNameSubGroupESG.setVisible(false);

        editSubGroupPanel.add(enterGroupESG);
        editSubGroupPanel.add(groupComboBoxESG);
        editSubGroupPanel.add(enterSubGroupESG);
        editSubGroupPanel.add(subGroupComboBoxESG);
        editSubGroupPanel.add(enterNewGroupESG);
        editSubGroupPanel.add(newGroupComboBoxESG);
        editSubGroupPanel.add(enterNewNameSubGroupESG);
        editSubGroupPanel.add(newNameSubGroupESG);
        editSubGroupPanel.add(editSubGroupESG);
        editSubGroupPanel.add(incorrectNewNameSubGroupESG);

        clarificationPanel.add(editSubGroupPanel, "editSubGroupPanel");
    }

    private void initStatisticsPanel() {
        statisticsPanel = new JPanel(null);

        JButton back = new JButton("<-назад");
        back.setBounds(50, 50, 150, 20);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                layout.show(clarificationPanel, "mainPanel");
            }
        });
        statisticsPanel.add(back);

        statisticsSearchField = new JTextField();
        statisticsSearchField.setBounds(225, 50, 630, 20);
        statisticsSearchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                statisticsSearchList = null;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        statisticsSearchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!statisticsSearchField.getText().equals("")) {

                    if (statisticsSearchList == null) {
                        statisticsSearchList = statisticsList;
                    }
                    String searchText = statisticsSearchField.getText().toLowerCase();
                    ArrayList<StatisticsEntity> list = new ArrayList<StatisticsEntity>();

                    for (int i = 0; i < statisticsSearchList.size(); i++) {
                        boolean isSearchName = true;
                        String name = statisticsSearchList.get(i).getName().toLowerCase();

                        for (int j = 0; j < searchText.length(); j++) {

                            if (isSearchName) {
                                for (int k = 0; k < name.length(); k++) {

                                    if (searchText.charAt(j) == name.charAt(k)) {
                                        isSearchName = true;
                                        break;
                                    } else {
                                        isSearchName = false;
                                    }
                                }
                            }
                        }

                        if (isSearchName) {
                            list.add(statisticsSearchList.get(i));
                        }
                    }
                    statisticsSearchList = list;
                    statisticsTableModel.remove();

                    for (int i = 0; i < statisticsSearchList.size(); i++) {
                        String[] table = {
                                String.valueOf(statisticsSearchList.get(i).getId()),
                                statisticsSearchList.get(i).getName(),
                                statisticsSearchList.get(i).getGroup(),
                                statisticsSearchList.get(i).getSubGroup(),
                                String.valueOf(statisticsSearchList.get(i).getPrice()),
                                String.valueOf(statisticsSearchList.get(i).getAmountSell()),
                                String.valueOf(statisticsSearchList.get(i).getPriceSell()),
                                statisticsSearchList.get(i).getData()
                        };
                        statisticsTableModel.addDate(table);
                    }
                    statisticsTable.setVisible(false);
                    statisticsTable.setVisible(true);

                } else {
                    statisticsTableModel.remove();

                    for (int i = 0; i < statisticsList.size(); i++) {
                        String[] table = {
                                String.valueOf(statisticsList.get(i).getId()),
                                statisticsList.get(i).getName(),
                                statisticsList.get(i).getGroup(),
                                statisticsList.get(i).getSubGroup(),
                                String.valueOf(statisticsList.get(i).getPrice()),
                                String.valueOf(statisticsList.get(i).getAmountSell()),
                                String.valueOf(statisticsList.get(i).getPriceSell()),
                                statisticsList.get(i).getData()
                        };
                        statisticsTableModel.addDate(table);
                    }
                    statisticsTable.setVisible(false);
                    statisticsTable.setVisible(true);
                }
            }
        });

        statisticsSearchButton = new JButton();
        statisticsSearchButton.setBounds(880, 35, 50, 50);
        statisticsSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!statisticsSearchField.getText().equals("")) {

                    if (statisticsSearchList == null) {
                        statisticsSearchList = statisticsList;
                    }
                    String searchText = statisticsSearchField.getText().toLowerCase();
                    ArrayList<StatisticsEntity> list = new ArrayList<StatisticsEntity>();

                    for (int i = 0; i < statisticsSearchList.size(); i++) {
                        boolean isSearchName = true;
                        String name = statisticsSearchList.get(i).getName().toLowerCase();

                        for (int j = 0; j < searchText.length(); j++) {

                            if (isSearchName) {
                                for (int k = 0; k < name.length(); k++) {

                                    if (searchText.charAt(j) == name.charAt(k)) {
                                        isSearchName = true;
                                        break;
                                    } else {
                                        isSearchName = false;
                                    }
                                }
                            }
                        }

                        if (isSearchName) {
                            list.add(statisticsSearchList.get(i));
                        }
                    }
                    statisticsSearchList = list;
                    statisticsTableModel.remove();

                    for (int i = 0; i < statisticsSearchList.size(); i++) {
                        String[] table = {
                                String.valueOf(statisticsSearchList.get(i).getId()),
                                statisticsSearchList.get(i).getName(),
                                statisticsSearchList.get(i).getGroup(),
                                statisticsSearchList.get(i).getSubGroup(),
                                String.valueOf(statisticsSearchList.get(i).getPrice()),
                                String.valueOf(statisticsSearchList.get(i).getAmountSell()),
                                String.valueOf(statisticsSearchList.get(i).getPriceSell()),
                                statisticsSearchList.get(i).getData()
                        };
                        statisticsTableModel.addDate(table);
                    }
                    statisticsTable.setVisible(false);
                    statisticsTable.setVisible(true);

                } else {
                    statisticsTableModel.remove();

                    for (int i = 0; i < statisticsList.size(); i++) {
                        String[] table = {
                                String.valueOf(statisticsList.get(i).getId()),
                                statisticsList.get(i).getName(),
                                statisticsList.get(i).getGroup(),
                                statisticsList.get(i).getSubGroup(),
                                String.valueOf(statisticsList.get(i).getPrice()),
                                String.valueOf(statisticsList.get(i).getAmountSell()),
                                String.valueOf(statisticsList.get(i).getPriceSell()),
                                statisticsList.get(i).getData()
                        };
                        statisticsTableModel.addDate(table);
                    }
                    statisticsTable.setVisible(false);
                    statisticsTable.setVisible(true);
                }
            }
        });

        statisticsTableModel = new StatisticsTableModel();
        statisticsTable = new JTable(statisticsTableModel);
        statisticsScroll = new JScrollPane(statisticsTable);
        statisticsScroll.setBounds(50, 100, 900, 450);

        statisticsPanel.add(statisticsSearchField);
        statisticsPanel.add(statisticsSearchButton);
        statisticsPanel.add(statisticsScroll);

        clarificationPanel.add(statisticsPanel, "statistics");
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

    private class Action implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == addProduct) {
                dataExchange.transferString("addProduct");
                groupBoxModelAP.removeAllElements();

                for (int i = 0; i <listGroup.size(); i++) {
                    groupBoxModelAP.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "addProductPanel");


            } else if (e.getSource() == delProduct) {
                dataExchange.transferString("delProduct");
                groupBoxModelDP.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupBoxModelDP.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "delProductPanel");


            } else if (e.getSource() == editProduct) {
                dataExchange.transferString("editProduct");
                groupBoxModelEP.removeAllElements();
                newGroupBoxModelEP.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupBoxModelEP.addElement(listGroup.get(i).getGroupName());
                    newGroupBoxModelEP.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "editProductPanel");


            } else if (e.getSource() == addGroup) {
                dataExchange.transferString("addGroup");
                layout.show(clarificationPanel, "addGroupPanel");


            } else if (e.getSource() == delGroup) {
                dataExchange.transferString("delGroup");
                groupComboBoxModelDG.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupComboBoxModelDG.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "delGroup");


            } else if (e.getSource() == editGroup) {
                dataExchange.transferString("editGroup");
                groupComboBoxModelEG.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupComboBoxModelEG.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "editGroupPanel");


            } else if (e.getSource() == addSubGroup) {
                dataExchange.transferString("addSubGroup");
                groupComboBoxModelASG.removeAllElements();

                for (int i = 0; i < listGroup.size(); i ++) {
                    groupComboBoxModelASG.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "addSubGroupPanel");


            } else if (e.getSource() == delSubGroup) {
                dataExchange.transferString("delSubGroup");
                groupComboBoxModelDSG.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupComboBoxModelDSG.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "delSubGroupPanel");


            } else if (e.getSource() == editSubGroup) {
                dataExchange.transferString("editSubGroup");
                groupComboBoxModelESG.removeAllElements();
                newGroupComboBoxModelESG.removeAllElements();

                for (int i = 0; i < listGroup.size(); i++) {
                    groupComboBoxModelESG.addElement(listGroup.get(i).getGroupName());
                    newGroupComboBoxModelESG.addElement(listGroup.get(i).getGroupName());
                }
                layout.show(clarificationPanel, "editSubGroupPanel");



            } else if (e.getSource() == statistics) {
                dataExchange.transferString("statistics");
                int listSize = dataExchange.acceptInt();
                statisticsList = new ArrayList<StatisticsEntity>();

                for (int i = 0; i < listSize; i++) {
                    statisticsList.add(dataExchange.acceptStatisticsEntity());
                }
                statisticsTableModel.remove();

                for (int i = 0; i < statisticsList.size(); i++) {
                    String[] table = {
                            String.valueOf(statisticsList.get(i).getId()),
                            statisticsList.get(i).getName(),
                            statisticsList.get(i).getGroup(),
                            statisticsList.get(i).getSubGroup(),
                            String.valueOf(statisticsList.get(i).getPrice()),
                            String.valueOf(statisticsList.get(i).getAmountSell()),
                            String.valueOf(statisticsList.get(i).getPriceSell()),
                            statisticsList.get(i).getData()
                    };
                    statisticsTableModel.addDate(table);
                }
                statisticsTable.setVisible(false);
                statisticsTable.setVisible(true);
                layout.show(clarificationPanel, "statistics");


            }else if (e.getSource() == updateTable) {
                listSearchTable = null;
                searchField.setText(null);
                updateTable();
            } else if (e.getSource() == searchButton) {

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
                    scroll.setVisible(false);
                    scroll.setVisible(true);
                } else {
                    updateTable();
                }
            } else if (e.getSource() == searchField) {

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

                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setProductId(i + 1);
                    }
                    listSearchTable = list;
                    productTableModel.removeIsAll();

                    for (int i = 0; i < listSearchTable.size(); i++) {
                        System.out.println(i);
                        String[] table = {String.valueOf(listSearchTable.get(i).getProductId()),
                                listSearchTable.get(i).getProductName(),
                                listSearchTable.get(i).getGroupName(),
                                listSearchTable.get(i).getSubGroupName(),
                                String.valueOf(listSearchTable.get(i).getAmount()),
                                String.valueOf(listSearchTable.get(i).getPrice())};
                        productTableModel.addDate(table);
                    }
                    scroll.setVisible(false);
                    scroll.setVisible(true);
                } else {
                    updateTable();
                }
            }
        }
    }
}
