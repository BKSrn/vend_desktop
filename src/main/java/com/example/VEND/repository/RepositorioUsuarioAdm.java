package com.example.VEND.repository;

import com.example.VEND.model.UsuarioAdm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuarioAdm extends JpaRepository<UsuarioAdm, Long> {
    //procura email e senha
    Optional<UsuarioAdm> findByEmailAndSenha (String email, String senha);
}
