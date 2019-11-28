package br.senac.lojaandroid.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiCliente;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPasswd;
    private TextView txtCadastro;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtCadastro = findViewById(R.id.txtCadastrar);
        txtPasswd = findViewById(R.id.txtPasswd);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Cliente cliente = new Cliente();
                cliente.setEmailCliente(txtEmail.getText().toString());
                cliente.setSenhaCliente(txtPasswd.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://oficinacordova.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiCliente apiCliente = retrofit.create(ApiCliente.class);
                Call<Cliente> callCliente = apiCliente.setLogin(cliente);

                callCliente.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        try {
                            if (response.code() == 200) {
                                Cliente clientResp = response.body();

                                // Salva o status de logado na preferencia compartilhada
                                SharedPreferences.Editor editor = Util.getPreference(LoginActivity.this).edit();
                                editor.putBoolean("logado", true);
                                editor.apply();

                                // Salva o Cliente no SQLLite
                                LojaDatabase appDB = LojaDatabase.getInstance(LoginActivity.this);
                                appDB.clienteDao().insertCliente(cliente);

                                // Retorna para a pagina Inicial
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }catch (Throwable t) {

                        }

                        //Pega a Instancia do DB
                        //LojaDatabase appDB = LojaDatabase.getInstance(LoginActivity.this);
                        //Salva no SQLite
                        //appDB.clienteDao().insertCliente(clientResp);
                        //Pega do SQLite
                        //Cliente clienteDB = appDB.clienteDao().clienteById(clientResp.getIdCliente());
                        //showDialog(clienteDB.getNomeCompletoCliente(), "Logou");
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

}
