package com.example.VEND.view;

import com.example.VEND.model.DadosFipe;
import com.example.VEND.util.ConverteJson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaFipe extends JFrame {
    private JPanel mainPanel;
    private JScrollPane tableScrollPane;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    private String codigoMarca;
    private String codigoModelo;
    private String veiculoSelecionado;

    List<DadosFipe> fipes = new ArrayList<>();

    private final String ENDERECO_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConverteJson converteJson = new ConverteJson();

    public TelaFipe(List<DadosFipe> fipes) {
        this.fipes = fipes;

        initializeComponents();
        setupTable();
        configurarJanela();


        popularTabelaFipe();
    }



    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Valor", "Ano", "Marca", "Modelo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela apenas de leitura
            }
        };

        dataTable = new JTable(tableModel);
        tableScrollPane = new JScrollPane(dataTable);
    }

    private void setupTable() {
        dataTable.setRowHeight(25);
        dataTable.setGridColor(Color.LIGHT_GRAY);
        dataTable.setShowGrid(true);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.setSelectionBackground(new Color(184, 230, 255));
        dataTable.setFillsViewportHeight(true);

        dataTable.getTableHeader().setBackground(new Color(135, 206, 235));
        dataTable.getTableHeader().setForeground(Color.BLACK);
        dataTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        dataTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Valor
        dataTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Ano
        dataTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Marca
        dataTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Modelo

        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private void configurarJanela() {
        setContentPane(mainPanel);
        setTitle("Tabela Fipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void popularTabelaFipe() {
//        limparTabela();
        if (fipes == null || fipes.isEmpty()){
            JOptionPane.showMessageDialog(this, "Nenhum dado encontrado para os critérios selecionados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }

        for (DadosFipe fipe : fipes) {
            Object[] linha = {
                    fipe.valor(),
                    fipe.ano(),
                    fipe.marca(),
                    fipe.modelo()
            };
            tableModel.addRow(linha);
        }
    }

    public void limparTabela() {
        tableModel.setRowCount(0);
    }
}
