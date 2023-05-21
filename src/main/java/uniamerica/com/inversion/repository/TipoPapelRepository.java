package com.uniamerica.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.uniamerica.inversion.entity.TipoPapel;

@Repository
    public interface TipoPapelRepository extends JpaRepository<TipoPapel, Long> {
        @Modifying
        @Query("UPDATE TipoPapel tipoPapel" +
                " SET tipoPapel.ativo = false " +
                "WHERE tipoPapel.id = :tipoPapel")
        public void desativar(@Param("tipoPapel") Long idTipo_Papel);

    }
