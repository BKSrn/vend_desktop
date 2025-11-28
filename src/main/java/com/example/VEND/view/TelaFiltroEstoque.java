package com.example.VEND.view;

import com.example.VEND.model.Carro;
import com.example.VEND.repository.RepositorioCarro;
import com.example.VEND.service.CarroService;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.util.List;



public class TelaFiltroEstoque extends JFrame{
    private final CarroService carroService;

    private JPanel mainPanel;
    private JTextField marcaTextField;
    private JTextField modeloTextField;
    private JButton filtrarButton;


    public TelaFiltroEstoque(CarroService carroService) {
        this.carroService = carroService;

        configurarListeners();
        setupCampos();

        setTitle("Filtro de Estoque");
        setContentPane(getMainPanel());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void configurarListeners() {
        filtrarButton.addActionListener(e -> abrirTelaListaEstoque());

    }

    private void abrirTelaListaEstoque() {
        String marca = marcaTextField.getText();
        String modelo = modeloTextField.getText();

        this.dispose();
        TelaListaEstoque telaListaEstoque = new TelaListaEstoque(marca, modelo, carroService);
        telaListaEstoque.setVisible(true);
    }

    private void setupCampos() {
        setupCampo(marcaTextField, "Marca");
        setupCampo(modeloTextField, "Modelo");
    }

    private void setupCampo(JTextField textField, String texto) {
        textField.setForeground(Color.BLACK);


        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(texto)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(texto);
                    textField.setFont(new Font("Arial", Font.BOLD, 13));
                }
            }
        });
    }

    // Getter para o painel principal - necessário para adicionar à janela
    public JPanel getMainPanel() {
        return mainPanel;
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
