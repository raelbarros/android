package br.senac.dynamiclayout.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import br.senac.dynamiclayout.R;
import br.senac.dynamiclayout.api.Api;
import br.senac.dynamiclayout.model.Produto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_activity extends AppCompatActivity {

    private ViewGroup mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Produto>> call = api.getObject();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                List<Produto> listaProduto = response.body();
                for (Produto produto : listaProduto) {
                    addCard(produto.getNomeProduto(), String.valueOf(produto.getPrecProduto()), produto.getIdProduto());
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                showDialog("Erro de Conexão", "Erro");
                t.printStackTrace();
            }
        });
    }

    private void addCard(String title, String msg, int id) {
        CardView cardview = (CardView) LayoutInflater.from(this).inflate(R.layout.cardview, mainLayout, false);
        TextView txtTitle = cardview.findViewById(R.id.txtNome);
        TextView txtMsg = cardview.findViewById(R.id.txtPreco);

        String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + id;
        ImageView imageView = cardview.findViewById(R.id.image);
        ImageLoader imagemLoader = ImageLoader.getInstance();
        imagemLoader.init(ImageLoaderConfiguration.createDefault(Main_activity.this));

        imagemLoader.displayImage(url, imageView);

        txtTitle.setText(title);
        txtMsg.setText(msg);
        mainLayout.addView(cardview);
    }

    private void showDialog(String val, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_activity.this);
        builder.setMessage(val);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
