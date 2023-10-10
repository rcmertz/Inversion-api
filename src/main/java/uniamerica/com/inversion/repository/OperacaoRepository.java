package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao,Long> {

    @Modifying
    @Query("UPDATE Operacao operacao " +
            "SET operacao.ativo = false " +
            "WHERE operacao.id = :operacao")
    public void desativar(@Param("operacao") Long idOperacao);

    //** PARA TRAZER TODAS OPERACOES POR CARTEIRA, USADO PARA PAGINAR RANGE DE DATA  **//
    Page<Operacao> findByInvestimento_CarteiraIdAndUsuarioAndDataBetween(Long carteira, Usuario usuario, LocalDateTime dataStart, LocalDateTime dataEnd, Pageable pageable);

    //** PARA TRAZER TODAS OPERACOES POR CARTEIRA, SEM RANGE DE DATA  **//
    Page<Operacao> findByInvestimento_CarteiraIdAndUsuario(Long carteira, Usuario usuario, Pageable pageable);

    Page<Operacao> findByUsuario(Usuario usuario, Pageable pageable);

    //** FILTRAR POR INVESTIMENTO E DATA  **//
    Page<Operacao> findByUsuarioAndInvestimentoAndDataBetween(Usuario usuario, Investimento investimento, LocalDateTime dataStart, LocalDateTime dataEnd, Pageable pageable);

    Page<Operacao> findByUsuarioAndInvestimento(Usuario usuario, Investimento investimento, Pageable pageable);
    Optional<Operacao> findByIdAndUsuario(Long Id, Usuario usuario);

    @Query("SELECT o FROM Operacao o WHERE o.investimento.id = :idInvestimento AND o.ativo = true AND o.usuario = :usuario")
    List<Operacao> findValorByTipoCompraAndUsuario(Usuario usuario, Long idInvestimento);

    @Query("SELECT COALESCE(SUM(CASE WHEN o.tipo = 'compra' THEN o.quantidade ELSE -o.quantidade END), 0) AS saldo " +
            "FROM Operacao o " +
            "WHERE o.investimento.id = :investimentoId AND o.ativo = true AND o.usuario = :usuario")
    BigDecimal saldo(@Param("investimentoId") Long investimentoId, @Param("usuario") Usuario usuario);

}
