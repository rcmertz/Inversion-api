package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Getter @Setter
    @Column(name = "data")
    private LocalDate data;

    public Investimento(Carteira carteira, String nomeInvestimento, Double valorInvestimento, LocalDate data) {
        this.carteira = carteira;
        this.nomeInvestimento = nomeInvestimento;
        this.valorInvestimento = valorInvestimento;
        this.data = data;
    }
}
