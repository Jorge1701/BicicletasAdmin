package com.example.jorge.testgithub.BD;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BDInterface {

    @FormUrlEncoded
    @POST("login")
    Call<Respuesta> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("admin") String admin
    );

    @GET("paradas")
    Call<RespuestaParadas> getParadas();

    @GET("promedioAlquileres")
    Call<RespuestaParadas> getPromedios();

    @GET("bicicletas")
    Call<RespuestaBicicletas> getBicicletas();

    @FormUrlEncoded
    @POST("agregarParada")
    Call<Respuesta> agregarParada(
            @Field("nombre") String nombre,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("direccion") String direccion,
            @Field("cantBicis") int cantBicis
    );

    @GET("usuarios")
    Call<RespuestaUsuarios> obtenerUsuarios();

    @FormUrlEncoded
    @POST("habilitar")
    Call<Respuesta> habilitar(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("inhabilitar")
    Call<Respuesta> inhabilitar(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("editarParada")
    Call<Respuesta> editarParada(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("lat") double lat,
            @Field("lng") double lng,
            @Field("direccion") String direccion,
            @Field("cantBicis") int cantBicis
    );

    @GET("incidencias")
    Call<RespuestaIncidencias> obtenerIncidencias();

    @FormUrlEncoded
    @POST("nuevoComentario")
    Call<Respuesta> agregarComentario(
            @Field("admin") String admin,
            @Field("incidencia") int incidencia,
            @Field("comentario") String comentario
    );

    @GET("administradores")
    Call<RespuestaAdministradores> obtenerAdministradores();

    @FormUrlEncoded
    @POST("cambiarEstadoIncidencia")
    Call<Respuesta> cambiarEstado(
            @Field("id") int id,
            @Field("admin") String admin,
            @Field("estado") int estado
    );

    @GET ("paradasPorFecha")
    Call<RespuestaParadas> getParadasPorFecha(@Query("fechaInicio") String fechaInicio, @Query("fechaFin") String fechaFin);
}
