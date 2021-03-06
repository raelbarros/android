package br.senac.lojaandroid.view;


import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amitshekhar.DebugDB;

import java.util.List;

import br.senac.lojaandroid.R;
import br.senac.lojaandroid.api.ApiCategoria;
import br.senac.lojaandroid.model.Categoria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }

    private ViewGroup mainLayout;
    private ProgressBar loader;

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity = (MainActivity)getActivity();
            mainActivity.setTitle(R.string.app_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //IP Dbug DB
        System.out.println(DebugDB.getAddressLog());


        mainLayout = view.findViewById(R.id.itensLayout);
        loader = view.findViewById(R.id.loader);

        loader.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://oficinacordova.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCategoria apiCategoria = retrofit.create(ApiCategoria.class);
        Call<List<Categoria>> call = apiCategoria.getCategoria();

        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                try {
                    if (response.code() == 200) {
                        List<Categoria> listaCategoria = response.body();

                        for (Categoria c : listaCategoria) {
                            addCard(c.getNomeCategoria(), c.getIdCategoria());
                        }
                        loader.setVisibility(View.GONE);
                    }
                } catch (Throwable t) {

                }

            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    private void addCard(final String nome, final int id) {
        final CardView cardview = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.card_category_layout, mainLayout, false);
        final TextView nomeCategoria = cardview.findViewById(R.id.txtData);

        nomeCategoria.setText(nome);

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItensCategoria itens = new ItensCategoria();
                Bundle bundle = new Bundle();

                //Set id para ser usado na tela de itens
                bundle.putInt("id", id);
                bundle.putString("title", nome);

                itens.setArguments(bundle);

                //Chama o fragmento do itens
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_container, itens);
                fragmentTransaction.commit();
            }
        });
        mainLayout.addView(cardview);
    }

}
