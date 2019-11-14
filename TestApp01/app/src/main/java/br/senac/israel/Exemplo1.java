package br.senac.israel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Exemplo1 extends AppCompatActivity {

    EditText txtNome;
    EditText txtSobNome;
    Button btnShowMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo1);

        txtNome = findViewById(R.id.txtNome);
        txtSobNome = findViewById(R.id.txtSobNome);
        btnShowMsg = findViewById(R.id.btnShowMsg);

        btnShowMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMsg("Bem Vindo!", txtNome.getText().toString(), txtSobNome.getText().toString());
            }
        });
    }

    void showMsg(String title, String nome, String sobNome){
        AlertDialog.Builder builder = new AlertDialog.Builder(Exemplo1.this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(title);
        builder.setMessage(nome + " " +  sobNome);
        builder.setPositiveButton("Ok", null);
        builder.show();
    }
}
