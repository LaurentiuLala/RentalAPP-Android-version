package com.example.proiect.network;

import com.example.proiect.model.*;
import java.util.List;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("users/login")
    Call<UserDTO> login(@Body UserLoginDTO loginDTO);

    @POST("users/register")
    Call<Void> register(@Body UserRegisterDTO registerDTO);

    @GET("masini")
    Call<List<MasinaDTO>> getAllMasini();

    @GET("masini/{id}")
    Call<MasinaDTO> getMasinaById(@Path("id") Long id);

    @GET("locatii")
    Call<List<LocatieDTO>> getAllLocatii();

    @POST("inchirieri")
    Call<InchiriereDTO> createInchiriere(@Body InchiriereDTO inchiriereDTO);

    @GET("inchirieri/user/{userId}")
    Call<List<InchiriereDTO>> getUserInchirieri(@Path("userId") Long userId);

    @POST("masini")
    Call<MasinaDTO> createMasina(@Body MasinaDTO dto);

    @PUT("masini/{id}")
    Call<MasinaDTO> updateMasina(@Path("id") Long id, @Body MasinaDTO dto);

    @DELETE("masini/{id}")
    Call<Void> deleteMasina(@Path("id") Long id);

    @POST("locatii")
    Call<LocatieDTO> createLocatie(@Body LocatieDTO dto);

    @PUT("locatii/{id}")
    Call<LocatieDTO> updateLocatie(@Path("id") Long id, @Body LocatieDTO dto);

    @DELETE("locatii/{id}")
    Call<Void> deleteLocatie(@Path("id") Long id);

    @GET("users")
    Call<List<UserDTO>> getAllUsers();

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") Long id);

    @Multipart
    @POST("masini/{id}/images")
    Call<Void> uploadImages(@Path("id") Long id, @Part List<MultipartBody.Part> files);

    @DELETE("masini/{id}/images")
    Call<Void> deleteImages(@Path("id") Long id);
}
