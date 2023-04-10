package uniamerica.com.inversion.entity;

public enum TipoOperacao {

    compra("Compra"),
        venda("Venda");

    public final String valor;

    private TipoOperacao(String valor){
        this.valor = valor;
    }
}
