package it.trade.model;


import okhttp3.Cookie;

import java.util.List;

public interface RequestCookieProvider {
    List<Cookie> provideCookies();
}
