package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {

    Optional<TrilhaEntity> findByNomeContainingIgnoreCase(String nome);

    Page<TrilhaEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Optional<TrilhaEntity> findByEdicao(Integer edicao);

    Optional<TrilhaEntity> findByNomeIgnoreCase(String nome);

    List<TrilhaEntity> findAllByNomeContainingIgnoreCase(String nome);

    List<TrilhaEntity> findAllByEdicao(Integer edicao);
}
