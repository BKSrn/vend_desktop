package com.example.VEND.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios_cliente")
public class UsuarioCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany (mappedBy = "usuarioCliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Carro> listaInteressesCar;


    private String email;
    private String senha;

    public UsuarioCliente() {
    }

    public UsuarioCliente(String email, String senha) {
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

    public List<Carro> getListaInteressesCar() {
        return listaInteressesCar;
    }

    public void setListaInteressesCar(List<Carro> listaInteressesCar) {
        this.listaInteressesCar = listaInteressesCar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
