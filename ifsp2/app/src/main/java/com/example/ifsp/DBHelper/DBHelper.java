package com.example.ifsp.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.ifsp.exception.ContatoException;
import com.example.ifsp.model.Contato;
import com.example.ifsp.validator.ContatoValidador;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    // Configuração do banco
    private static final String DATABASE_NAME = "bancodedados.db";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contato";

    private Context context;
    private SQLiteDatabase db;

    private SQLiteStatement insertStnt;
    private static final String INSERT = "INSERT INTO " + TABLE_NAME + "(nome, cpf, idade, telefone, email) VALUES (?, ?, ?, ?, ?);";

    public DBHelper(Context context) {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
        this.insertStnt = this.db.compileStatement(INSERT);
    }

    // Funcao de inserir um contato no banco
    public void insert (Contato c) throws ContatoException {
        // Valida se os dados do obj estao certos
        ContatoValidador.validador(c);
        try {
            // Insere os valores no comando do Banco
            this.insertStnt.bindString(1, c.getNome());
            this.insertStnt.bindString(2, c.getCpf());
            this.insertStnt.bindString(3, c.getIdade());
            this.insertStnt.bindString(4, c.getTelefone());
            this.insertStnt.bindString(5, c.getEmail());

            // Executa o insert
            this.insertStnt.executeInsert();
        } finally {
        }
    }

    // Pega todos os contatos do Banco
    public List<Contato> queryGetAll(){
        // Lista de COntatos
        List<Contato> listaContatos = null;
        Cursor cursor = null;

        try {
            // Comando Select do bando
            cursor = this.db.query(TABLE_NAME, new String[]{"nome", "cpf", "idade", "telefone", "email"}, null, null, null, null, null,null);
            // salva o total de obj retornados do select
            int registros = cursor.getCount();

            if (registros != 0){
                // Para cada inderacao cria um obj Contato e add na listaContato
                while (cursor.moveToNext()){
                    // se a lista de contatos estiver fazia, estancia ela
                    if (listaContatos == null) {
                        listaContatos = new ArrayList<>();
                    }
                    // cria o obj Contato e recebe os valores do select
                    Contato c = new Contato();
                    c.setNome(cursor.getString(0));
                    c.setCpf(cursor.getString(1));
                    c.setIdade(cursor.getString(2));
                    c.setTelefone(cursor.getString(3));
                    c.setEmail(cursor.getString(4));

                    // Salva os dados na lista
                    listaContatos.add(c);
                }
            }

        } finally {
            // fecha conexao com o banco
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        // retorna a lista de contatos
        return listaContatos;
    }

    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper (Context contex){
            super (contex, DATABASE_NAME, null, DATABASE_VERSION);
        }
        // Cria a cabela do banco de daos
        public void onCreate (SQLiteDatabase db) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, cpf TEXT, idade TEXT, telefone TEXT, email TEXT);";
            db.execSQL(sql);

        }
        // Atualiza a tela do banco de dados.
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(sql);
        }
    }
}