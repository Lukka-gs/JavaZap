package br.senac.rj.javazapservidor.model;

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
		
		Thread sendThread = new Thread(() -> {
			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				out.println(scanner.nextLine());
			}
			scanner.close();
		});
		
		Thread receiveThread = new Thread(() -> {
			while (in.hasNextLine()) {
				System.out.println(in.nextLine());
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
    		server.close();
    		client.close();
    		in.close();
    	}
        
	}
}

