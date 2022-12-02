package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.AlunoTrilhaPersonalizadoDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeNotaDTO;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByEmail(String email);

    List<UsuarioEntity> findByNomeIgnoreCase(String nome);

    Page<UsuarioEntity> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

    Page<UsuarioEntity> findAllByTipoPerfil(Integer tipoPerfil, Pageable pageable);

    Page<UsuarioEntity> findAllByTipoPerfilAndNomeContainingIgnoreCase(Integer tipoPerfil, String nome, Pageable pageable);

    List<UsuarioEntity> findTop5ByOrderByPontuacaoAlunoDesc();

    UsuarioEntity findByLoginIgnoreCase(String login);

    @Query("  select distinct new br.com.vemrankser.ranqueamento.dto.AlunoTrilhaPersonalizadoDTO ( " +
            " tu.idUsuario," +
            " tu.nome," +
            " tu.email," +
            " tu.login," +
            " tu.statusUsuario," +
            " tu.tipoPerfil," +
            " t.nome," +
            " t.edicao," +
            " t.anoEdicao," +
            " t.idTrilha" +
            " ) " +
            " from  TRILHA t" +
            " left join t.usuarios tu " +
            " where (:nome is null or tu.nome = :nome and t.idTrilha = :idTrilha) " )
    Page<AlunoTrilhaPersonalizadoDTO> listAlunoTrilhaQuery(Pageable pageable, String nome, Integer idTrilha);




}
