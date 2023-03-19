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
@Table(name = "USUARIOS", schema = "public") //Seguir este padrão para tabelas e campos
public class Usuario extends AbstractEntity{

    @Getter @Setter
    @Column(name = "USU_NOME", length = 50)
    private String nome;

    @Getter @Setter
    @CPF
    @Column(name = "USU_CPF", unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @Column(name = "USU_TELEFONE", length = 18)
    private String telefone;

    @Getter @Setter
    @Column(name = "USU_EMAIL", length = 50)
    private String email;

    @Getter @Setter
    @Column(name = "USU_SENHA", length = 50)
    private String senha;

    public Usuario(String USU_NOME, String USU_CPF, String USU_TELEFONE, String USU_EMAIL, String USU_SENHA) {
        this.nome = USU_NOME;
        this.cpf = USU_CPF;
        this.telefone = USU_TELEFONE;
        this.email = USU_EMAIL;
        this.senha = USU_SENHA;
    }
}
