package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity,Integer> {

    List<AtividadeEntity> findByStatusAtividade(Integer statusAtividade);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO ( " +
            " u.nome, " +
            " a.dataEntrega " +
            " ) " +
            " from USUARIO u, ATIVIDADE a " +
            " left join u.trilhas ut " +
            " left join ut.modulos tm " +
            " left join tm.atividades" +
            " where (u.idUsuario = a.idAtividade) " +
            " order by a.dataEntrega DESC ")
    Page<AtividadeMuralDTO> listarAtividadeMural(Pageable pageable);
}
