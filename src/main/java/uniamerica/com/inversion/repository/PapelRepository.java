package com.uniamerica.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uniamerica.inversion.entity.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel,Long> {

    @Modifying
    @Query("UPDATE Papel papel " +
            "SET papel.ativo = false " +
            "WHERE papel.id = :papel")
    public void desativar(@Param("papel") Long idPapel);

}