package com.example.VEND.view;
import com.example.VEND.model.DadosAnos;
import com.example.VEND.model.DadosFipe;
import com.example.VEND.model.DadosLista;
import com.example.VEND.model.DadosMarca;
import com.example.VEND.util.ConsumoAPi;
import com.example.VEND.util.ConverteJson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaFiltroFipe extends JFrame {

    private JPanel mainPanel;
    private JComboBox<String> veiculoComboBox;
    private JComboBox<String> modeloComboBox;
    private JComboBox<String> marcaComboBox;
    private JButton pesquisarButton;


    private ConverteJson converteJson = new ConverteJson();
    private final String ENDERECO_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private final String ENDERECO_CARROS_MARCAS = ENDERECO_BASE + "carros/marcas";
    private final String ENDERECO_MOTOS_MARCAS = ENDERECO_BASE + "motos/marcas";

    List<DadosLista> marcas;
    DadosMarca marcaSelecionada;
    private List<DadosAnos> anos = new ArrayList<>();
    List<DadosFipe> fipes = new ArrayList<>();

    String codigoMarca = null;
    String codigoModelo = null;

    public TelaFiltroFipe() {
        initializeComponents();
        configurarJanela();
        setupEventListeners();
    }

    private void initializeComponents() {
        setContentPane(mainPanel);

        modeloComboBox.setEnabled(false);
        marcaComboBox.setEnabled(false);
        pesquisarButton.setEnabled(false);

        modeloComboBox.removeAllItems();
        marcaComboBox.removeAllItems();
    }

    private void configurarJanela() {
        setTitle("Fipe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }


    private void setupEventListeners() {
        veiculoComboBox.addActionListener(e -> processarTipoVeiculoSelecionado());

        marcaComboBox.addActionListener(e -> processarMarcaSelecionada());

        modeloComboBox.addActionListener(e -> processarModeloSelecionado());

        pesquisarButton.addActionListener(e -> filtrar());
    }

    private void processarTipoVeiculoSelecionado() {
        String tipoSelecionado = (String) veiculoComboBox.getSelectedItem();

        if (!tipoSelecionado.equals("Selecione")) {
            marcaComboBox.setEnabled(true);
            modeloComboBox.setEnabled(false);
            pesquisarButton.setEnabled(false);


            marcaComboBox.removeAllItems();
            modeloComboBox.removeAllItems();

            carregarMarcas(tipoSelecionado);

        } else {
            marcaComboBox.setEnabled(false);
            modeloComboBox.setEnabled(false);
            pesquisarButton.setEnabled(false);
            marcaComboBox.removeAllItems();
            modeloComboBox.removeAllItems();
        }
    }

    private void carregarMarcas(String tipoVeiculo) {
        String url = null;

        if (tipoVeiculo.equals("Carro")) {
            url = ENDERECO_CARROS_MARCAS;
        } else if (tipoVeiculo.equals("Moto")) {
            url = ENDERECO_MOTOS_MARCAS;
        }

        if (url != null) {
            String jsonMarcas = ConsumoAPi.getJson(url);
            this.marcas = converteJson.getDadosLista(jsonMarcas, DadosLista.class);
            marcaComboBox.addItem("Selecione");
            for (DadosLista marca : marcas) {
                marcaComboBox.addItem(marca.nome());
            }
        }
    }

    private void processarMarcaSelecionada() {
        String marcaSelecionada = (String) marcaComboBox.getSelectedItem();

        if (marcaComboBox.isEnabled() && marcaSelecionada != null && !marcaSelecionada.equals("Selecione")) {
            modeloComboBox.setEnabled(true);
            modeloComboBox.removeAllItems();

            carregarModelos(marcaSelecionada);
        } else {
            modeloComboBox.setEnabled(false);
            modeloComboBox.removeAllItems();
            pesquisarButton.setEnabled(false);
        }
    }

    private void carregarModelos(String marcaSelecionada) {
        String tipoSelecionado = veiculoComboBox.getSelectedItem().toString();

        if (!marcas.isEmpty()) {
            String url = null;
            if (tipoSelecionado.equals("Carro")) {
                url = ENDERECO_CARROS_MARCAS;
            } else if (tipoSelecionado.equals("Moto")) {
                url = ENDERECO_MOTOS_MARCAS;
            }

            // Encontrar o código da marca selecionada pelo nome
            for (DadosLista marca : marcas) {
                if (marca.nome().equals(marcaSelecionada)) {
                    this.codigoMarca = marca.codigo();
                    break;
                }
            }

            if (codigoMarca != null) {
                String urlModelos = url + "/" + codigoMarca + "/modelos";
                String jsonModelos = ConsumoAPi.getJson(urlModelos);
                this.marcaSelecionada = converteJson.getDados(jsonModelos, DadosMarca.class);

                modeloComboBox.addItem("Selecione");

                if (this.marcaSelecionada != null && !this.marcaSelecionada.modelos().isEmpty()) {

                    this.marcaSelecionada.modelos().forEach(m -> modeloComboBox.addItem(m.nome()));
                }
            }
        }

    }

    private void processarModeloSelecionado() {
        String itemSelecionado = (String) modeloComboBox.getSelectedItem();
        String modeloSelecionado = (itemSelecionado != null) ? itemSelecionado : null;
        String tipoSelecionado = (String) veiculoComboBox.getSelectedItem();

        if (modeloSelecionado == null || modeloSelecionado.equals("Selecione")) {
            pesquisarButton.setEnabled(false);
            return;
        }

        carregarAnos(modeloSelecionado, tipoSelecionado);
        carregarDadosFipe(tipoSelecionado);

        if (modeloComboBox.isEnabled()) {
            pesquisarButton.setEnabled(true);
        } else {
            pesquisarButton.setEnabled(false);
        }
    }

    private void carregarAnos(String modeloSelecionado, String tipoSelecionado) {
        if (this.marcaSelecionada == null || this.marcaSelecionada.modelos() == null || modeloSelecionado == null) {
            this.codigoModelo = null;
            return;
        }

        // Encontrar o código do modelo selecionado pelo nome
        for (DadosLista modelo : this.marcaSelecionada.modelos()) {
            if (modelo.nome().equalsIgnoreCase(modeloSelecionado)) {
                this.codigoModelo = modelo.codigo();
                break;
            }
        }
        if (this.codigoModelo == null) {
            JOptionPane.showMessageDialog(this, "Modelo não encontrado na lista de modelos da marca selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String urlAnos = null;
        if (tipoSelecionado.equals("Carro")) {
            urlAnos = ENDERECO_BASE + "carros/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/";
        } else if (tipoSelecionado.equals("Moto")) {
            urlAnos = ENDERECO_BASE + "motos/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/";
        }

        String jsonAnos = ConsumoAPi.getJson(urlAnos);
        this.anos = converteJson.getDadosLista(jsonAnos, DadosAnos.class);
    }

    private void carregarDadosFipe(String veiculoSelecionado) {
        String urlFipe = null;

        for (DadosAnos ano : anos) {
            if (veiculoSelecionado.equals("Carro")) {
                urlFipe = ENDERECO_BASE + "carros/marcas/" + this.codigoMarca+ "/modelos/"+this.codigoModelo + "/anos/" + ano.codigo();
            } else {
                urlFipe = ENDERECO_BASE + "motos/marcas/" + this.codigoMarca+ "/modelos/"+this.codigoModelo + "/anos/" + ano.codigo();
            }

            String jsonDados = ConsumoAPi.getJson(urlFipe);
            DadosFipe fipe = converteJson.getDados(jsonDados, DadosFipe.class);
            fipes.add(fipe);
        }
    }

    private void filtrar() {
        TelaFipe telaFipe = new TelaFipe(fipes);
        telaFipe.setVisible(true);

        JOptionPane.showMessageDialog(telaFipe, "RESULTADOS ENCONTRADOS!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        limparCampos();
    }

    private void limparCampos() {
        veiculoComboBox.setSelectedIndex(0);
        marcaComboBox.removeAllItems();
        modeloComboBox.removeAllItems();
        marcaComboBox.setEnabled(false);
        modeloComboBox.setEnabled(false);
        pesquisarButton.setEnabled(false);
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
