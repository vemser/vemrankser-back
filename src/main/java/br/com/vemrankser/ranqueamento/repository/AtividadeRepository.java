package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeEntity,Integer> {
}
