////package com.example.VEND.view;
////
////import com.example.VEND.model.UsuarioAdm;
////import com.example.VEND.repository.RepositorioCarro;
////import com.example.VEND.repository.RepositorioUsuarioAdm;
////import com.example.VEND.service.UsuarioService;
////
////import javax.swing.*;
////import java.awt.*;
////import java.awt.event.FocusAdapter;
////import java.awt.event.FocusEvent;
////import java.awt.event.MouseAdapter;
////import java.awt.event.MouseEvent;
////import java.util.Optional;
////
//public class TelaLogin extends JFrame {
//    private RepositorioUsuarioAdm repositorioUsuarioAdm;
//    private RepositorioCarro repositorioCarro;
//
//    private JPanel mainPanel;
//    private JLabel userIconLabel;
//    private JLabel adminLabel;
//    private JTextField emailField;
//    private JButton loginButton;
//    private JPasswordField senhaField;
//    private JLabel cadastrarLabel;
//
//    public TelaLogin(RepositorioUsuarioAdm repositorioUsuarioAdm, RepositorioCarro repositorioCarro) {
//        this.repositorioUsuarioAdm = repositorioUsuarioAdm;
//        this.repositorioCarro = repositorioCarro;
//
//        setTitle("Login");
//        setContentPane(mainPanel);
//
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setResizable(false);
//        pack();
//        setLocationRelativeTo(null);
//
//        setupCampos();
//        setupEvents();
//
//        try {
//            setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/iconVend.png")));
//        } catch (Exception e) {
//            System.out.println("Ícone não encontrado");
//        }
//    }
//
//
//
//
//
//
//    private void setupCampos() {
//        setupCampo(emailField, "Email");
//        setupCampo(senhaField, "Senha");
//    }
//
//    private void setupCampo(JTextField textField, String texto) {
//        textField.setForeground(Color.BLACK);
//
//        textField.addFocusListener(new FocusAdapter() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (textField.getText().equals(texto)) {
//                    textField.setText("");
//                    textField.setForeground(Color.BLACK);
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (textField.getText().isEmpty()) {
//                    textField.setForeground(Color.GRAY);
//                    textField.setText(texto);
//                }
//            }
//        });
//    }
//}

package com.example.VEND.view;

import com.example.VEND.VendApplication;
import com.example.VEND.util.CriptografiaUtil;
import com.example.VEND.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

@Component
public class TelaLogin extends JFrame {

    @Autowired
    private UsuarioService usuarioService;

    private JPanel mainPanel;
    private JLabel userIconLabel;
    private JLabel adminLabel;
    private JTextField emailField;
    private JButton loginButton;
    private JPasswordField senhaField;
    private JLabel cadastrarLabel;

    public TelaLogin() {
        setupJanela();
        setupCampos();
        setupEventos();
    }

    private void setupEventos() {
        loginButton.addActionListener(e -> validarLogin());

        cadastrarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirTelaCadastro();
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

    private void validarLogin() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());


            if (usuarioService.validarLogin(email, CriptografiaUtil.gerarHash(senha))){
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                abrirTelaPrincipal();
            }else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }



    }

    private void setupJanela(){
        setTitle("Login");
        setContentPane(mainPanel);

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

    private void abrirTelaPrincipal() {
        this.dispose();
        TelaPrincipal telaPrincipal = VendApplication.getTela(TelaPrincipal.class);
        telaPrincipal.setVisible(true);
    }

    private void abrirTelaCadastro() {
        this.dispose();
        TelaCadastro telaCadastro = VendApplication.getTela(TelaCadastro.class);
        telaCadastro.setVisible(true);
    }

    private void limpaCampos() {
        emailField.setText("");
        senhaField.setText("");
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
