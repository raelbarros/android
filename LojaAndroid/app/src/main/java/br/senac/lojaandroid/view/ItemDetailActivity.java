package br.senac.lojaandroid.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.DecimalFormat;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiProduto;
import br.senac.lojaandroid.model.Produto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemDetailActivity extends AppCompatActivity {

    TextView txtProduto, txtPreco, txtDescricao, txtPrecoCDesc;
    Button btnAdd;
    ImageView imgProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        txtProduto = findViewById(R.id.txtProduto);
        txtPreco = findViewById(R.id.txtPreco);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtPrecoCDesc = findViewById(R.id.txtPrecoCDesc);
        btnAdd = findViewById(R.id.btnAdd);
        imgProduto = findViewById(R.id.imgDetailProd);

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

                if (p.getDescontoPromocao() == 0) {
                    DecimalFormat format = new DecimalFormat("RS: 0.00");

                    txtPrecoCDesc.setVisibility(View.INVISIBLE);

                    txtPreco.setText(format.format(p.getPrecProduto()));

                    txtProduto.setText(p.getNomeProduto());
                    txtDescricao.setText(p.getDescProduto());

                    String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + p.getIdProduto();
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(ItemDetailActivity.this));
                    imageLoader.displayImage(url, imgProduto);

                } else {
                    DecimalFormat format = new DecimalFormat("RS: 0.00");

                    Double precoTotal = p.getPrecProduto() - p.getDescontoPromocao();

                    SpannableString preco = new SpannableString("DE - " + format.format(p.getPrecProduto()));
                    preco.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, preco.length(), 0);

                    txtPreco.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    txtPreco.setTextColor(Color.RED);
                    txtPreco.setText(preco);
                    txtPrecoCDesc.setText("POR - " + format.format(precoTotal));

                    txtProduto.setText(p.getNomeProduto());
                    txtDescricao.setText(p.getDescProduto());

                    String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + p.getIdProduto();
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(ItemDetailActivity.this));
                    imageLoader.displayImage(url, imgProduto);

                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {

            }
        });


    }
}
