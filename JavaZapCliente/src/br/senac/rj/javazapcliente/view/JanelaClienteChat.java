package br.senac.rj.javazapcliente.view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.senac.rj.javazapcliente.model.Cliente;

public class JanelaClienteChat {
		
	public static JFrame criarJanelaClienteChat(Cliente cliente) {
		JFrame janelaClienteChat = new JFrame("Client Side");
		janelaClienteChat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaClienteChat.setSize(800, 800); 
		
		return janelaClienteChat;
	}
}
