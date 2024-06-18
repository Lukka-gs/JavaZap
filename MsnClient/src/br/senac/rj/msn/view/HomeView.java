package br.senac.rj.msn.view;

import br.senac.rj.msn.controller.ClientController;
import br.senac.rj.msn.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {

    private JTextField textIP;
    private JTextField textPORT;
    private JTextField textName;
    private JButton loginButton;
    public HomeView() {

        setTitle("Client Side");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(null);

        JLabel labelIP = new JLabel("Server's IP: ");
        labelIP.setBounds(50, 50, 110, 20);
        add(labelIP);

        textIP = new JTextField();
        textIP.setBounds(175, 50, 120, 20);
        add(textIP);

        JLabel labelPORT = new JLabel("Server's Port: ");
        labelPORT.setBounds(50, 100, 110, 20);
        add(labelPORT);

        textPORT = new JTextField();
        textPORT.setBounds(175, 100, 120, 20);
        add(textPORT);

        JLabel labelName = new JLabel("User name: ");
        labelName.setBounds(50, 150, 110, 20);
        add(labelName);

        textName = new JTextField();
        textName.setBounds(175, 150, 120, 20);
        add(textName);

        loginButton = new JButton("Login");
        loginButton.setBounds(140, 200, 100, 20);
        add(loginButton);
        loginButton.addActionListener(this::actionPerformed);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        try {
            String IP = textIP.getText();
            int PORT = Integer.parseInt(textPORT.getText());
            String userName = textName.getText();

            if (IP.isEmpty() || PORT <= 0 || userName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem estar preenchidos");
            } else {
                setVisible(false);
                User userClient = new User(userName);
                ClientController client = new ClientController(IP, PORT, userClient);
                ClientChatView newWindow = new ClientChatView(client);
                client.requestConnection(newWindow);
            }
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar o chat: " + erro);
        }
    };
}
