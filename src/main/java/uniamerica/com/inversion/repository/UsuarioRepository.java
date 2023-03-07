package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    @Modifying
    @Query("UPDATE Usuario usuario " +
            "SET usuario.ativo = false " +
            "WHERE usuario.id = :usuario");
}
