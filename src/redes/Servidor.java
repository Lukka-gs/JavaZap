package redes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10000);
        System.out.println("Porta 10000 aberta, aguardando uma conexão");

        Socket client = server.accept();
        System.out.println("Conexão do cliente " + client.getInetAddress().getHostAddress());

        Scanner in = new Scanner(client.getInputStream());
        PrintStream out = new PrintStream(client.getOutputStream());

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
                System.out.println(in.nextLine());
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