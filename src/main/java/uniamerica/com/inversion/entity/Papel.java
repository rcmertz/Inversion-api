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
@Table(name = "papel", schema = "public")
public class Papel extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "carteira")
    @ManyToOne(fetch = FetchType.EAGER)
    private Carteira carteira;

    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    @Getter @Setter
    @Column(name = "quantidade")
    private Integer quantidade;

    @Getter @Setter
    @Column(name= "tipo")
    private String tipo;

    @Getter @Setter
    @Column(name = "operacao")
    private String operacao;

    public Papel(BigDecimal valor, Integer quantidade, String tipo, String operacao, Carteira carteira) {
        this.valor = valor;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.operacao = operacao;
        this.carteira = carteira;
    }
}
