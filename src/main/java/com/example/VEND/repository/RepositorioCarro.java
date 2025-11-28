package com.example.VEND.repository;

import com.example.VEND.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioCarro extends JpaRepository<Carro, Long> {

    List<Carro> findByMarcaAndModeloContainsOrderByPrecoAsc(String marca, String modelo);

    List<Carro> findAllByOrderByPrecoAsc();

    List<Carro> findByMarcaContainsOrderByPrecoAsc(String marca);

    List<Carro> findByModeloContainsOrderByPrecoAsc(String modelo);

    List<Carro> findByMarca(String marca);

    List<Carro> findByAnoGreaterThanEqual(Integer ano);

}
