package uniamerica.com.inversion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "CARTEIRA", schema = "public")
public class Carteira extends AbstractEntity{

    @Getter @Setter
    @Column(name = "CAR_DESCRICAO", length = 100)
    private String descricao;

    @Getter @Setter
    @Column(name = "CAR_VALOR")
    private BigDecimal valor;

//    @Getter @Setter
//    @Column(name = "CAR_DATA_CRIACAO")
//    private LocalDate dataCriacao;

    @Getter @Setter
    @Column(name = "CAR_TIPO", length = 100)
    private String tipo;

}
