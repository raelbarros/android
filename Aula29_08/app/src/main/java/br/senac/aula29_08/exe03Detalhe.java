package br.senac.aula29_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class exe03Detalhe extends AppCompatActivity {

    TextView txtName, txtLastName;
    Button btnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe03_detalhe);

        txtName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        btnPrevious = findViewById(R.id.btnPrevious);

        txtName.setBackgroundColor(Color.GRAY);
        txtLastName.setBackgroundColor(Color.GRAY);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String lastName = intent.getStringExtra("lastName");

        txtName.setText(name);
        txtLastName.setText(lastName);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exe03Detalhe.this, exe03.class);
                startActivity(intent);
            }
        });
    }
}
