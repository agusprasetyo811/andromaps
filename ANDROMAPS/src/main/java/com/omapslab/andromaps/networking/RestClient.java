package com.omapslab.andromaps.networking;


import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omapslab.andromaps.baseapi.datamanager.TokenManager;
import com.omapslab.andromaps.baseapi.model.AccessToken;
import com.omapslab.andromaps.baseapi.model.AuthModel;
import com.omapslab.andromaps.contants.APPS_CORE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * REST HTTP/S
 * Network engine with RETROFIT 2.+
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class RestClient {

    private OkHttpClient.Builder httpClient = null;
    private Retrofit retrofit;
    private Context c;
    private TokenManager tokenManager;


    /**
     * @param baseUrl
     */
    public RestClient(String baseUrl) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     *
     * @param baseUrl
     * @param disableSSL
     */
    public RestClient(String baseUrl, boolean disableSSL) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        if (disableSSL) {
            try {
                URL url = new URL(baseUrl);
                SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(url);
                httpClient.sslSocketFactory(NoSSLv3Factory);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public RestClient(String baseUrl, String auth) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor(auth);
        httpClient.addInterceptor(authenticationInterceptor);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * Rest Client Core
     *
     * @param baseUrl
     */
    public RestClient(final Context c, String baseUrl, final AuthModel authModel) {
        tokenManager = new TokenManager();
        this.c = c;
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Cache-Control", "no-store")
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, final Response response) throws IOException {

                Log.e(APPS_CORE.TAGS, response.message());

                if (tokenManager.getTokenData(c) == null) {
                    try {
                        retrofit2.Response<AccessToken> accessTokenResponse = getAccessTokenCall(authModel).execute();
                        if (accessTokenResponse.code() == 200) {
                            AccessToken accessToken = accessTokenResponse.body();
                            tokenManager.saveAccessToken(c, accessToken);
                            Log.i(APPS_CORE.TAGS, new Gson().toJson(accessToken));
                            return response.request().newBuilder()
                                    .header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
                                    .build();

                        } else {
                            Log.e(APPS_CORE.TAGS, "Access Token Error Log  -> " + accessTokenResponse.message());
                            return null;
                        }
                    } catch (IOException e) {
                        Log.e(APPS_CORE.TAGS, e.getMessage());
                        return null;
                    }
                } else {
                    retrofit2.Response<AccessToken> accessTokenResponse;
                    try {
                        accessTokenResponse = getRefreshTokenCall(authModel).execute();
                        if (accessTokenResponse.code() == 200) {
                            AccessToken accessToken = accessTokenResponse.body();
                            tokenManager.saveAccessToken(c, accessToken);
                            Log.i(APPS_CORE.TAGS, new Gson().toJson(accessToken));
                            return response.request().newBuilder()
                                    .header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
                                    .build();

                        } else {
                            try {
                                accessTokenResponse = getAccessTokenCall(authModel).execute();
                                if (accessTokenResponse.code() == 200) {
                                    AccessToken accessToken = accessTokenResponse.body();
                                    tokenManager.saveAccessToken(c, accessToken);
                                    Log.i(APPS_CORE.TAGS, new Gson().toJson(accessToken));
                                    return response.request().newBuilder()
                                            .header("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken())
                                            .build();

                                } else {
                                    Log.e(APPS_CORE.TAGS, "Access Token Error Log  -> " + accessTokenResponse.message());
                                    return null;
                                }
                            } catch (IOException e) {
                                Log.e(APPS_CORE.TAGS, e.getMessage());
                                return null;
                            }
                        }
                    } catch (IOException e) {
                        Log.e(APPS_CORE.TAGS, e.getMessage());
                        return null;
                    }
                }
            }
        });

        OkHttpClient okClient = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get Access Token call
     *
     * @param authModel
     * @return
     */
    public Call<AccessToken> getAccessTokenCall(AuthModel authModel) {
        String key = authModel.getClientId() + ":" + authModel.getClientSecret();
        byte[] data = key.getBytes();
        String generateKey = "Basic " + Base64.encodeToString(data, Base64.DEFAULT);
        AUTH auth = restClientHelper(authModel.getBaseURL(), generateKey.trim(), AUTH.class);
        Call<AccessToken> accessTokenCall = auth.getAccessToken(
                authModel.getAuthURL(),
                authModel.getClientId(),
                authModel.getClientSecret(),
                authModel.getUsername(),
                authModel.getPassword(),
                authModel.getGrantType(),
                authModel.getScope()
        );

        return accessTokenCall;
    }

    /**
     * Get Refresh token call
     *
     * @param authModel
     * @return
     */
    public Call<AccessToken> getRefreshTokenCall(AuthModel authModel) {
        Log.i(APPS_CORE.TAGS, "OPEN REST REFRESH TOKEN CALL");
        String key = authModel.getClientId() + ":" + authModel.getClientSecret();
        byte[] data = key.getBytes();
        String generateKey = "Basic " + Base64.encodeToString(data, Base64.DEFAULT);
        AUTH auth = restClientHelper(authModel.getBaseURL(), generateKey.trim(), AUTH.class);
        Call<AccessToken> accessTokenCall = auth.getRefreshToken(
                authModel.getAuthURL(),
                authModel.getClientId(),
                authModel.getClientSecret(),
                "refresh_token",
                tokenManager.getTokenData(c).getRefreshToken()
        );
        return accessTokenCall;
    }


    public <T> T restClientHelper(String baseUrl, final String authKey, Class<T> apiInterfaceClass) {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();
                                Request.Builder requestBuilder = original.newBuilder()
                                        .header("Authorization", authKey)
                                        .addHeader("Cache-Control", "no-cache")
                                        .addHeader("Cache-Control", "no-store")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        })
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(apiInterfaceClass);
    }


    /**
     * AuthenticationInterceptor
     */
    public class AuthenticationInterceptor implements Interceptor {

        private String authToken;

        public AuthenticationInterceptor(String token) {
            this.authToken = token;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .header("Authorization", authToken);
            Request request = builder.build();
            return chain.proceed(request);
        }
    }

    /**
     * Creates an implementation of the API defined
     * by the ApiInterfaceClass
     *
     * @param apiInterfaceClass
     * @param <T>
     * @return
     */

    public <T> T create(Class<T> apiInterfaceClass) {
        return retrofit.create(apiInterfaceClass);
    }


    public interface AUTH {

        /**
         * Get Access Token
         *
         * @param url
         * @param clientId
         * @param secret
         * @param username
         * @param password
         * @param grantType
         * @param scope
         * @return
         */
        @Headers("Accept: application/json")
        @POST
        @FormUrlEncoded
        Call<AccessToken> getAccessToken(
                @Url String url,
                @Field("client_id") String clientId,
                @Field("client_secret") String secret,
                @Field("username") String username,
                @Field("password") String password,
                @Field("grant_type") String grantType,
                @Field("scope") String scope

        );

        /**
         * Get Refresh Token
         *
         * @param url
         * @param clientId
         * @param secret
         * @param grantType
         * @param refreshToken
         * @return
         */
        @Headers("Accept: application/json")
        @POST
        @FormUrlEncoded
        Call<AccessToken> getRefreshToken(
                @Url String url,
                @Field("client_id") String clientId,
                @Field("client_secret") String secret,
                @Field("grant_type") String grantType,
                @Field("refresh_token") String refreshToken

        );

    }

}
