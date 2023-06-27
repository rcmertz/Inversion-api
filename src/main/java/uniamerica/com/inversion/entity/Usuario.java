package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uniamerica.com.inversion.config.JsonDeserializers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "usuario", schema = "public") //Seguir este padrão para tabelas e campos
public class Usuario extends AbstractEntity implements UserDetails {


    @Getter @Setter
    @Column(name = "nome", length = 50)
    private String nome;

    @Getter @Setter
    @CPF(message = "CPF NAO ENCONTRADO")
    @Column(name = "cpf", unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", length = 18)
    private String telefone;

    @Getter @Setter
    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Getter @Setter
    @Column(name = "senha", length = 256)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonDeserialize(using = JsonDeserializers.PasswordDeserializer.class)
    private String senha;

    public static Usuario build(final Usuario usuario) {
        Usuario currentUser = new Usuario();
        currentUser.setNome(usuario.getNome());
        currentUser.setAtivo(usuario.isAtivo());
        currentUser.setEmail(usuario.getEmail());
        currentUser.setSenha(usuario.getSenha());
        currentUser.setId(usuario.getId());

        return currentUser;
    }

    public Usuario(String nome, String cpf, String telefone, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return getSenha();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
