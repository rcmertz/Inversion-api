package com.uniamerica.inversion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "TIPO_PAPEL", schema = "public")
public class TipoPapel extends AbstractEntity{

    @Getter @Setter
    @Column(name = "TIP_TIPO")
    private String tipo;

    public TipoPapel(String tipo) {
        this.tipo = tipo;
    }
}