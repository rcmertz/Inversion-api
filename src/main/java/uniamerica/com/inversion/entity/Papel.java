package uniamerica.com.inversion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "Papel", schema = "public") //Seguir este padrão para tabelas e campos
public class Papel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "PAP_VALOR")
    private String valor;

    @Getter @Setter
    @Column(name = "PAP_QNT")
    private Double quantidade;

    @Getter @Setter
    @JoinColumn(name= "idTipo_Papel")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipoPapel tipo;

}
