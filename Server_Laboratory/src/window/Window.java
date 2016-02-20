package window;

import javax.swing.*;
import java.awt.*;

public class Window extends Thread {
    private JFrame frame;
    private JPanel panel;
    private JTextArea textClient;
    private JScrollPane scroll;
    private String client = "";

    public Window() {
        frame = new JFrame("Сервер");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initPanel();
        this.start();
    }

    private void initPanel() {
        panel = new JPanel(new GridLayout());

        textClient = new JTextArea();
        textClient.setEnabled(false);
        textClient.setDisabledTextColor(Color.BLACK);
        textClient.setSelectedTextColor(Color.GREEN);
        scroll = new JScrollPane(textClient);

        panel.add(scroll);
        frame.add(panel);
    }

    public void setTextClient(String client) {
        this.client += client + "\n";
        textClient.setText(this.client);
        panel.setVisible(false);
        panel.setVisible(true);
    }

    @Override
    public void run() {
        super.run();
        frame.setVisible(true);
    }
}
