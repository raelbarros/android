package br.senac.lojaandroid.api;

import java.util.List;
import br.senac.lojaandroid.model.Produto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiProduto {
    @GET("/android/rest/produto")
    Call<List<Produto>> getProdutos();

}
