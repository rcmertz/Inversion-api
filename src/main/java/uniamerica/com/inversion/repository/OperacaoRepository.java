package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Operacao;
import uniamerica.com.inversion.entity.Usuario;

import java.util.Optional;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao,Long> {

    @Modifying
    @Query("UPDATE Operacao operacao " +
            "SET operacao.ativo = false " +
            "WHERE operacao.id = :operacao")
    public void desativar(@Param("operacao") Long idOperacao);

    Page<Operacao> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Operacao> findByIdAndUsuario(Long Id, Usuario usuario);

}