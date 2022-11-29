package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByNomeIgnoreCase(String nome);

    Page<UsuarioEntity> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

    Page<UsuarioEntity> findAllByTipoPerfil(Integer tipoPerfil,Pageable pageable);


}
