package uniamerica.com.inversion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "Tipo_Papel", schema = "public") //Seguir este padrão para tabelas e campos
public class TipoPapel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "TIP_TIPO")
    private String tipo;

    public TipoPapel(String TIP_TIPO) {
        this.tipo = TIP_TIPO;

    }
}