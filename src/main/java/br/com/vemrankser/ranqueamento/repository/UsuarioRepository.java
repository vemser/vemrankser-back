package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.dto.AlunoTrilhaPersonalizadoDTO;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
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

    Page<UsuarioEntity> findAllByTipoPerfil(Integer tipoPerfil, Pageable pageable);

    Page<UsuarioEntity> findAllByTipoPerfilAndNomeContainingIgnoreCase(Integer tipoPerfil, String nome, Pageable pageable);


    UsuarioEntity findByLoginIgnoreCase(String login);

    @Query("  select new br.com.vemrankser.ranqueamento.dto.AlunoTrilhaPersonalizadoDTO ( " +
            " tu.idUsuario," +
            " tu.foto, " +
            " tu.nome," +
            " tu.email," +
            " tu.login," +
            " tu.statusUsuario," +
            " tu.tipoPerfil," +
            " tut.nome," +
            " tut.edicao," +
            " tut.anoEdicao," +
            " tut.idTrilha" +
            " ) " +
            " from USUARIO tu " +
            " inner join tu.trilhas tut " +
            " on (tut.idTrilha = :idTrilha or :idTrilha is null)" +
            " where (tu.nome like :nome or :nome is null) ")
    Page<AlunoTrilhaPersonalizadoDTO> listAlunoTrilhaQuery(Pageable pageable, String nome, Integer idTrilha);


}
