package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {
    List<ComentarioEntity> findAllByIdAtividade(Integer idAtividade);

    List<ComentarioEntity> findAllByStatusComentario(Integer statusComentario);

}
