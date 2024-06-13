package br.senac.rj.javazapcliente.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class JanelaClienteMenu {

	public static void Menu() {
		JFrame janelaMenu = new JFrame("Client Side");
		janelaMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		janelaMenu.setSize(400, 400);
		
		Container caixa = janelaMenu.getContentPane();
		caixa.setLayout(null);
		
		JLabel labelIP = new JLabel("IP do Servidor: ");
		labelIP.setBounds(50, 50, 110, 20);
		janelaMenu.add(labelIP);
		
		JTextField jTextIP = new JTextField();
		jTextIP.setBounds(175, 50, 120, 20);
		janelaMenu.add(jTextIP);
		
		JLabel labelPorta = new JLabel("Porta do Servidor: ");
		labelPorta.setBounds(50, 100, 110, 20);
		janelaMenu.add(labelPorta);
		
		JTextField jTextPorta = new JTextField();
		jTextPorta.setBounds(175, 100, 120, 20);
		janelaMenu.add(jTextPorta);
		
		JLabel labelNomeUsuario = new JLabel("Seu nome no Chat: ");
		labelNomeUsuario.setBounds(50, 150, 110, 20);
		janelaMenu.add(labelNomeUsuario);
		
		JTextField jTextNomeUsuario = new JTextField();
		jTextNomeUsuario.setBounds(175, 150, 120, 20);
		janelaMenu.add(jTextNomeUsuario);
		
		JButton botaoIniciarChat = new JButton("Iniciar Chat");
		botaoIniciarChat.setBounds(140, 200, 100, 20);
		janelaMenu.add(botaoIniciarChat);
		
		botaoIniciarChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				try {
						String ip = jTextIP.getText();
						int porta = Integer.parseInt(jTextPorta.getText());
						String nomeUsuario = jTextNomeUsuario.getText();
						
						if(ip.isEmpty() || porta <= 0 || nomeUsuario.isEmpty()) {
							JOptionPane.showMessageDialog(janelaMenu,"Todos os campos devem estar preenchidos");
						} else {
							janelaMenu.setVisible(false);
							JanelaClienteChat.criarJanelaClienteChat(ip, porta, nomeUsuario);
						}
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(janelaMenu, "Erro ao iniciar o chat: " + erro);
				}
			}
		});
		janelaMenu.setVisible(true);
	}
	
	public static void main(String[] args) {
		Menu();
	}
}
