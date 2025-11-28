package com.example.VEND.view;

import com.example.VEND.util.ConsumoGemini;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

public class TelaAssistenteGemini extends JFrame {
    private JPanel mainPanel;
    private JTextField searchTextField;
    private JButton searchButton;


    public TelaAssistenteGemini() {
        setContentPane(mainPanel);
        setTitle("Agente VEND");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        initializeComponents();

        setupEventListeners();

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void initializeComponents() {
        setupPlaceholder();
    }

    private void setupPlaceholder() {
        searchTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Tire suas dúvidas")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText("Tire suas dúvidas");
                    searchTextField.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }

    private void setupEventListeners() {
        searchButton.addActionListener(e -> processarBusca());
    }

    private void processarBusca() {
        String perguntaUser = searchTextField.getText();

        if (!perguntaUser.isEmpty() && !perguntaUser.equals("Tire suas dúvidas")) {
            processarGeminiQuery(perguntaUser);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, digite uma pergunta para o assistente Gemini!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void processarGeminiQuery(String perguntaUser) {
        String resposta = ConsumoGemini.consumirGemini(perguntaUser);

        if (resposta.isEmpty()){
            JOptionPane.showMessageDialog(this, "O assistente nao conseguiu processar sua pergunta.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        try {
            JOptionPane.showMessageDialog(this, resposta, "Agente", JOptionPane.INFORMATION_MESSAGE);

            limpaCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar a consulta: " + e.getMessage() , "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpaCampos() {
        searchTextField.setText("Tire suas dúvidas");
        searchTextField.setForeground(java.awt.Color.GRAY);
        searchTextField.requestFocus();
    }

    private void createUIComponents() {
        mainPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
                    // Carrega a imagem como recurso do classpath
                    backgroundImage = ImageIO.read(getClass().getResource("/imagemFundoView.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }


}