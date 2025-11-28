package com.example.VEND.view;

import com.example.VEND.VendApplication;
import com.example.VEND.model.UsuarioAdm;
import com.example.VEND.util.CriptografiaUtil;
import com.example.VEND.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

@Component
public class TelaCadastro extends JFrame {
    @Autowired
    private UsuarioService usuarioService;

    private JPanel mainPanel;
    private JLabel userIconLabel;
    private JLabel adminLabel;
    private JTextField emailField;
    private JButton cadastrarButton;
    private JPasswordField senhaField;
    private JLabel loginLabel;

    public TelaCadastro() {
        setTitle("Cadastro");
        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        setupCampos();
        setupEventListeners();

        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado");
        }
    }


    private void setupEventListeners() {
        cadastrarButton.addActionListener(e -> cadastrar());

        loginLabel.addMouseListener(new MouseAdapter() {;
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirTelaLogin();
            }
        });
    }

    private void setupCampos() {
        setupCampo(emailField, "Email");
        setupCampo(senhaField, "Senha");
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
                }
            }
        });
    }

    private void cadastrar() {
        String email = emailField.getText();
        String senhaHash = CriptografiaUtil.gerarHash(senhaField.getText());

        if (usuarioService.validarCadastro(email, senhaHash)){
            try {
                UsuarioAdm usuarioAdm = new UsuarioAdm(email, senhaHash);
                usuarioService.cadastrarUsuario(usuarioAdm);

                JOptionPane.showMessageDialog(this,
                        "Usuário cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);

                abrirTelaLogin();
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erro ao salvar usuário: " + ex.getCause() + '\n' + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void limpaCampos() {
        emailField.setText("");
        senhaField.setText("");
    }

    private void abrirTelaLogin() {
        this.dispose();
        TelaLogin telaLogin = VendApplication.getTela(TelaLogin.class);
        telaLogin.setVisible(true);
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

