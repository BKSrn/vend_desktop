package com.example.VEND.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios_adm")
public class UsuarioAdm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;
    private String senha;

    public UsuarioAdm() {
    }

    public UsuarioAdm(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
