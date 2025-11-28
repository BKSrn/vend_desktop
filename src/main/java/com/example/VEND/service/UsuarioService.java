package com.example.VEND.service;

import com.example.VEND.model.UsuarioAdm;
import com.example.VEND.repository.RepositorioUsuarioAdm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private RepositorioUsuarioAdm repositorioUsuarioAdm;

    @Transactional(readOnly = true)
    public boolean validarCadastro(String email, String senha) {
        Optional<UsuarioAdm> usuarioAdm = repositorioUsuarioAdm.findByEmailAndSenha(email, senha);

        if (usuarioAdm.isPresent()){
            JOptionPane.showMessageDialog(null,
                    "E-Mail ou senha ja cadastrado",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        } else if (email.isEmpty() || senha.isEmpty() || email.equals("Email") || senha.equals("Senha")) {

            JOptionPane.showMessageDialog(null,
                    "Por favor, preencha todos os campos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        } else if (!isEmailValido(email)) {
            JOptionPane.showMessageDialog(null,
                    "Por favor, insira um e-mail válido no formato correto.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }else {
            return true;
        }

    }

    private boolean isEmailValido(String email) {
        //validar e-mail
        boolean matches = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return matches;
    }

    @Transactional
    public void cadastrarUsuario(UsuarioAdm usuarioAdm) {
        repositorioUsuarioAdm.save(usuarioAdm);
    }

    @Transactional
    public void atualizarUsuario(UsuarioAdm usuarioAdm) {
        repositorioUsuarioAdm.save(usuarioAdm);
    }

    @Transactional
    public void deletarUsuario(Long id) {
        repositorioUsuarioAdm.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioAdm> listarTodos() {
        return repositorioUsuarioAdm.findAll();
    }

    public boolean validarLogin(String email, String senhaHash) {
        Optional<UsuarioAdm> usuarioEncontrado = repositorioUsuarioAdm.findByEmailAndSenha(email, senhaHash);

        if (usuarioEncontrado.isPresent()) {
            return true;
        }
        else {
            return false;
        }

    }
}
