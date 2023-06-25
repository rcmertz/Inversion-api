package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "carteira", schema = "public")
public class Carteira extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "idUsuario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @Column(name = "descricao", length = 100)
    private String descricaoCarteira;

    @Getter @Setter
    @Column(name = "valor")
    private Double valorCarteira;

    public Carteira(String descricaoCarteira, Double valorCarteira) {
        this.descricaoCarteira = descricaoCarteira;
        this.valorCarteira = valorCarteira;
    }
}
