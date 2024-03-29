package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "idUsuario")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @Getter @Setter
    @JoinColumn(name = "idCarteira")
    @ManyToOne(fetch = FetchType.EAGER)
    private Carteira carteira;

    @Getter @Setter
    @Column(name = "nome")
    private String nomeInvestimento;

    @Getter @Setter
    @Column(name = "valor", nullable = false)
    private Double valorInvestimento;

    @Getter @Setter
    @Column(name = "data")
    private LocalDate data;

    @Getter @Setter
    @Column(name = "saldo", nullable = false)
    private Integer saldo;

    public Investimento(Usuario usuario, Carteira carteira, String nomeInvestimento, Double valorInvestimento, LocalDate data) {
        this.usuario = usuario;
        this.carteira = carteira;
        this.nomeInvestimento = nomeInvestimento;
        this.valorInvestimento = valorInvestimento;
        this.data = data;
    }

    @PrePersist
    public void prePersist() {
        this.saldo = 0;
        this.valorInvestimento = 0.00;
    }
}
