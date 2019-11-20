package br.senac.lojaandroid.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.senac.lojaandroid.dao.ClienteDao;
import br.senac.lojaandroid.dao.ItensPedidoDao;
import br.senac.lojaandroid.dao.PedidoDao;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.model.ItensPedido;
import br.senac.lojaandroid.model.Pedido;

@Database(entities = {Pedido.class, ItensPedido.class, Cliente.class}, exportSchema = false, version = 1)
public abstract class LojaDatabase extends RoomDatabase {
    private static final String DB_NAME = "loja_db";
    private static LojaDatabase INSTANCE;

    public static synchronized LojaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LojaDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
    public abstract PedidoDao pedidoDao();
    public abstract ItensPedidoDao itensPedido();
    public abstract ClienteDao clienteDao();

}
