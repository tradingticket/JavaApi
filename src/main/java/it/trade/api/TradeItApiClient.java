package it.trade.api;


import it.trade.factory.TLS12SocketFactory;
import it.trade.model.TradeItErrorResult;
import it.trade.model.callback.AuthenticationCallback;
import it.trade.model.callback.DefaultCallbackWithErrorHandling;
import it.trade.model.callback.PreviewTradeCallback;
import it.trade.model.callback.TradeItCallback;
import it.trade.model.reponse.*;
import it.trade.model.reponse.TradeItAvailableBrokersResponse.Broker;
import it.trade.model.request.*;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TradeItApiClient {
    protected transient TradeItApi tradeItApi;
    protected Interceptor requestInterceptor;
    protected String serverUuid;
    protected String sessionToken;
    protected TradeItEnvironment environment;
    protected String apiKey;
    protected boolean forceTLS12;

    public TradeItApiClient(String apiKey, TradeItEnvironment environment) {
        this(apiKey, environment, null, false);
    }

    public TradeItApiClient(String apiKey, TradeItEnvironment environment, boolean forceTLS12) {
        this(apiKey, environment, null, forceTLS12);
    }

    /**
     * Use this constructor if you want to add your custom headers to each request
     */
    public TradeItApiClient(String apiKey, TradeItEnvironment environment, Interceptor requestInterceptor) {
        this(apiKey, environment, requestInterceptor, false);
    }

    public TradeItApiClient(String apiKey, TradeItEnvironment environment, Interceptor requestInterceptor, boolean forceTLS12) {
        this.environment = environment;
        this.apiKey = apiKey;
        TradeItRequestWithKey.API_KEY = apiKey;
        this.tradeItApi = createTradeItApi(environment, requestInterceptor, forceTLS12);
    }

    protected TradeItApi createTradeItApi(TradeItEnvironment environment, final Interceptor requestInterceptor, boolean forceTLS12) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        if (requestInterceptor != null) {
            this.requestInterceptor = requestInterceptor;
            httpClientBuilder.networkInterceptors().add(requestInterceptor);
        }

        if (forceTLS12) {
            TLS12SocketFactory.enableTLS12(httpClientBuilder);
        }

//        httpClientBuilder.networkInterceptors().add(new LoggingInterceptor()); //uncomment if you want some request/response logs

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getBaseUrl())
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TradeItApi.class);
    }

    protected TradeItApiClient(TradeItApi tradeItApi) { //used for unit tests
        this.tradeItApi = tradeItApi;
    }

    protected TradeItApiClient() {}

    private void injectSession(TradeItRequestWithSession request) {
        request.sessionToken = sessionToken;
    }

    public void getAvailableBrokers(final TradeItCallback<List<Broker>> callback) {
        TradeItRequestWithKey request = new TradeItRequestWithKey();
        tradeItApi.getAvailableBrokers(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItAvailableBrokersResponse, List<Broker>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItAvailableBrokersResponse> response) {
                callback.onSuccess(response.body().brokerList);
            }
        });
    }

    public void getOAuthLoginPopupUrlForMobile(String broker, String deepLinkCallback, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForMobileRequest request = new TradeItOAuthLoginPopupUrlForMobileRequest(broker, deepLinkCallback);
        tradeItApi.getOAuthLoginPopupUrlForMobile(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForMobileResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForMobileResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void getOAuthLoginPopupUrlForWebApp(String broker, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForWebAppRequest request = new TradeItOAuthLoginPopupUrlForWebAppRequest(broker);
        tradeItApi.getOAuthLoginPopupUrlForWebApp(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForWebAppResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForWebAppResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void getOAuthLoginPopupUrlForTokenUpdate(String broker, String userId, String deepLinkCallback, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForTokenUpdateRequest request = new TradeItOAuthLoginPopupUrlForTokenUpdateRequest(broker, deepLinkCallback, userId);
        tradeItApi.getOAuthLoginPopupURLForTokenUpdate(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForTokenUpdateResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void linkBrokerWithOauthVerifier(String oAuthVerifier, final TradeItCallback<TradeItLinkedLogin> callback) {
        final TradeItOAuthAccessTokenRequest request = new TradeItOAuthAccessTokenRequest(oAuthVerifier);
        tradeItApi.getOAuthAccessToken(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthAccessTokenResponse, TradeItLinkedLogin>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthAccessTokenResponse> response) {
                TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin(request, response.body());
                callback.onSuccess(linkedLogin);
            }
        });
    }

    /**
     * @deprecated Use the new OAuth flow instead
     */
    @Deprecated
    public void linkBrokerAccount(String userId, String userPassword, String broker, final TradeItCallback<TradeItLinkedLogin> callback) {
        final TradeItLinkLoginRequest request = new TradeItLinkLoginRequest(userId, userPassword, broker);
        tradeItApi.linkLogin(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItLinkLoginResponse, TradeItLinkedLogin>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItLinkLoginResponse> response) {
                TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin(request, response.body());
                callback.onSuccess(linkedLogin);
            }
        });
    }

    public void unlinkBrokerAccount(TradeItLinkedLogin linkedLogin, final TradeItCallback callback) {
        TradeItUnlinkLoginRequest request = new TradeItUnlinkLoginRequest(linkedLogin);
        tradeItApi.unlinkLogin(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void authenticate(TradeItLinkedLogin linkedLogin, final AuthenticationCallback<TradeItAuthenticateResponse, ?> callback) {
        if (serverUuid == null) {
            serverUuid = UUID.randomUUID().toString();
        }

        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(linkedLogin);
        authenticateRequest.serverUuid = serverUuid;

        tradeItApi.authenticate(authenticateRequest).enqueue(new Callback<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                if (response.isSuccessful()) {
                    TradeItAuthenticateResponse authenticateResponse = response.body();
                    if (authenticateResponse.status == TradeItResponseStatus.SUCCESS || authenticateResponse.status == TradeItResponseStatus.INFORMATION_NEEDED) {
                        sessionToken = authenticateResponse.sessionToken;
                    }
                }
                callback.onResponse(call, response);
            }

            public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void answerSecurityQuestion(TradeItAnswerSecurityQuestionRequest request, Callback<TradeItAuthenticateResponse> callback) {
        request.serverUuid = serverUuid;
        injectSession(request);
        tradeItApi.answerSecurityQuestion(request).enqueue(new PassthroughCallback<>(callback));
    }

    public void keepSessionAlive(final TradeItCallback<TradeItResponse> callback) {
        TradeItRequestWithSession request = new TradeItRequestWithSession();
        injectSession(request);
        tradeItApi.keepSessionAlive(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void closeSession(final TradeItCallback<TradeItResponse> callback) {
        TradeItRequestWithSession request = new TradeItRequestWithSession();
        injectSession(request);
        tradeItApi.closeSession(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void previewStockOrEtfOrder(TradeItPreviewStockOrEtfOrderRequest request, final TradeItCallback<TradeItPreviewStockOrEtfOrderResponse> callback) {
        injectSession(request);
        tradeItApi.previewStockOrEtfOrder(request).enqueue(new PreviewTradeCallback<TradeItPreviewStockOrEtfOrderResponse, TradeItPreviewStockOrEtfOrderResponse>(callback) {

            @Override
            public void onSuccessResponse(Response<TradeItPreviewStockOrEtfOrderResponse> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onErrorResponse(TradeItErrorResult errorResult) {
                callback.onError(errorResult);
            }

        });
    }

    public void placeStockOrEtfOrder(String orderId, final TradeItCallback<TradeItPlaceStockOrEtfOrderResponse> callback) {
        TradeItPlaceStockOrEtfOrderRequest request = new TradeItPlaceStockOrEtfOrderRequest(orderId);
        injectSession(request);
        tradeItApi.placeStockOrEtfOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItPlaceStockOrEtfOrderResponse, TradeItPlaceStockOrEtfOrderResponse>(callback) {

            @Override
            public void onSuccessResponse(Response<TradeItPlaceStockOrEtfOrderResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getAccountOverview(String accountNumber, final TradeItCallback<TradeItAccountOverviewResponse> callback) {
        TradeItGetAccountOverviewRequest request = new TradeItGetAccountOverviewRequest(accountNumber);
        injectSession(request);
        tradeItApi.getAccountOverview(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItAccountOverviewResponse, TradeItAccountOverviewResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItAccountOverviewResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getPositions(String accountNumber, final TradeItCallback<List<TradeItPosition>> callback) {
        TradeItGetPositionsRequest request = new TradeItGetPositionsRequest(accountNumber, null);
        injectSession(request);
        tradeItApi.getPositions(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetPositionsResponse, List<TradeItPosition>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetPositionsResponse> response) {
                callback.onSuccess(response.body().positions);
            }
        });
    }

    public void getAllOrderStatus(String accountNumber, final TradeItCallback<List<OrderStatusDetails>> callback) {
        TradeItGetAllOrderStatusRequest request = new TradeItGetAllOrderStatusRequest(accountNumber);
        injectSession(request);
        tradeItApi.getAllOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, List<OrderStatusDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                callback.onSuccess(response.body().orderStatusDetailsList);
            }
        });
    }

    public void getSingleOrderStatus(String accountNumber, String orderNumber, final TradeItCallback<OrderStatusDetails> callback) {
        TradeItGetSingleOrderStatusRequest request = new TradeItGetSingleOrderStatusRequest(accountNumber, orderNumber);
        injectSession(request);
        tradeItApi.getSingleOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void cancelOrder(String acccountNumber, String orderNumber, final TradeItCallback<OrderStatusDetails> callback) {
        TradeItCancelOrderRequest request = new TradeItCancelOrderRequest(acccountNumber, orderNumber);
        injectSession(request);
        tradeItApi.cancelOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void getAllTransactionsHistory(String accountNumber, final TradeItCallback<List<TransactionDetails>>  callback) {
        TradeItGetAllTransactionsHistoryRequest request = new TradeItGetAllTransactionsHistoryRequest(accountNumber);
        injectSession(request);

        tradeItApi.getAllTransactionsHistory(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetAllTransactionsHistoryResponse, List<TransactionDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetAllTransactionsHistoryResponse> response) {
                callback.onSuccess(response.body().transactionHistoryDetailsList);
            }
        });
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    private class PassthroughCallback<T> implements Callback<T> {
        Callback<T> callback;

        PassthroughCallback(Callback<T> callback) {
            this.callback = callback;
        }

        public void onResponse(Call<T> call, Response<T> response) {
            callback.onResponse(call, response);
        }

        public void onFailure(Call<T> call, Throwable t) {
            callback.onFailure(call, t);
        }
    }

    public TradeItEnvironment getEnvironment() {
        return environment;
    }

    public String getApiKey() {
        return apiKey;
    }
}