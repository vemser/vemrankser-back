package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {
}
