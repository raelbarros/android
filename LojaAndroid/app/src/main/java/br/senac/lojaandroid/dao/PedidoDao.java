package br.senac.lojaandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.senac.lojaandroid.model.Pedido;

@Dao
public interface PedidoDao {

    @Query("SELECT * FROM pedido")
    List<Pedido> getAllPedido();

    @Insert
    void insertPedido(Pedido pedido);

    @Update
    void updatePedido(Pedido pedido);

    @Delete
    void deletePedido(Pedido pedido);
}
