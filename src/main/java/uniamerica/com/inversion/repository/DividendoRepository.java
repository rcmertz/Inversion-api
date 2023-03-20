package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.com.inversion.entity.Dividendo;

public interface DividendoRepository extends JpaRepository<Dividendo, Long> {
    @Modifying
    @Query("UPDATE Dividendo dividendo" +
            " SET dividendo.ativo = false " +
            "WHERE dividendo.id = :dividendo")
    public void desativar(@Param("dividendo") Long idDividendo);
}
