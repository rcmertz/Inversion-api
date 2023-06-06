package uniamerica.com.inversion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "papel", schema = "public")
public class Papel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "valor")
    private BigDecimal valor;

    @Getter @Setter
    @Column(name = "quantidade")
    private Integer quantidade;

    @Getter @Setter
    @JoinColumn(name= "tipo")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPapel tipo;


}
