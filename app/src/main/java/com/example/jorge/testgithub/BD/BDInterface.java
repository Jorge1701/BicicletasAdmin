package com.example.jorge.testgithub.BD;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BDInterface {

	@FormUrlEncoded
	@POST ("login")
	Call<Respuesta> login (
			@Field ("email") String email,
			@Field ("password") String password,
			@Field ("admin") String admin
	);

	@GET("paradas")
	Call<RespuestaParadas> getParadas();

	@FormUrlEncoded
	@POST ("agregarParada")
	Call<Respuesta> agregarParada (
	        @Field ("nombre") String nombre,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field ("direccion") String direccion,
            @Field ("cantBicis") int cantBicis
   );

}
