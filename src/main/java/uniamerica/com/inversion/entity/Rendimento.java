package uniamerica.com.inversion.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "rendimento", schema = "public")
public class Rendimento extends AbstractEntity {

    @Getter @Setter
    @JoinColumn(name= "idOperacao")
    @ManyToOne(fetch = FetchType.EAGER)
    private Operacao operacao;

    @Getter @Setter
    @Column(name = "preco_un")
    private Double preco_un;

    @Getter @Setter
    @Column(name = "quantidade")
    private Integer quantidade;

    @Getter @Setter
    @Column(name = "data")
    private LocalDateTime data;

    public Rendimento(Operacao operacao, Double preco_un, Integer quantidade, LocalDateTime data) {
        this.operacao = operacao;
        this.preco_un = preco_un;
        this.quantidade = quantidade;
        this.data = data;
    }
}
