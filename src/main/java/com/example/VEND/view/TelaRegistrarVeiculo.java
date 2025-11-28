package com.example.VEND.view;

import com.example.VEND.model.Carro;
import com.example.VEND.model.enums.Carroceria;
import com.example.VEND.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelaRegistrarVeiculo extends JFrame {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JComboBox<Carroceria> carroceriaComboBox;
    private JTextField marcaTextField;
    private JTextField precoTextField;
    private JTextField modeloTextField;
    private JTextField anoTextField;
    private JButton btnRegistrar;
    private JButton btnSelecionarImagem;
    private JLabel lblImagemPreview;
    private File imagemSelecionada;
    private int anoAtual = LocalDate.now().getYear();

    @Autowired
    private CarroService carroService;

    public TelaRegistrarVeiculo(CarroService carroService) {
        this.carroService = carroService;

        initComponents();
        setupCampos();
        setupEventos();

        setTitle("Registrar Veículo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }

    private void initComponents() {
        // configura comboBox carroceria
        List<Carroceria> listaCarrocerias = new ArrayList<>();
        for (Carroceria carroceria : Carroceria.values()) {
            listaCarrocerias.add(Carroceria.fromString(carroceria.toString()));
        }
        Carroceria[] carrocerias = listaCarrocerias.toArray(new Carroceria[0]);
        carroceriaComboBox.setModel(new DefaultComboBoxModel<>(carrocerias));

        btnSelecionarImagem.addActionListener(this::selecionarImagem);
    }

    private void setupCampos() {
        setupCampo(marcaTextField, "Marca");
        setupCampo(precoTextField, "Preço");
        setupCampo(modeloTextField, "Modelo");
        setupCampo(anoTextField, "Ano");
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

    private void setupEventos() {
        btnRegistrar.addActionListener(e -> registrarVeiculo());
    }

    private void selecionarImagem(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecionar Imagem do Veículo");

        // Filtro para arquivos
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Arquivos de Imagem", "jpg", "jpeg", "png", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = fileChooser.getSelectedFile();

            if (imagemSelecionada.length() > 10 * 1024 * 1024) {
                JOptionPane.showMessageDialog(this,
                        "Arquivo muito grande! Máximo: 10MB",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Carregar a imagem para preview
                ImageIcon imageIcon = new ImageIcon(imagemSelecionada.getAbsolutePath());
                Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                lblImagemPreview.setIcon(new ImageIcon(image));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem: " + ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void registrarVeiculo() {
        byte[] imagemBytes;
        String carroceria = "";
        String marca = "";
        Double preco = 0.0;
        String modelo = "";
        Integer ano = 0;

        if (carroceriaComboBox.getSelectedItem().toString().equalsIgnoreCase("SELECIONE")
                || marcaTextField.getText().isEmpty() || precoTextField.getText().isEmpty()
                || modeloTextField.getText().isEmpty() || anoTextField.getText().describeConstable().isEmpty() || imagemSelecionada == null) {

            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            carroceria = carroceriaComboBox.getSelectedItem().toString().toLowerCase();
            marca = marcaTextField.getText().toLowerCase();
            preco = Double.valueOf(precoTextField.getText().toLowerCase());
            modelo = modeloTextField.getText().toLowerCase();
            ano = Integer.valueOf(anoTextField.getText().toLowerCase());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Informe os campos com os caracteres corretos",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            imagemBytes = Files.readAllBytes(imagemSelecionada.toPath());
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao transformar imagem" + ex.getMessage());
        }

        if (ano > anoAtual){
            JOptionPane.showMessageDialog(this, "O ano não pode ser maior que o atual!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Carro carro = new Carro(carroceria, imagemBytes, modelo, ano, preco, marca);
        carroService.cadastrarCarro(carro);

        String mensagem = String.format(
                """
                        Veículo registrado com sucesso!
                        
                        Carroceria: %s
                        Marca: %s
                        Modelo: %s
                        Ano: %s
                        Preço: %s
                        Nome Imagem: %s
                        """,
                carroceria, marca, modelo, ano, preco, imagemSelecionada.getName());

        JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        limparFormulario();

    }

    private void limparFormulario() {
        marcaTextField.setText("Marca");
        marcaTextField.setForeground(Color.BLACK);

        precoTextField.setText("Preço");
        precoTextField.setForeground(Color.BLACK);

        modeloTextField.setText("Modelo");
        modeloTextField.setForeground(Color.BLACK);

        anoTextField.setText("Ano");
        anoTextField.setForeground(Color.BLACK);

        carroceriaComboBox.setSelectedIndex(0);
        lblImagemPreview.setIcon(null);
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
