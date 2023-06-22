package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "operacao", schema = "public")
public class Operacao extends AbstractEntity{

    @Getter @Setter
    @JoinColumn(name = "idInvestimento")
    @ManyToOne(fetch = FetchType.EAGER)
    private Investimento investimento;

    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    @Getter @Setter
    @Column(name = "quantidade")
    private Integer quantidade;

    @Getter @Setter
    @Column(name = "data")
    private LocalDateTime data;

    @Getter @Setter
    @Enumerated(EnumType.STRING) // Indica que o campo será persistido como uma string
    @Column(name = "tipo")
    private TipoOperacao tipo;

    public Operacao(Investimento investimento, BigDecimal valor, Integer quantidade, LocalDateTime data, TipoOperacao tipo) {
        this.investimento = investimento;
        this.valor = valor;
        this.quantidade = quantidade;
        this.data = data;
        this.tipo = tipo;
    }
}
