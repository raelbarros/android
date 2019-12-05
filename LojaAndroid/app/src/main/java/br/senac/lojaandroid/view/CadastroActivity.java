package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiCliente;
import br.senac.lojaandroid.model.Cliente;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.MaskUtil;
import br.senac.lojaandroid.util.Util;
import br.senac.lojaandroid.validador.ValidadorCliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroActivity extends AppCompatActivity {

    private Button btnSalvar, btnCancelar, btnEscolherImg;
    private EditText txtNome, txtSobrenome, txtPhone, txtCPF, txtEmail, txtPasswd;
    private ImageView imageUser;
    private String auxImage;
    private ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobreN);
        txtPhone = findViewById(R.id.txtPhone);
        txtCPF = findViewById(R.id.txtCPF);
        txtEmail = findViewById(R.id.txtEmail);
        txtPasswd = findViewById(R.id.txtPasswd);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnCancelar = findViewById(R.id.btnCancel);
        btnEscolherImg = findViewById(R.id.btnEscolherImg);
        imageUser = findViewById(R.id.imageUser);
        loader = findViewById(R.id.loader);

        //Mascara dos inputs
        txtCPF.addTextChangedListener(MaskUtil.mask(txtCPF, MaskUtil.FORMAT_CPF));
        txtPhone.addTextChangedListener(MaskUtil.mask(txtPhone, MaskUtil.FORMAT_FONE));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);

                final Cliente cliente = new Cliente();
                cliente.setNomeCompletoCliente(txtNome.getText().toString() + " " + txtSobrenome.getText().toString());
                cliente.setCelularCliente(txtPhone.getText().toString());
                cliente.setCPFCliente(txtCPF.getText().toString());
                cliente.setEmailCliente(txtEmail.getText().toString());
                cliente.setSenhaCliente(txtPasswd.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://oficinacordova.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiCliente apiCliente = retrofit.create(ApiCliente.class);
                Call<Cliente> clienteCall = apiCliente.setCliente(cliente);

                clienteCall.enqueue(new Callback<Cliente>() {
                    @Override
                    public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                        try {
                            //Valida o obj cliente
                            ValidadorCliente.validate(cliente);

                            if (response.code() == 200) {
                                Cliente clienteResp = response.body();

                                cliente.setIdCliente(clienteResp.getIdCliente());
                                cliente.setImage(auxImage);

                                // Salva o status de logado na preferencia compartilhada
                                SharedPreferences.Editor editor = Util.getPreference(CadastroActivity.this).edit();
                                editor.putBoolean("isLogado", true);
                                editor.putInt("currentUser", clienteResp.getIdCliente());

                                editor.apply();

                                // Salva o Cliente no SQLLite
                                LojaDatabase appDB = LojaDatabase.getInstance(CadastroActivity.this);
                                appDB.clienteDao().insertCliente(cliente);

                                loader.setVisibility(View.GONE);

                                // Retorna para a pagina Inicial
                                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                loader.setVisibility(View.GONE);
                                Toast.makeText(CadastroActivity.this, "Falha de conexao!", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            loader.setVisibility(View.GONE);
                            Toast.makeText(CadastroActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Cliente> call, Throwable t) {

                    }
                });
            }
        });

        btnEscolherImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(2,2)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .start(CadastroActivity.this);
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                String nameImage = System.currentTimeMillis()+".jpg";

                File to = new File(Environment.getExternalStorageDirectory(), nameImage);

                try {
                    to.createNewFile();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                    FileOutputStream fOut = new FileOutputStream(to);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                    auxImage = nameImage;

                    imageUser.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

}
