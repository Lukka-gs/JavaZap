package br.senac.rj.javazapcliente.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private String ip;
	private int porta;
	private String nomeUsuario;
	
	public Cliente(String ip, int porta, String nomeUsuario) {
		this.ip = ip;
		this.porta = porta;
		this.nomeUsuario = nomeUsuario;
	}
	
	public void iniciarChat() throws UnknownHostException, IOException { //Tirar esse throws!!!!!!!
		Socket client = new Socket(this.ip, this.porta);
		System.out.println("Cliente conectado ao servidor!");
		
		Scanner send = new Scanner(System.in);
		Scanner receive = new Scanner(client.getInputStream());
		PrintStream out = new PrintStream(client.getOutputStream());
		
		Thread sendThread = new Thread(() -> {
			while (send.hasNextLine()) {
				out.println(send.nextLine());
			}
		});
		
		Thread receiveThread = new Thread(() -> {
			while (receive.hasNextLine()) {
				System.out.println(receive.nextLine());
			}
		});
		
		sendThread.start();
		receiveThread.start();
		
        try {
            sendThread.join();
            receiveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
    		send.close();
    		client.close();
    		receive.close();
    	}
	}
}