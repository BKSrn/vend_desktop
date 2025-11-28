package com.example.VEND.view;

import com.example.VEND.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

@org.springframework.stereotype.Component
public class TelaPrincipal extends JFrame {
    @Autowired
    private CarroService carroService;

    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel centerPanel;
    private JPanel buttonsPanel;
    private JLabel titleLabel;
    private JButton fipeButton;
    private JButton estoqueButton;
    private JButton registrarButton;
    private JButton agenteButton;

    public TelaPrincipal() {
        setContentPane(mainPanel);
        configurarJanela();
        configurarBotoes();
        setupEventListeners();
    }

    private void setupEventListeners() {
        estoqueButton.addActionListener(e -> abrirTelaFiltroEstoque());
        fipeButton.addActionListener(e -> abrirTelaFiltroFipe());
        registrarButton.addActionListener(e -> abrirTelaRegistrarVeiculo());
        agenteButton.addActionListener(e -> abrirTelaAssistenteGemini());
    }

    private void abrirTelaAssistenteGemini() {
        TelaAssistenteGemini telaAssistenteGemini = new TelaAssistenteGemini();
        telaAssistenteGemini.setVisible(true);

    }

    private void abrirTelaRegistrarVeiculo(){
        TelaRegistrarVeiculo telaRegistrarVeiculo = new TelaRegistrarVeiculo(carroService);
        telaRegistrarVeiculo.setVisible(true);

    }

    private void abrirTelaFiltroFipe(){
        TelaFiltroFipe telaFiltroFipe = new TelaFiltroFipe();
        telaFiltroFipe.setVisible(true);

    }

    private void abrirTelaFiltroEstoque(){
        TelaFiltroEstoque telaFiltroEstoque = new TelaFiltroEstoque(carroService);
        telaFiltroEstoque.setVisible(true);

    }

    private void configurarJanela() {
        setTitle("Painel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(true);

        // Define ícone da aplicação
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void configurarBotoes() {
        estoqueButton = formatarBotao("Estoque");
        fipeButton = formatarBotao("Fipe");
        registrarButton = formatarBotao("Registrar");
        agenteButton = formatarBotao("Agente Gemini");

        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        // Adicionar espaçamento entre os botões
        buttonsPanel.add(Box.createVerticalStrut(10)); // Espaço no topo
        buttonsPanel.add(estoqueButton);
        buttonsPanel.add(Box.createVerticalStrut(15)); // Espaço entre botões
        buttonsPanel.add(fipeButton);
        buttonsPanel.add(Box.createVerticalStrut(15)); // Espaço entre botões
        buttonsPanel.add(registrarButton);
        buttonsPanel.add(Box.createVerticalStrut(15)); // Espaço entre botões
        buttonsPanel.add(agenteButton);
        buttonsPanel.add(Box.createVerticalStrut(10)); // Espaço na base

        buttonsPanel.add(estoqueButton);
        buttonsPanel.add(fipeButton);
        buttonsPanel.add(registrarButton);
        buttonsPanel.add(agenteButton);
    }

    private JButton formatarBotao(String texto){
        JButton botao = new  JButton(texto);
        botao.setFont(new Font("Arial", Font.PLAIN, 12));
        botao.setMaximumSize(new Dimension(200, 45));
        botao.setMinimumSize(new Dimension(200, 45));
        botao.setPreferredSize(new Dimension(200, 45));
        // Centralizar o texto
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                botao.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                botao.setBackground(new Color(220, 220, 220));
            }
        });

        return botao;
    }

    private void createUIComponents() {
        mainPanel = new JPanel() {
            private Image backgroundImage;

            {
                try {
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

