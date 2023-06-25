package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Carteira;
import uniamerica.com.inversion.entity.Usuario;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    @Modifying
    @Query("UPDATE Carteira carteira " +
            "SET carteira.ativo = false " +
            "WHERE carteira.id = :carteira")
    public void desativar(@Param("carteira") Long idCarteira);

    Page<Carteira> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Carteira> findByIdAndUsuario(Long Id, Usuario usuario);

}
