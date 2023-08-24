package uniamerica.com.inversion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Meta;
import uniamerica.com.inversion.entity.Usuario;

import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<Meta,Long> {

    @Modifying
    @Query("UPDATE Meta meta " +
            "SET meta.ativo = false " +
            "WHERE meta.id = :meta")
    public void desativar(@Param("meta") Long idMeta);

    @Query("SELECT m FROM Meta m WHERE m.descricaoMeta = :descricaoMeta AND m.ativo = true AND m.usuario.id = :usuario")
    Meta findByDescricaoMeta(String descricaoMeta, Long usuario);

    Page<Meta> findByUsuario(Usuario usuario, Pageable pageable);

    Optional<Meta> findByIdAndUsuario(Long Id, Usuario usuario);
}