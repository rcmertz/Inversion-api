package uniamerica.com.inversion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "investimento", schema = "public")
public class Investimento extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "idCarteira")
    @ManyToOne(fetch = FetchType.EAGER)
    private Carteira carteira;

    @Getter @Setter
    @Column(name = "nome")
    private String nomeInvestimento;

    @Getter @Setter
    @Column(name = "valor")
    private Double valorInvestimento;

    public Investimento(Carteira carteira, String nomeInvestimento, Double valorInvestimento) {
        this.carteira = carteira;
        this.nomeInvestimento = nomeInvestimento;
        this.valorInvestimento = valorInvestimento;
    }
}
