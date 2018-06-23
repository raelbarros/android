package com.example.ifsp.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ifsp.DBHelper.DBHelper;
import com.example.ifsp.R;
import com.example.ifsp.model.Contato;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    // Classe das funcoes do db
    private DBHelper dh;

    // Objetos pertencentes a view
    EditText nome, cpf, idade, tel, email;
    Button btnVoltar, btnSalvar, btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Atribuiçao dos obj da da view
        nome = (EditText) findViewById(R.id.txtNome);
        cpf = (EditText) findViewById(R.id.txtCPF);
        idade = (EditText) findViewById(R.id.txtIdade);
        tel = (EditText) findViewById(R.id.txtTel);
        email = (EditText) findViewById(R.id.txtEmail);

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnListar = (Button) findViewById(R.id.btnListar);

        // Funcao voltar para tela anterior
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Funcao de salvar
        this.dh = new DBHelper(this);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instancia o obj contato
                Contato c = new Contato();

                // Atribui os valores passados nao view para os contatos
                c.setNome(nome.getText().toString());
                c.setCpf(cpf.getText().toString());
                c.setIdade(idade.getText().toString());
                c.setTelefone(tel.getText().toString());
                c.setEmail(email.getText().toString());
                try {
                    // Insere o contato no banco
                    dh.insert(c);
                    // Mostra uma msg de sucesso
                    showAlert("Sucesso", "Cadastro inserido com sucesso!");
                    // Limpa os campos
                    clear();
                } catch (Exception e) {
                    // Caso haja algum erro, informa o que esta faltando atravez de um alerta
                    showAlert("Erro", e.getMessage());
                    // Limpa os campos
                    clear();
                }
            }
        });

        // Funcao para mostrar todos os obj cadastrados no banco
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pega a lista que estiver no banco
                List<Contato> listaContatos = dh.queryGetAll();

                // Se a lista estiver vazia, informa uma msg de erro e interrompe a funcao
                if (listaContatos == null) {
                    showAlert("Sem Resultados", "Não há registros cadastrados!");
                    return;
                }

                // Percorre o a lista de contatos e mostra as informacoes atravez de um alerta
                int cont = 0;
                for (Contato c : listaContatos) {
                    cont++;
                    String msg = "Nome: " + c.getNome() +"\nCPF: " +c.getCpf() + "\nIdade: " + c.getIdade() +"\nTelefone: " + c.getTelefone() + "\nE-mail: " + c.getEmail();
                    showAlert("Registro " + cont, msg);
                }
            }
        });


    }

    // Funcao de criacao de alerta
    // Informar o titulo do alerta e a msg exibida
    private void showAlert(String title, String msg){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    // Funcao de limpas os valores dos imputs da view
    private void clear (){
        nome.setText("");
        cpf.setText("");
        idade.setText("");
        tel.setText("");
        email.setText("");
    }
}
