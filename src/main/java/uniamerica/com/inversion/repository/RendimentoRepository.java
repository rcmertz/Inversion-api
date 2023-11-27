package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Rendimento;
import uniamerica.com.inversion.entity.Usuario;

import java.util.Optional;

public interface RendimentoRepository extends JpaRepository<Rendimento, Long> {
    @Modifying
    @Query("UPDATE Rendimento dividendo" +
            " SET dividendo.ativo = false " +
            "WHERE dividendo.id = :dividendo")
    public void desativar(@Param("dividendo") Long idDividendo);

    Page<Rendimento> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Rendimento> findByIdAndUsuarioAndAtivoIsTrue(Long Id, Usuario usuario);
}
