package br.senac.rj.javazapservidor.model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintStream;


public class Servidor {
	private ServerSocket server;
	private PrintStream out;
	public Servidor() {
		try {
			server = new ServerSocket(10000);
			System.out.println("Servidor iniciado na porta 10000");
		} catch (IOException e) {
			System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
		}
	}
	public void conectarCliente(JTextArea conversa, JTextArea mensagem, JButton botaoEnviarMensagem) throws IOException {
		Socket client = server.accept();
		mensagem.setEditable(true);
		mensagem.setBackground(Color.WHITE);
		botaoEnviarMensagem.setEnabled(true);
		conversa.setText("");

		Scanner in = new Scanner(client.getInputStream());
		this.out = new PrintStream(client.getOutputStream());

		Thread receiveThread = new Thread(() -> {
			while (in.hasNextLine()) {
				conversa.append(in.nextLine() + "\n");
			}
		});

		receiveThread.start();

		try {
			receiveThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			in.close();
			client.close();
		}
	}
	public void enviarMensagem(String mensagem) {
		if (this.out != null) {
			this.out.println("Servidor: " + mensagem);
		} else  {
            out.close();
		}
	}
}

