package br.senac.dynamiclayout.api;

import java.util.List;

import br.senac.dynamiclayout.model.Produto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/android/rest/produto")
    Call<List<Produto>> getObject();
}
