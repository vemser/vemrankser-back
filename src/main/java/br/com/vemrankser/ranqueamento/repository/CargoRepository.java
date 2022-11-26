package br.com.vemrankser.ranqueamento.repository;

import br.com.vemrankser.ranqueamento.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity,Integer> {
}
