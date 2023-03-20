package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel,Long> {

    @Modifying
    @Query("UPDATE Papel papel " +
            "SET papel.ativo = false " +
            "WHERE papel.id = :produto")
    public void desativar(@Param("papel") Long idPapel);

}