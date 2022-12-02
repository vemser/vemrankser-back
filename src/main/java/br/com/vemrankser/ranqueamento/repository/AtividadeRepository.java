package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeTrilhaDTO ( " +
            " atr.atividade.idAtividade, " +
            " atr.atividade.nomeInstrutor, " +
            " atr.atividade.titulo, " +
            " atr.atividade.pesoAtividade, " +
            " atr.atividade.dataCriacao, " +
            " atr.atividade.dataEntrega, " +
            " atr.trilha.nome, " +
            " atr.trilha.edicao, " +
            " atr.trilha.anoEdicao " +
            " ) " +
            " from ATIVIDADE_TRILHA atr" +
            " where (atr.trilha.idTrilha = :idTrilha and atr.trilha.idTrilha = :idTrilha and atr.atividade.statusAtividade = :atividadeStatus ) " +
            " ")
    Page<AtividadeTrilhaDTO> listarAtividadePorStatus(Pageable pageable, Integer idTrilha, AtividadeStatus atividadeStatus);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO ( " +
            " atr.atividade.idAtividade, " +
            " atr.atividade.titulo, " +
            " atr.atividade.instrucoes, " +
            " atr.atividade.pesoAtividade, " +
            " atr.atividade.dataCriacao, " +
            " atr.atividade.dataEntrega, " +
            " atr.atividade.idModulo, " +
            " atr.atividade.statusAtividade, " +
            " atr.atividade.modulo.nome, " +
            " atr.trilha.nome, " +
            " atr.trilha.edicao " +
            " ) " +
            " from ATIVIDADE_TRILHA atr " +
            " where (atr.trilha.idTrilha = :idTrilha ) " +
            " ")
    Page<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Pageable pageable, Integer idTrilha);


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

    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeNotaDTO ( " +
            " au.usuarioEntity.nome, " +
            " au.atividade.idAtividade," +
            " au.atividade.pontuacao " +
            " ) " +
            " from ATIVIDADE_USUARIO au" +
            " left join au.atividade aua" +
            " left join aua.trilhas atr  " +
            " where ( au.atividade.idModulo = :idModulo and atr.idTrilha = :idTrilha and au.atividade.statusAtividade = :atividadeStatus ) " +
            "  ")
    Page<AtividadeNotaDTO> listarAtividadePorIdTrilhaIdModulo(Pageable pageable, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus);

}
