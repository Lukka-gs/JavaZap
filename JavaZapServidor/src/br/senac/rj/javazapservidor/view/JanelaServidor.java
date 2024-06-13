package br.senac.rj.javazapservidor.view;

import br.senac.rj.javazapservidor.model.Servidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

public class JanelaServidor extends JFrame {
    public static JFrame criarJanelaServidor(Servidor servidor) {

        JFrame janelaServidor = new JFrame("Server Side");
        janelaServidor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaServidor.setSize(800, 800);

        Container caixa = janelaServidor.getContentPane();
        caixa.setLayout(null);

        JTextArea conversa = new JTextArea();
        conversa.setEditable(false);
        conversa.setBackground(Color.WHITE);
        janelaServidor.add(conversa);
        JScrollPane scrollConversa = new JScrollPane(conversa);
        scrollConversa.setBounds(45, 50, 700, 500);
        janelaServidor.add(scrollConversa);

        JTextArea mensagem = new JTextArea();
        mensagem.setLineWrap(true);
        mensagem.setWrapStyleWord(true);
        JScrollPane scrollMensagem = new JScrollPane(mensagem);
        scrollMensagem.setBounds(35, 575, 610, 150);
        caixa.add(scrollMensagem);

        JButton botaoEnviarMensagem = new JButton("Enviar");
        botaoEnviarMensagem.setBounds(655, 575, 100, 150);
        janelaServidor.add(botaoEnviarMensagem);
        botaoEnviarMensagem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                try {
                    String conteudoMensagem = mensagem.getText();
                    if (!conteudoMensagem.isEmpty()) {
                        conversa.append("Servidor: " + conteudoMensagem + "\n");
                        mensagem.setText("");
                        servidor.enviarMensagem(conteudoMensagem);
                    }



                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(janelaServidor, "Erro ao iniciar o chat: " + erro);
                }
            }
        });

        new Thread(() -> {
            try {
                servidor.conectarCliente(conversa);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaServidor, "Erro ao conectar ao servidor: " + e.getMessage()));
            } catch (IOException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(janelaServidor, "Erro de I/O: " + e.getMessage()));
            }
        }).start();

        janelaServidor.setVisible(true);
        return janelaServidor;
    }

    public static void main(String[] args) {
        Servidor server = new Servidor();
        JFrame testFrame = criarJanelaServidor(server);

    }
}
