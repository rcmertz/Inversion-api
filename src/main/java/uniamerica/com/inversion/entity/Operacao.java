package com.uniamerica.inversion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "OPERACOES", schema = "public")
public class Operacao  extends AbstractEntity{

    @Getter @Setter
    @Column(name = "OPE_VALOR")
    private String valor;

    @Getter @Setter
    @Column(name = "OPE_DATA")
    private LocalDate data;

    @Getter @Setter
    @Column(name = "OPE_TIPO_OPERCAO")
    private TipoOperacao tipoOperacao;
}
