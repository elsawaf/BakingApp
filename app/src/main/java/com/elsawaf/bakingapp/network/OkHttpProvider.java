package com.elsawaf.bakingapp.network;

import android.content.Context;

import com.elsawaf.bakingapp.BuildConfig;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpProvider {
    private static OkHttpClient instance = null;

    public static OkHttpClient getOkHttpInstance (Context context) {
        if (instance == null) {
            // Cache network requests for configuration changing and offline access with Retrofit2
            int cacheSize = 10 * 1024 * 1024; // 10 MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);

            OkHttpClient.Builder httpClient = new OkHttpClient
                    .Builder()
                    .cache(cache)
                    .addInterceptor(new Interceptor() {
                        @Override public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            if (NetworkUtils.hasNetworkAccess(context)) {
                                // If there is connectivity, the interceptor will tell the request it can reuse the data for sixty seconds.
                                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                            } else {
                                // If there's no connectivity, we ask to be given only (only-if-cached) 'stale' data up to 7 days ago
                                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                            }
                            return chain.proceed(request);
                        }
                    });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(loggingInterceptor);
            }

            instance = httpClient.build();
        }
        return instance;
    }
}
