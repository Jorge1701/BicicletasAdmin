package com.example.jorge.testgithub.BD;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BDInterface {

	@FormUrlEncoded
	@POST ("login")
	Call<Respuesta> login (
			@Field ("email") String email,
			@Field ("password") String password,
			@Field ("admin") String admin
	);
}
