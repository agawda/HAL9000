package com.javaacademy.crawler.common.retrofit;

import com.javaacademy.crawler.common.model.BookModels;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface BookServerEndpoint {

    @PUT("/books/addall")
    Call<Object> putBooksToServer(@Body BookModels bookModels);
}
