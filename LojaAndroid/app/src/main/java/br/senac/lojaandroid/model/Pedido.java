package br.senac.lojaandroid.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity(tableName = "pedido")
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    private long nunPedido;
    private int idCliente;
    private String nunCartao;
    private int cardCodigo;
    private String cardVal;
    private String cep;
    private String endereco;
    private String bairro;
    private String date;


    //@Embedded
    //private List<ItensPedido> listaItensPedido;

    public Pedido () {

    }

    public long getNunPedido() {
        return nunPedido;
    }

    public void setNunPedido(long nunPedido) {
        this.nunPedido = nunPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNunCartao() {
        return nunCartao;
    }

    public void setNunCartao(String nunCartao) {
        this.nunCartao = nunCartao;
    }

    public int getCardCodigo() {
        return cardCodigo;
    }

    public void setCardCodigo(int cardCodigo) {
        this.cardCodigo = cardCodigo;
    }

    public String getCardVal() {
        return cardVal;
    }

    public void setCardVal(String cardVal) {
        this.cardVal = cardVal;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


//    public List<ItensPedido> getListaItensPedido() {
//        return listaItensPedido;
//    }
//
//    public void setListaItensPedido(List<ItensPedido> listaItensPedido) {
//        this.listaItensPedido = listaItensPedido;
//    }
}
