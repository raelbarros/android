package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiProduto;
import br.senac.lojaandroid.model.ItensPedido;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.LojaDatabase;
import br.senac.lojaandroid.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaItensComprados extends AppCompatActivity {

    private ViewGroup mainLayout;
    private ProgressBar loader;

    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens_comprados);

        mainLayout = findViewById(R.id.itensComprados);
        txtTitle = findViewById(R.id.titleItensComprados);

        loader = findViewById(R.id.loader);

        //Mostra Progress Bar
        loader.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        long id = intent.getLongExtra("id",0);

        txtTitle.setText(title);

        LojaDatabase appDB = LojaDatabase.getInstance(ListaItensComprados.this);
        List<ItensPedido> listaItens = appDB.itensPedido().getById(id);

        for (ItensPedido i : listaItens) {
            callAPI(i.getIdProduto());
        }

        loader.setVisibility(View.GONE);
    }

    public void callAPI(long id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        int auxId = (int) id;
        ApiProduto apiProduto = retrofit.create(ApiProduto.class);
        Call<Produto> call = apiProduto.getProduto(auxId);

        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                Produto p = response.body();
                addCards(p.getNomeProduto(), p.getPrecProduto(), p.getIdProduto());
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {

            }
        });

    }

    private void addCards(String name, double preco, final int id) {
        CardView cardview = (CardView) LayoutInflater.from(ListaItensComprados.this).inflate(R.layout.produtos_layout, mainLayout, false);

        TextView nomeProduto = cardview.findViewById(R.id.txtProduto);
        TextView precoProduto = cardview.findViewById(R.id.txtPreco);
        ImageView image = cardview.findViewById(R.id.imgProduto);

        String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + id;
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(ListaItensComprados.this));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.animation)
                .showImageForEmptyUri(R.drawable.no_image96)
                .showImageOnFail(R.drawable.no_image96)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(url, image, options);
        nomeProduto.setText(name);
        precoProduto.setText(Util.formatPreco(preco));

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaItensComprados.this, ItemDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        mainLayout.addView(cardview);
    }
}
