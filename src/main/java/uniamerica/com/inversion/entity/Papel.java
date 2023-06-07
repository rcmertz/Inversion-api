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
@Table(name = "PAPEL", schema = "public")
public class Papel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "PAP_VALOR")
    private BigDecimal valor;

    @Getter @Setter
    @Column(name = "PAP_QNT")
    private Integer quantidade;

    @Getter @Setter
    @JoinColumn(name= "TIP_PAPEL")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPapel tipo;


}
