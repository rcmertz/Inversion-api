package uniamerica.com.inversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniamerica.com.inversion.entity.Usuario;
import uniamerica.com.inversion.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class AuthenticationService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Usuario usuario = this.usuarioRepository
                .findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("User " + username + " couldn't be located.");
        }

        return Usuario.build(usuario);
    }
}
