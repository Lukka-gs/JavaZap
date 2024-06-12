package br.senac.rj.javazapcliente.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

public class Cliente {
    private String ip;
    private int porta;
    private String nomeUsuario;
    private PrintStream out; // Armazenar o PrintStream como campo de instância

    public Cliente(String ip, int porta, String nomeUsuario) {
        this.ip = ip;
        this.porta = porta;
        this.nomeUsuario = nomeUsuario;
    }

    public void iniciarChat(JTextArea conversa) throws UnknownHostException, IOException {
        Socket client = new Socket(this.ip, this.porta);
        System.out.println("Cliente conectado ao servidor!");

        Scanner receive = new Scanner(client.getInputStream());
        this.out = new PrintStream(client.getOutputStream()); // Inicializar o PrintStream

        Thread receiveThread = new Thread(() -> {
            while (receive.hasNextLine()) {
                conversa.append("Servidor diz: " + receive.nextLine() + "\n");
            }
        });

        receiveThread.start();

        try {
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            receive.close();
            client.close();
        }
    }

    public void enviarMensagem(String mensagem) {
        if (this.out != null) {
            this.out.println(nomeUsuario + ": " + mensagem);
        }
    }
}