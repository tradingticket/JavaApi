package it.trade.interceptors;

import it.trade.model.Cookie;
import it.trade.model.RequestCookieProvider;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Set;

public class AddCookiesInterceptor implements Interceptor {
    private RequestCookieProvider requestCookieProvider;

    public AddCookiesInterceptor(RequestCookieProvider requestCookieProvider) {
        this.requestCookieProvider = requestCookieProvider;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<Cookie> cookies = requestCookieProvider.provideCookies();
        if (cookies != null && !cookies.isEmpty()) {
            String cookieString = "";
            for (Cookie cookie : cookies) {
                cookieString += cookie.getName() + "=" + cookie.getValue() + ";";
            }
            builder.addHeader("Cookie", cookieString);
        }

        return chain.proceed(builder.build());
    }
}