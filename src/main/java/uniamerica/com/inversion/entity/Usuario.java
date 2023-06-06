package uniamerica.com.inversion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "usuario", schema = "public") //Seguir este padrão para tabelas e campos
public class Usuario extends AbstractEntity{


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
    @Column(name = "email", length = 50)
    private String email;

    @Getter @Setter
    @Column(name = "senha", length = 50)
    private String senha;

    public Usuario(String nome, String cpf, String telefone, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }
}
