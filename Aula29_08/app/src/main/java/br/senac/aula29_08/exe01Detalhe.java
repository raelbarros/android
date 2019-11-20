package br.senac.aula29_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class exe01Detalhe extends AppCompatActivity {

    Button btnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe01_detalhe);

        btnPrevious = findViewById(R.id.btnPrevious);

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(exe01Detalhe.this, exe01.class);
                startActivity(intent);
            }
        });
    }
}
