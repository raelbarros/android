package br.senac.cep.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.senac.cep.R;
import br.senac.cep.api.ApiCep;
import br.senac.cep.model.Cep;
import br.senac.cep.model.MaskCep;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CepActivity extends AppCompatActivity {

    EditText txtCep;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cep);

        btnBuscar = findViewById(R.id.btnBuscar);
        txtCep = findViewById(R.id.txtCep);

        txtCep.addTextChangedListener(MaskCep.mask(txtCep, MaskCep.FORMAT_CEP));

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cep = txtCep.getText().toString();

                if (cep.isEmpty()) {
                    showDialog("Informe um CEP!", "Erro");
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://viacep.com.br/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiCep api = retrofit.create(ApiCep.class);
                Call<Cep> call = api.getObject(cep);

                call.enqueue(new Callback<Cep>() {
                    @Override
                    public void onResponse(Call<Cep> call, Response<Cep> response) {
                        Cep cep = response.body();

                        CepFragment fragment = new CepFragment();
                        getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();

                        fragment.AtualizarValue(cep);
                    }

                    @Override
                    public void onFailure(Call<Cep> call, Throwable t) {
                        showDialog("Erro de Conexão", "Erro");
                        t.printStackTrace();
                    }
                });

            }
        });


    }

    private void showDialog(String val, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CepActivity.this);
        builder.setMessage(val);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
