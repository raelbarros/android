package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DecimalFormat;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiProduto;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.Singleton;
import br.senac.lojaandroid.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView txtProduto, txtPreco, txtDescricao, titleDescricao;
    private Button btnAdd;
    private ImageView imgProduto;
    private ProgressBar loader;
    private CardView card1, card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);


        txtProduto = findViewById(R.id.txtProduto);
        txtPreco = findViewById(R.id.txtPreco);
        txtDescricao = findViewById(R.id.txtDescricao);
        btnAdd = findViewById(R.id.btnAdd);
        imgProduto = findViewById(R.id.imgDetailProd);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        titleDescricao = findViewById(R.id.titleDescricao);

        loader = findViewById(R.id.loader);

        loader.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiProduto apiProduto = retrofit.create(ApiProduto.class);
        Call<Produto> call = apiProduto.getProduto(id);

        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                Produto p = response.body();

                //Altera o Titulo da pagina
                getSupportActionBar().setTitle(p.getNomeProduto());

                try {
                    txtPreco.setText(Util.formatPreco(p.getPrecProduto()));
                    txtProduto.setText(p.getNomeProduto());
                    txtDescricao.setText(p.getDescProduto());

                    String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + p.getIdProduto();
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(ItemDetailActivity.this));

                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.animation)
                            .showImageForEmptyUri(R.drawable.no_image96)
                            .showImageOnFail(R.drawable.no_image96)
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .build();

                    imageLoader.displayImage(url, imgProduto, options);

                    addCarrinho(p);


                    loader.setVisibility(View.GONE);
                    setItensVisible();
                } catch (Throwable t) {

                }

            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {

            }
        });


    }

    private void addCarrinho(final Produto p) {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Singleton s = Singleton.getInstance();
                s.addCarrinho(p);
                Util.showMsg(ItemDetailActivity.this, "Show!", "Produto adicionado no Carrinho!", "Continuar Comprando");

            }
        });
    }

    private void setItensVisible() {
        imgProduto.setVisibility(View.VISIBLE);
        card1.setVisibility(View.VISIBLE);
        txtProduto.setVisibility(View.VISIBLE);
        txtPreco.setVisibility(View.VISIBLE);
        card2.setVisibility(View.VISIBLE);
        titleDescricao.setVisibility(View.VISIBLE);
        txtDescricao.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.VISIBLE);
    }
}
