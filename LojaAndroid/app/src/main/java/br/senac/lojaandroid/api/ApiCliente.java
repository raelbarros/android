package br.senac.lojaandroid.api;

import br.senac.lojaandroid.model.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCliente {
    @POST("/android/rest/cliente/cadastro")
    Call<Cliente> setCliente(@Body Cliente cliente);

    @POST("/android/rest/cliente")
    Call<Cliente> setLogin(@Body Cliente cliente);
}
