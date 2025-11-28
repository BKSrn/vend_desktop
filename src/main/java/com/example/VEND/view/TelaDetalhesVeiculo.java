package com.example.VEND.view;

import com.example.VEND.model.Carro;
import com.example.VEND.repository.RepositorioCarro;
import com.example.VEND.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class TelaDetalhesVeiculo extends JFrame {
    private JPanel contentPane;
    private JPanel painelPrincipal;
    private JPanel painelImagem;
    private JPanel painelInformacoes;
    private JLabel labelImagem;
    private JTextField campoMarca;
    private JTextField campoModelo;
    private JTextField campoPreco;
    private JTextField campoAno;
    private JButton botaoAtualizar;

    private Long carroId;
    private Optional<Carro> carroEscolhido;

    @Autowired
    CarroService carroService;

    public TelaDetalhesVeiculo(Long carroId, CarroService carroService) {
        this.carroId = carroId;
        this.carroService = carroService;

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Detalhes do Veículo");

        inicializarComponentes();
        configurarListeners();
        carregarDadosVeiculo();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void inicializarComponentes() {
        labelImagem.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        labelImagem.setOpaque(true);
        labelImagem.setBackground(Color.WHITE);

        // Permitir edição nos campos
        campoMarca.setEditable(true);
        campoModelo.setEditable(true);
        campoPreco.setEditable(true);
        campoAno.setEditable(true);
    }

    private void configurarListeners() {
        botaoAtualizar.addActionListener(e -> atualizarDados());
    }

    private void carregarDadosVeiculo() {
        carroEscolhido = carroService.buscarPorId(carroId);

        if (carroEscolhido.isPresent()) {

            campoMarca.setText(carroEscolhido.get().getMarca());
            campoModelo.setText(carroEscolhido.get().getModelo());
            campoPreco.setText(String.format("R$ %.2f", carroEscolhido.get().getPreco()));
            campoAno.setText(String.valueOf(carroEscolhido.get().getAno()));

            carregarImagem();
        } else {
            JOptionPane.showMessageDialog(this, "Veículo não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private void carregarImagem() {
        try {
            if (carroEscolhido.get().getImagem() != null && carroEscolhido.get().getImagem().length > 0) {
                ImageIcon icon = new ImageIcon(carroEscolhido.get().getImagem());
                Image image = icon.getImage().getScaledInstance(280, 320, Image.SCALE_SMOOTH);
                labelImagem.setIcon(new ImageIcon(image));
                labelImagem.setText("");

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar imagem: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarDados() {
        Integer ano = Integer.valueOf(campoAno.getText());
        String marca = campoMarca.getText();
        String modelo = campoModelo.getText();
        Double preco = Double.valueOf(campoPreco.getText().replace("R$ ", "").replace(",", "."));

        try {
            carroEscolhido.get().setAno(ano);
            carroEscolhido.get().setMarca(marca);
            carroEscolhido.get().setModelo(modelo);
            carroEscolhido.get().setPreco(preco);

            carroService.cadastrarCarro(carroEscolhido.get());

            carregarDadosVeiculo();

            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dados: " + ex.getMessage());
        }
    }

    public void exibir() {
        setVisible(true);
    }
}
