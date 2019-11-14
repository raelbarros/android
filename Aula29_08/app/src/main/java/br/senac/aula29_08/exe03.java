package br.senac.aula29_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class exe03 extends AppCompatActivity {

    EditText txtName, txtLastName;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe03);

        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exe03.this, exe03Detalhe.class);
                intent.putExtra("name", txtName.getText().toString());
                intent.putExtra("lastName", txtLastName.getText().toString());

                startActivity(intent);
            }
        });
    }
}
