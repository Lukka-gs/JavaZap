package redes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket client = new Socket("127.0.0.1", 10000);
        System.out.println("Cliente conectado ao servidor!");

        Scanner s = new Scanner(System.in);
        Scanner in = new Scanner(client.getInputStream());
        PrintStream out = new PrintStream(client.getOutputStream());

        // Thread para enviar mensagens ao servidor
        Thread sendThread = new Thread(() -> {
            while (s.hasNextLine()) {
                out.println(s.nextLine());
            }
        });
        sendThread.start();

        // Receber mensagens do servidor
        Thread receiveThread = new Thread(() -> {
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        });
        receiveThread.start();

        // Aguardar que as threads terminem
        try {
            sendThread.join();
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//Fechar recursos
        sendThread.interrupt();
        receiveThread.interrupt();
        client.close();
        s.close();
        in.close();
    }
}