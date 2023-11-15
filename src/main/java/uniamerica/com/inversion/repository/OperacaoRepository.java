package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Investimento;
import uniamerica.com.inversion.entity.Operacao;
import uniamerica.com.inversion.entity.Usuario;

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

    @Query("SELECT o FROM Operacao o " +
            "WHERE o.investimento.id = :idInvestimento " +
            "AND o.ativo = true " +
            "AND o.usuario = :usuario " +
            "AND o.tipo = 'compra' " +
            "AND o.data >= (SELECT COALESCE(MAX(o2.data), '1900-01-01') FROM Operacao o2 " +
            "               WHERE o2.investimento.id = :idInvestimento " +
            "               AND o2.tipo = 'venda' " +
            "               AND o2.preco_medio = 0) " +
            "ORDER BY o.data DESC")
    List<Operacao> findValorByTipoCompraAndUsuario(@Param("usuario") Usuario usuario, @Param("idInvestimento") Long idInvestimento);


    @Query("SELECT COALESCE(SUM(CASE WHEN o.tipo = 'compra' THEN o.quantidade ELSE -o.quantidade END), 0) AS saldo " +
            "FROM Operacao o " +
            "WHERE o.investimento.id = :investimentoId AND o.ativo = true AND o.usuario = :usuario")
    int saldo(@Param("investimentoId") Long investimentoId, @Param("usuario") Usuario usuario);

    @Query("SELECT COALESCE(o.preco_medio, 0) " +
            "FROM Operacao o " +
            "WHERE o.investimento.id = :investimentoId " +
            "AND o.tipo = 'compra' " +
            "AND o.data = (SELECT MAX(o2.data) FROM Operacao o2 WHERE o2.investimento.id = :investimentoId AND o2.tipo = 'compra')")
    BigDecimal findUltimoPrecoMedioCompra(@Param("investimentoId") Long investimentoId);

}