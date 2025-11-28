package com.example.VEND.view;

import com.example.VEND.model.Carro;
import com.example.VEND.repository.RepositorioCarro;
import com.example.VEND.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class TelaListaEstoque extends JFrame {
    private JTable tabelaVeiculos;
    private JButton voltarButton;
    private JButton adicionarButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JPanel painelBotoes;
    private JButton detalhesButton;
    private JButton atualizarButton;


    private DefaultTableModel modeloTabela;
    private String filtroMarca;
    private String filtroModelo;
    List<Carro> carrosEncontrados;

    @Autowired
    private CarroService carroService;

    public TelaListaEstoque(String marca, String modelo, CarroService carroService) {
        this.carroService = carroService;
        this.filtroMarca = marca.equalsIgnoreCase("Marca") ? "Todas" : marca;
        this.filtroModelo = modelo.equalsIgnoreCase("Modelo") ? "Todos" : modelo;

        inicializarComponentes();
        configurarListeners();
        carregarDados();

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Lista do Estoque");
        setSize(800, 600);
        setLocationRelativeTo(null);
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void inicializarComponentes() {
        // Configura colunas da tabela
        String[] colunas = {"ID", "Carroceria", "Imagem", "Marca", "Modelo", "Ano", "Preço"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela nao editável
            }
        };

        // Aplicar o modelo à tabela existente
        tabelaVeiculos.setModel(modeloTabela);
        tabelaVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaVeiculos.getTableHeader().setReorderingAllowed(false);

    }

    private void configurarListeners() {
        detalhesButton.addActionListener(e -> abrirTelaDetalhesVeiculo());

        voltarButton.addActionListener(e -> voltarParaTelaFiltro());

        atualizarButton.addActionListener(e -> atualizarLista());

        adicionarButton.addActionListener(e -> adicionarVeiculo());

        excluirButton.addActionListener(e -> excluirVeiculo());
    }

    private void carregarDados() {
        limparTabela();

        if (filtroMarca.equals("Todas") && filtroModelo.equals("Todos")) {
            carrosEncontrados = carroService.listarTodos();

        } else if (!filtroMarca.equals("Todas") && filtroModelo.equals("Todos")) {
            carrosEncontrados = carroService.buscarPorMarca(filtroMarca.toLowerCase());

        }else{
            carrosEncontrados = carroService.buscarPorMarcaModelo(filtroMarca.toLowerCase(), filtroModelo.toLowerCase());
        }


        if (carrosEncontrados.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nenhum veículo encontrado com os filtros aplicados.",
                    "Nenhum Resultado", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }

        carrosEncontrados.forEach(c -> {
            Object[] linha = {
                    c.getId(),
                    c.getCarroceria(),
                    c.getImagem() != null ? "Sim" : "Não",
                    c.getMarca(),
                    c.getModelo(),
                    c.getAno(),
                    String.format("R$ %.2f", c.getPreco())
            };

            modeloTabela.addRow(linha);
        });

    }

    private void abrirTelaDetalhesVeiculo() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para ver os detalhes.");
            return;
        }

        Long carroId = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);

        // instanciei a tela de detalhes do veículo e chamei o metodo exibir
        SwingUtilities.invokeLater(() -> {
            TelaDetalhesVeiculo telaDetalhesVeiculo = new TelaDetalhesVeiculo(carroId, carroService);
            telaDetalhesVeiculo.exibir();
        });
    }

    private void voltarParaTelaFiltro() {
        dispose();
        SwingUtilities.invokeLater(() -> {

            TelaFiltroEstoque telaFiltroEstoque = new TelaFiltroEstoque(carroService);
            telaFiltroEstoque.setVisible(true);
        });
    }

    private void adicionarVeiculo() {
        TelaRegistrarVeiculo telaRegistrarVeiculo = new TelaRegistrarVeiculo(carroService);
        telaRegistrarVeiculo.setVisible(true);
    }

    private void excluirVeiculo() {
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para excluir.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este veículo?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            Long carroId = (Long) modeloTabela.getValueAt(linhaSelecionada, 0);

            try {
                carroService.deletarCarro(carroId);

                atualizarLista();
                JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao excluir veículo: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else {
            atualizarLista();
        }

    }

    public void atualizarLista() {
        carregarDados();
    }

    public void limparTabela() {
        modeloTabela.setRowCount(0); // Remove todas as linhas
    }

}
