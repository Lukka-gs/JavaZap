package br.senac.rj.javazapcliente.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1", 10000);
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