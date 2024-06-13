package br.senac.rj.javazapcliente.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;

import br.senac.rj.javazapcliente.model.Cliente;

public class JanelaClienteChat extends JFrame{

    public static JFrame criarJanelaClienteChat(String ip, int porta, String nomeUsuario) {

		Cliente cliente = new Cliente(ip, porta, nomeUsuario);
    	
        JFrame janelaClienteChat = new JFrame("Client Side");
        janelaClienteChat.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        
        JTextArea mensagem = new JTextArea();
        mensagem.setLineWrap(true);
        mensagem.setWrapStyleWord(true);
		JScrollPane scrollMensagem = new JScrollPane(mensagem);
        scrollMensagem.setBounds(35, 575, 610, 150);
        caixa.add(scrollMensagem);
        
		JButton botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.setBounds(655, 575, 100, 150);
		janelaClienteChat.add(botaoEnviarMensagem);
		botaoEnviarMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				try { 
						String conteudoMensagem = mensagem.getText();
						if (!conteudoMensagem.isEmpty()) {
							conversa.append(nomeUsuario + ": " + conteudoMensagem + "\n");
							mensagem.setText("");
							cliente.enviarMensagem(conteudoMensagem);
						}
						
						
						
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaClienteChat, "Erro ao iniciar o chat: " + erro);
				}
			}
		});

		new Thread(() -> {
			try {
				cliente.iniciarChat(conversa);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaClienteChat, "Erro ao conectar ao servidor: " + e.getMessage()));
			} catch (IOException e) {
				e.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaClienteChat, "Erro de I/O: " + e.getMessage()));
			}
		}).start();

		janelaClienteChat.setVisible(true);
		return janelaClienteChat;
    }
    
    public static void main(String[] args) {       
        JFrame testFrame = criarJanelaClienteChat("127.0.0.1", 10000, "TestUser");
        
    }
}

