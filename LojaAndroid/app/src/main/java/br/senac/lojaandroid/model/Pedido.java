package br.senac.lojaandroid.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;
import java.util.List;

@Entity(tableName = "pedido")
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    private int nunPedido;
    private int idCliente;
    private int nunCartao;
    private String bandCartao;
    private String date;
    private String endereco;

    //@Embedded
    //private List<ItensPedido> listaItensPedido;

    public Pedido () {

    }

    public int getNunPedido() {
        return nunPedido;
    }

    public void setNunPedido(int nunPedido) {
        this.nunPedido = nunPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getNunCartao() {
        return nunCartao;
    }

    public void setNunCartao(int nunCartao) {
        this.nunCartao = nunCartao;
    }

    public String getBandCartao() {
        return bandCartao;
    }

    public void setBandCartao(String bandCartao) {
        this.bandCartao = bandCartao;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
//
//    public List<ItensPedido> getListaItensPedido() {
//        return listaItensPedido;
//    }
//
//    public void setListaItensPedido(List<ItensPedido> listaItensPedido) {
//        this.listaItensPedido = listaItensPedido;
//    }
}
