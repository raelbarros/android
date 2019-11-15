package br.senac.lojaandroid.api;

import java.util.List;

import br.senac.lojaandroid.model.Categoria;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCategoria {
    @GET("/android/rest/categoria")
    Call<List<Categoria>> getCategoria();
}
