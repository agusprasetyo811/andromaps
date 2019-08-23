package com.omapslab.andromaps.networking;


import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omapslab.andromaps.baseapi.datamanager.TokenManager;
import com.omapslab.andromaps.baseapi.model.AccessToken;
import com.omapslab.andromaps.baseapi.model.AuthModel;
import com.omapslab.andromaps.contants.APPS_CORE;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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


    public static OkHttpClient.Builder setSSLFactoryForClient(OkHttpClient.Builder client) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            client.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            client.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return client;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }


    ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2)
            .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
            .build();


    /**
     * @param baseUrl
     * @param disableSSL
     */
    public RestClient(String baseUrl, boolean disableSSL) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Cache-Control", "no-store")
                        /*.addHeader("Accept-Encoding", "gzip")
                        .addHeader("Content-Encoding", "identity")*/
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.followRedirects(true);
        httpClient.followSslRedirects(true);
        httpClient.retryOnConnectionFailure(true);
        httpClient.cache(null);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (disableSSL) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(enableTls12OnPreLollipop(httpClient).build())
                    .client(setSSLFactoryForClient(httpClient).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }


    public static class Config {
        private HashMap<String, String> customHeader = new HashMap<>();
        private int timeout = 60;
        private HttpLoggingInterceptor.Level loggingLevel = HttpLoggingInterceptor.Level.BODY;

        public HashMap<String, String> getCustomHeader() {
            return customHeader;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public HttpLoggingInterceptor.Level getLoggingLevel() {
            return loggingLevel;
        }

        public void setLoggingLevel(HttpLoggingInterceptor.Level loggingLevel) {
            this.loggingLevel = loggingLevel;
        }
    }


    public RestClient(String baseUrl, boolean disableSSL, final Config config) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(config.getLoggingLevel());
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                requestBuilder.addHeader("Cache-Control", "no-cache");
                requestBuilder.addHeader("Cache-Control", "no-store");
                for (String key : config.customHeader.keySet()) {
                    requestBuilder.addHeader(key, config.customHeader.get(key));
                }
                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        httpClient.followRedirects(true);
        httpClient.followSslRedirects(true);
        httpClient.retryOnConnectionFailure(true);
        httpClient.cache(null);
        httpClient.readTimeout(config.timeout, TimeUnit.SECONDS);
        httpClient.connectTimeout(config.timeout, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (disableSSL) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(enableTls12OnPreLollipop(httpClient).build())
                    .client(setSSLFactoryForClient(httpClient).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
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
