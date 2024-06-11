package br.senac.rj.javazapcliente.view;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import br.senac.rj.javazapcliente.model.Cliente;

/*public class JanelaClienteChat {
		
	public static JFrame criarJanelaClienteChat(Cliente cliente) {
		JFrame janelaClienteChat = new JFrame("Client Side");
		janelaClienteChat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		janelaClienteChat.setSize(800, 800); 
		
		JTextArea conversa = new JTextArea();
		conversa.setEditable(false);
		JScrollPane scrollConversa = new JScrollPane(conversa);
		scrollConversa.setBounds(10, 10, 360, 340);
        janelaClienteChat.add(scrollConversa);
		
		
		return janelaClienteChat;
	}
}*/

public class JanelaClienteChat {

    public static JFrame criarJanelaClienteChat(Cliente cliente) {
        JFrame janelaClienteChat = new JFrame("Client Side");
        janelaClienteChat.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        janelaClienteChat.setSize(800, 800);
        
        Container caixa = janelaClienteChat.getContentPane();
        caixa.setLayout(null);
        
        JTextArea conversa = new JTextArea();
        conversa.setEditable(false);
        conversa.setBackground(Color.WHITE);
        janelaClienteChat.add(conversa);
        JScrollPane scrollConversa = new JScrollPane(conversa);
        scrollConversa.setBounds(45, 50, 700, 500);
        janelaClienteChat.add(scrollConversa);
        for (int i = 1; i <= 100; i++) {
            conversa.append("This is line number " + i + " in the chat area.\n");
        }
        
        JTextArea mensagem = new JTextArea();
        mensagem.setLineWrap(true);
        mensagem.setWrapStyleWord(true);
		
		JScrollPane scrollMensagem = new JScrollPane(mensagem);
        scrollMensagem.setBounds(35, 575, 610, 150);
        caixa.add(scrollMensagem);
        
		JButton botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.setBounds(655, 575, 100, 150);
		janelaClienteChat.add(botaoEnviarMensagem);
		
		
		
        return janelaClienteChat;
    }
    
    public static void main(String[] args) {
        Cliente cliente = new Cliente("127.0.0.1", 12345, "TestUser");        
        JFrame testFrame = criarJanelaClienteChat(cliente);
        testFrame.setVisible(true);
    }
}

