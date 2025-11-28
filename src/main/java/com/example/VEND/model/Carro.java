package com.example.VEND.model;

import jakarta.persistence.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.io.File;
import java.time.LocalDate;


@Entity
@Table(name = "carros")
public class Carro{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Carroceria carroceria;

    @Basic (fetch = FetchType.LAZY)
    @Column(name = "imagem", columnDefinition = "BYTEA")
    private byte[] imagem;

    private String modelo;
    private Double preco;
    private String marca;
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "interesse_usuario_id")
    private UsuarioCliente usuarioCliente;

    public Carro() {
    }

    public Carro(String carroceria, byte[] imagem, String modelo, Integer ano, Double preco, String marca) {
        this.carroceria = Carroceria.fromString(carroceria);
        this.imagem = imagem;
        this.modelo = modelo;
        this.ano = ano;
        this.preco = preco;
        this.marca = marca;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public Long getId() {
        return id;
    }

    public Carroceria getCarroceria() {
        return carroceria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo.toLowerCase();
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca.toLowerCase();
    }

    public void setCarroceria(Carroceria carroceria) {
        this.carroceria = carroceria;
    }

    public UsuarioCliente getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(UsuarioCliente usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }
}
