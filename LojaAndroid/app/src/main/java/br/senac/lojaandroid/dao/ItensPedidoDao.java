package br.senac.lojaandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.senac.lojaandroid.model.ItensPedido;
import br.senac.lojaandroid.model.Pedido;

@Dao
public interface ItensPedidoDao {

    @Query("SELECT * FROM itensPedido")
    List<ItensPedido> getAllItens();

    @Insert
    void insertItem(ItensPedido item);

    @Update
    void updateItem(ItensPedido item);

    @Delete
    void deleteItem(ItensPedido item);
}
