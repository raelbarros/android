package br.senac.lojaandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.senac.lojaandroid.model.Cliente;

@Dao
public interface ClienteDao {

    @Query("SELECT * FROM cliente")
    List<Cliente> getAllCliente();

    @Query("SELECT * FROM cliente WHERE idCliente= :idCliente")
    Cliente clienteById(int idCliente);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCliente(Cliente cliente);

    @Update
    void updateCliente(Cliente cliente);

    @Delete
    void deleteCliente(Cliente cliente);
}
