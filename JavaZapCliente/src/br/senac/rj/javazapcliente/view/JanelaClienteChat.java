package br.senac.rj.javazapcliente.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

import br.senac.rj.javazapcliente.model.Cliente;

public class JanelaClienteChat extends JFrame{

    public static JFrame criarJanelaClienteChat(String ip, int porta, String nomeUsuario) {

		Cliente cliente = new Cliente(ip, porta, nomeUsuario);
    	
        JFrame janelaClienteChat = new JFrame("Client Side");
        janelaClienteChat.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaClienteChat.setSize(800, 700);
        
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
        scrollMensagem.setBounds(35, 575, 610, 50);
        caixa.add(scrollMensagem);
        
		JButton botaoEnviarMensagem = new JButton("Enviar");
		botaoEnviarMensagem.setBounds(655, 575, 100, 50);
		janelaClienteChat.add(botaoEnviarMensagem);
		
		mensagem.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent apertarEnter) {
            	if (apertarEnter.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (apertarEnter.isShiftDown()) {
                        mensagem.append("\n");
                    } else {
                    	apertarEnter.consume();
                        botaoEnviarMensagem.doClick();
                    }
                }
            }
        });
		
		botaoEnviarMensagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				try { 
						String conteudoMensagem = mensagem.getText();
						if (!conteudoMensagem.isEmpty()) {
							LocalDateTime agora = LocalDateTime.now();
							DateTimeFormatter formatadorTimestamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
							String timestampFormatado = agora.format(formatadorTimestamp);
							conversa.append(timestampFormatado + " - " + nomeUsuario + ":\n" + conteudoMensagem + "\n\n");
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
			} catch (UnknownHostException erro) {
				erro.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaClienteChat, "Erro ao conectar ao servidor: " + erro.getMessage()));
			} catch (IOException erro) {
				erro.printStackTrace();
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaClienteChat, "Erro de I/O: " + erro.getMessage()));
			}
		}).start();

		janelaClienteChat.setVisible(true);
		return janelaClienteChat;
    }
}

