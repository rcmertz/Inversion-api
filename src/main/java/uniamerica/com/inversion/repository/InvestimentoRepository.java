package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Investimento;
import uniamerica.com.inversion.entity.Usuario;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento,Long> {

    @Modifying
    @Query("UPDATE Investimento investimento " +
            "SET investimento.ativo = false " +
            "WHERE investimento.id = :investimento")
    public void desativar(@Param("investimento") Long idInvestimento);

    @Query("SELECT i FROM Investimento i WHERE i.nomeInvestimento = :nomeInvestimento AND i.ativo = true")
    Investimento findByNomeInvestimento(String nomeInvestimento);
}