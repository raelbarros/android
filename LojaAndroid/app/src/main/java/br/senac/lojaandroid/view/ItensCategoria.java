package br.senac.lojaandroid.view;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiProduto;
import br.senac.lojaandroid.model.Produto;
import br.senac.lojaandroid.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItensCategoria extends Fragment {


    public ItensCategoria() {
        // Required empty public constructor
    }

    private ViewGroup mainLayout;
    private ProgressBar loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_itens_categoria, container, false);

        mainLayout = view.findViewById(R.id.itensLayout);
        loader = view.findViewById(R.id.loader);

        final int id = getArguments().getInt("id");

        //Altera o tituto
        String title = getArguments().getString("title");
        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.setTitle(title);
        }

        loader.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiProduto apiProduto = retrofit.create(ApiProduto.class);
        Call<List<Produto>> call = apiProduto.getProdutoByCategoria(id);

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                List<Produto> listaProduto = response.body();

                for (Produto p : listaProduto) {
                    addLines(p.getNomeProduto(), p.getPrecProduto(), p.getIdProduto());
                }
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });

        return view;
    }

    private void addLines(String name, double preco, final int id) {
        LinearLayout linerLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.produtos_layout, mainLayout, false);

        TextView nomeProduto = linerLayout.findViewById(R.id.txtProduto);
        TextView precoProduto = linerLayout.findViewById(R.id.txtPreco);
        ImageView image = linerLayout.findViewById(R.id.imgProduto);

        String url = "https://oficinacordova.azurewebsites.net/android/rest/produto/image/" + id;
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.animation)
                .showImageForEmptyUri(R.drawable.error24px)
                .showImageOnFail(R.drawable.error24px)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(url, image, options);
        nomeProduto.setText(name);
        precoProduto.setText(Util.formatPreco(preco));

        linerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        mainLayout.addView(linerLayout);
    }

}
