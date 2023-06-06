package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.com.inversion.entity.Rendimento;

public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {
    @Modifying
    @Query("UPDATE Rendimento rendimento" +
            " SET rendimento.ativo = false " +
            "WHERE rendimento.id = :rendimento")
    public void desativar(@Param("rendimento") Long idRendimento);
}
