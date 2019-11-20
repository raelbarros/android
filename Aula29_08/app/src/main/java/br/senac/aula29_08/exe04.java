package br.senac.aula29_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class exe04 extends AppCompatActivity {

    EditText txtValor1, txtValor2;
    TextView txtResult;
    Button btnCalcula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe04);

        txtValor1 = findViewById(R.id.txtValor1);
        txtValor2 = findViewById(R.id.txtValor2);
        txtResult = findViewById(R.id.txtResult);
        btnCalcula = findViewById(R.id.btnCalcula);

        btnCalcula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exe04.this, exe04Detalhe.class);
                intent.putExtra("val1", txtValor1.getText().toString());
                intent.putExtra("val2", txtValor2.getText().toString());

                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                txtResult.setText(data.getStringExtra("retorno"));
            }
            if (requestCode == RESULT_CANCELED) {
                txtResult.setText("Calculo Invalido");
            }
        }
    }
}
