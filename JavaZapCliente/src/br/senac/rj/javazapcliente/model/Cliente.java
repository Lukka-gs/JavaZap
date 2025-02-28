package br.senac.rj.javazapcliente.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.*;

public class Cliente {
    private final String ip;
    private final int porta;

    private final String nomeUsuario;
    private PrintStream out; 


    public Cliente(String ip, int porta, String nomeUsuario) {
        this.ip = ip;
        this.porta = porta;
        this.nomeUsuario = nomeUsuario;
    }

    public void iniciarChat(JTextArea conversa) throws IOException {
        Socket client = new Socket(this.ip, this.porta);
        System.out.println("Cliente conectado ao servidor!");

        Scanner receive = new Scanner(client.getInputStream());
        this.out = new PrintStream(client.getOutputStream());

        Thread receiveThread = new Thread(() -> {
            while (receive.hasNextLine()) {
                conversa.append(receive.nextLine() + "\n");
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
        	LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatadorTimestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String timestampFormatado = agora.format(formatadorTimestamp);
            this.out.println(timestampFormatado + " - " + nomeUsuario + ":\n" + mensagem + "\n");
        }
    }
}