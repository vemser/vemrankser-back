package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity, Integer> {

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeTrilhaDTO ( " +
            " a.idAtividade, " +
            " a.nomeInstrutor, " +
            " a.titulo, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " atr.nome, " +
            " atr.edicao, " +
            " atr.anoEdicao " +
            " ) " +
            " from ATIVIDADE a " +
            " inner join a.trilhas atr " +
            " on (atr.idTrilha = :idTrilha or :idTrilha is null) " +
            " where (a.statusAtividade = :atividadeStatus or :atividadeStatus is null ) " +
            " ")
    Page<AtividadeTrilhaDTO> listarAtividadePorStatus(Pageable pageable, Integer idTrilha, AtividadeStatus atividadeStatus);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AtividadeMuralDTO ( " +
            " a.idAtividade, " +
            " a.nomeInstrutor, " +
            " a.titulo, " +
            " a.instrucoes, " +
            " a.pesoAtividade, " +
            " a.dataCriacao, " +
            " a.dataEntrega, " +
            " a.idModulo, " +
            " a.statusAtividade, " +
            " a.modulo.nome, " +
            " atr.nome, " +
            " atr.edicao " +
            " ) " +
            " from ATIVIDADE a " +
            " inner join a.trilhas atr " +
            " where (atr.idTrilha = :idTrilha or :idTrilha is null ) " +
            " ")
    Page<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Pageable pageable, Integer idTrilha);


    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeMuralAlunoDTO ( " +
            " au.atividade.idAtividade, " +
            " au.usuarioEntity.idUsuario, " +
            " au.atividade.titulo, " +
            " au.atividade.instrucoes, " +
            " au.atividade.pesoAtividade, " +
            " au.atividade.dataCriacao, " +
            " au.atividade.dataEntrega, " +
            " au.atividade.idModulo, " +
            " au.atividade.statusAtividade, " +
            " au.atividade.modulo.nome, " +
            " atr.nome, " +
            " atr.edicao " +
            " ) " +
            " from ATIVIDADE_USUARIO au " +
            " left join au.atividade aua " +
            " left join aua.trilhas atr" +
            " where (au.usuarioEntity.idUsuario = :idUsuario and au.atividade.statusAtividade = :atividadeStatus) " +
            "  ")
    Page<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer idUsuario, AtividadeStatus atividadeStatus,Pageable pageable);


    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AtividadeNotaPageDTO ( " +
            " u.idUsuario, " +
            " u.nome, " +
            " au.idAtividade, " +
            " au.pontuacao, " +
            " aul.link " +
            " ) " +
            " from USUARIO u " +
            " left join u.trilhas ut " +
            " left join ut.modulos utm" +
            " left join u.atividades au" +
            " left join au.links aul " +
            " on ( au.idModulo = :idModulo and ut.idTrilha = :idTrilha and au.statusAtividade = :atividadeStatus ) " +
            "  ")
    Page<AtividadeNotaPageDTO> listarAtividadePorIdTrilhaIdModulo(Pageable pageable, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus);

}
