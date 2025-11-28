package com.example.VEND.service;

import com.example.VEND.model.Carro;
import com.example.VEND.repository.RepositorioCarro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private RepositorioCarro repositorioCarro;

    private int anoAtual = LocalDate.now().getYear();

    @Transactional(readOnly = true)
    public Optional<Carro> buscarPorId(Long id) {
        Optional<Carro> carro = repositorioCarro.findById(id);

        if (carro.isEmpty()) {
            throw new RuntimeException("Carro não encontrado");
        }

        return carro;
    }

    @Transactional
    public void cadastrarCarro(Carro carro) {
        repositorioCarro.save(carro);
    }

    @Transactional
    public void deletarCarro(Long id) {
        if (!repositorioCarro.existsById(id)) {
            throw new RuntimeException("Carro não encontrado para exclusão");
        }

        repositorioCarro.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Carro> listarTodos() {
        return repositorioCarro.findAll();
    }

    @Transactional(readOnly = true)
    public List<Carro> buscarPorMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca é obrigatória para busca");
        }

        return repositorioCarro.findByMarca(marca);
    }

    public List<Carro> buscarPorMarcaModelo(String marca, String modelo) {
        return repositorioCarro.findByMarcaAndModeloContainsOrderByPrecoAsc(marca, modelo);
    }

}
