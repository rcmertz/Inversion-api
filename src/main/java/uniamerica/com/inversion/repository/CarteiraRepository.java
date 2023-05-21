package com.uniamerica.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uniamerica.inversion.entity.Carteira;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    @Modifying
    @Query("UPDATE Carteira carteira " +
            "SET carteira.ativo = false " +
            "WHERE carteira.id = :carteira")
    public void desativar(@Param("carteira") Long idCarteira);
}
