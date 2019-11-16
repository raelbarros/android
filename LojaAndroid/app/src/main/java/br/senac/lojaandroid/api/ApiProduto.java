package br.senac.lojaandroid.api;

import java.util.List;

import br.senac.lojaandroid.model.Categoria;
import br.senac.lojaandroid.model.Produto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiProduto {
    @GET("/android/rest/produto")
    Call<List<Produto>> getProdutos();

    @GET("/android/rest/produto/categoria/{id}")
    Call<List<Produto>> getProdutoByCategoria(@Path("id") Integer id);
}
