package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrilhaRepository extends JpaRepository<TrilhaEntity, Integer> {
}
