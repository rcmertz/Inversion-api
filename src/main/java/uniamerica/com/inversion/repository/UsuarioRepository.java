package uniamerica.com.inversion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniamerica.com.inversion.entity.Usuario;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Modifying
    @Query("UPDATE Usuario usuario " +
            "SET usuario.ativo = false " +
            "WHERE usuario.id = :usuario")
    public void desativar(@Param("usuario") Long idUsuario);

    @Query("SELECT u FROM Usuario u where u.email = :email")
    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf")
    Usuario findByCpf(String cpf);
}
