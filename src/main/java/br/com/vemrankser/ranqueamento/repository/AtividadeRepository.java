package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeNotaDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity,Integer> {

    List<AtividadeEntity> findByStatusAtividade(Integer statusAtividade);

    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO ( " +
            " a.idAtividade, " +
            " a.titulo, " +
            " a.instrucoes, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " a.idModulo, " +
            " a.statusAtividade, " +
            " a.modulo.nome, " +
            " t.nome, " +
            " t.edicao " +
            " ) " +
            " from USUARIO u, ATIVIDADE a, TRILHA t " +
            " join u.trilhas  " +
//            " inner join t.modulos  " +
//            " inner join t.atividades" +
            " where ( :idTrilha in t.idTrilha ) " +
//            " group by t.idTrilha " +
            " ")
    Page<AtividadeMuralDTO> listarAtividadeMural(Pageable pageable, Integer idTrilha);


    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO ( " +
            " a.idAtividade, " +
            " a.titulo, " +
            " a.instrucoes, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " a.idModulo, " +
            " a.statusAtividade, " +
            " a.modulo.nome, " +
            " t.nome, " +
            " t.edicao " +
            " ) " +
            " from USUARIO u, ATIVIDADE a, TRILHA t " +
            " left join a.alunos " +
            " left join u.atividades " +
            " where (:idUsuarioLogado is null or u.idUsuario = :idUsuarioLogado and a.statusAtividade = :atividadeStatus) " +
            " ")
    List<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer idUsuarioLogado, AtividadeStatus atividadeStatus);

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
