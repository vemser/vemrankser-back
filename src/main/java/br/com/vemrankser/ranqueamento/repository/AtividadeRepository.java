package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeNotaDTO;
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


    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO ( " +
            " a.titulo " +
            " ) " +
            " from USUARIO u, ATIVIDADE a " +
            " left join a.alunos " +
            " left join u.atividades" +
            " where (:idUsuarioLogado is null or u.idUsuario = :idUsuarioLogado) " +
            " ")
    List<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer idUsuarioLogado);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeNotaDTO ( " +
            " u.nome, " +
            " a.pontuacao " +
            " ) " +
            " from USUARIO u, ATIVIDADE a " +
            " left join u.trilhas ut " +
            " left join ut.modulos tm " +
            " left join tm.atividades" +
            " where (u.idUsuario = a.idAtividade) " +
            " order by u.nome ASC ")
    Page<AtividadeNotaDTO> listarAtividadePorNota(Pageable pageable);

}
