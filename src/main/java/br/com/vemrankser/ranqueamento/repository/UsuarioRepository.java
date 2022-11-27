package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findByNomeContainingIgnoreCase(String nome);

    Page<UsuarioEntity> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);
}
