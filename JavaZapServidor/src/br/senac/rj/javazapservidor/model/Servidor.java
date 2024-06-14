package br.senac.rj.javazapservidor.model;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.PrintStream;


public class Servidor {
	private ServerSocket server;
	private PrintStream out;
	private final int PORT = 10000;
	public Servidor() {
		try {
			server = new ServerSocket(PORT);
			System.out.println("Servidor iniciado na porta 10000");
		} catch (IOException e) {
			System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
		}
	}
	public int getPORT() {
		return PORT;
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
			LocalDateTime agora = LocalDateTime.now();
			DateTimeFormatter formatadorTimestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String timestampFormatado = agora.format(formatadorTimestamp);
            this.out.println(timestampFormatado + " - Servidor:\n" + mensagem + "\n");
		}
	}

	public String verificaIp() {
		String ipAddress = "Desconhecido";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ipAddress;
	}

}

