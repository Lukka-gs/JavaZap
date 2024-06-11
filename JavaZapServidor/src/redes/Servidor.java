package redes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Servidor {

    private static JTextArea messageArea;
    private static JTextField inputField;
    private static JTextField ipField;
    private static JTextField portField;
    private static PrintStream out;

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Servidor - Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JLabel ipLabel = new JLabel("IP da Conexão:");
        ipField = new JTextField();
        ipField.setEditable(false);

        JLabel portLabel = new JLabel("Porta:");
        portField = new JTextField();
        portField.setEditable(false);

        topPanel.add(ipLabel);
        topPanel.add(ipField);
        topPanel.add(portLabel);
        topPanel.add(portField);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        inputField = new JTextField();
        JButton sendButton = new JButton("Enviar");
        

        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    out.println(message);
                    messageArea.append("Servidor: " + message + "\n");
                    inputField.setText("");
                }
            }
        };

        sendButton.addActionListener(sendAction);

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendAction.actionPerformed(null);
                }
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        ServerSocket server = new ServerSocket(10000);
        portField.setText("10000");
        messageArea.append("Porta 10000 aberta, aguardando uma conexão\n");

        Socket client = server.accept();
        String clientIp = client.getInetAddress().getHostAddress();
        messageArea.append("Conexão do cliente " + clientIp + "\n");
        ipField.setText(clientIp);

        Scanner in = new Scanner(client.getInputStream());
        out = new PrintStream(client.getOutputStream());

        // Thread para enviar mensagens ao cliente
        Thread sendThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
            }
        });
        sendThread.start();

        // Receber mensagens do cliente
        Thread receiveThread = new Thread(() -> {
            while (in.hasNextLine()) {
                String message = in.nextLine();
                messageArea.append("Cliente: " + message + "\n");
            }
        });
        receiveThread.start();

        try {
            sendThread.join();
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fechar recursos
        sendThread.interrupt();
        server.close();
        client.close();
        in.close();
    }
}
