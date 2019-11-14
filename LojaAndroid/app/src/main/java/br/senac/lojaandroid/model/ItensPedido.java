package br.senac.lojaandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "itensPedido")
public class ItensPedido {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idProduto;

    public ItensPedido () {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }
}
