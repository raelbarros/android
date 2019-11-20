package br.senac.cep.api;

import br.senac.cep.model.Cep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCep {
    @GET("/ws/{cep}/json")
    Call<Cep> getObject(@Path(value = "cep", encoded = true) String cep);
}
