package it.trade.model;


import java.util.Set;

public interface RequestCookieProvider {
    Set<Cookie> provideCookies();
}
