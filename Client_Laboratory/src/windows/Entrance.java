package windows;

import client.DataExchange;

import javax.swing.*;
import java.awt.event.*;
import java.net.Socket;

public class Entrance implements Runnable {
    private Socket socket;
    private JFrame frame;
    private JPanel panel;
    private JButton entrance;
    private JPasswordField passwordField;
    private JComboBox user;
    private JLabel enterPass, wrongPass, enterUser;
    private String[] userName = {"Покупець", "Продавець", "Адміністратор"};
    private DataExchange dataExchange;

    public Entrance(Socket socket) {
        this.socket = socket;
        dataExchange = new DataExchange(socket);
        createGUI();
        this.run();
    }

    private void createGUI() {
        frame = new JFrame("ВХІД");
        frame.setSize(300, 250);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                dataExchange.transferString("Exit");
            }
        });
        initialization();
        frame.setVisible(true);
    }

    private void initialization() {
        panel = new JPanel(null);
        frame.add(panel);

        enterUser = new JLabel("Виберіть користувача");
        enterUser.setBounds(50, 50, 200, 20);

        user = new JComboBox(userName);
        user.setBounds(50, 70, 200, 20);
        user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (user.getSelectedIndex() == 0) {
                    enterPass.setVisible(false);
                    passwordField.setVisible(false);
                    entrance.setBounds(50, 90, 200, 20);
                } else {
                    enterPass.setVisible(true);
                    passwordField.setVisible(true);
                    entrance.setBounds(50, 130, 200, 20);
                }
            }
        });

        enterPass = new JLabel("Введіть пароль");
        enterPass.setBounds(50, 90, 200, 20);
        enterPass.setVisible(false);

        passwordField = new JPasswordField();
        passwordField.setBounds(50, 110, 200, 20);
        passwordField.setVisible(false);
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initPassword();
            }
        });

        entrance = new JButton("Вхід");
        entrance.setBounds(50, 90, 200, 20);
        entrance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initPassword();
            }
        });

        wrongPass = new JLabel("Невірний пароль");
        wrongPass.setBounds(50,150, 200, 20);
        wrongPass.setVisible(false);

        panel.add(enterUser);
        panel.add(user);
        panel.add(enterPass);
        panel.add(passwordField);
        panel.add(entrance);
        panel.add(wrongPass);
    }

    private void initPassword() {
        wrongPass.setVisible(false);

        dataExchange.transferString("InitPassword");

        int us = user.getSelectedIndex();
        dataExchange.transferInt(us);

        if (us != 0) {
            char[] pass = passwordField.getPassword();
            String password = "";
            for (int i = 0; i < pass.length; i++) {
                password += pass[i];
            }
            dataExchange.transferString(password);
        }
        if (dataExchange.acceptBoolean()) {

            switch (us) {
                case 0:
                    new Buyer(dataExchange);
                    break;
                case 1:
                    new Seller(dataExchange);
                    break;
                case 2:
                    new Administrator(dataExchange);
                    break;
            }
            close();
        } else {
            wrongPass.setVisible(true);
        }
    }

    private void close() {
        frame.dispose();
    }

    @Override
    public void run() {

    }
}

