package it.trade.api;

import com.google.gson.annotations.SerializedName;
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
import java.util.concurrent.TimeUnit;

public class StatelessTradeItApiClient {
    protected transient TradeItApi tradeItApi;

    @SerializedName("requestInterceptor")
    protected Interceptor requestInterceptor;
    @SerializedName("environment")
    protected TradeItEnvironment environment;

    public StatelessTradeItApiClient(TradeItEnvironment environment) {
        this(environment, null, false);
    }

    public StatelessTradeItApiClient(TradeItEnvironment environment, boolean forceTLS12) {
        this(environment, null, forceTLS12);
    }

    /**
     * Use this constructor if you want to add your custom headers to each request
     */
    public StatelessTradeItApiClient(String apiKey, TradeItEnvironment environment, Interceptor requestInterceptor) {
        this(environment, requestInterceptor, false);
    }

    public StatelessTradeItApiClient(TradeItEnvironment environment, Interceptor requestInterceptor, boolean forceTLS12) {
        this.environment = environment;
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

    protected StatelessTradeItApiClient(String apiKey, TradeItApi tradeItApi) { //used for unit tests
        this.tradeItApi = tradeItApi;
    }

    protected StatelessTradeItApiClient() {}

    private void injectSession(String sessionToken, TradeItRequestWithSession request) {
        request.sessionToken = sessionToken;
    }

    public void getAvailableBrokers(String apiKey, final TradeItCallback<List<Broker>> callback) {
        TradeItRequestWithKey request = new TradeItRequestWithKey(apiKey);
        tradeItApi.getAvailableBrokers(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItAvailableBrokersResponse, List<Broker>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItAvailableBrokersResponse> response) {
                callback.onSuccess(response.body().brokerList);
            }
        });
    }

    public void getOAuthLoginPopupUrlForMobile(String apiKey, String broker, String deepLinkCallback, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForMobileRequest request = new TradeItOAuthLoginPopupUrlForMobileRequest(apiKey, broker, deepLinkCallback);
        tradeItApi.getOAuthLoginPopupUrlForMobile(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForMobileResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForMobileResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void getOAuthLoginPopupUrlForWebApp(String apiKey, String broker, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForWebAppRequest request = new TradeItOAuthLoginPopupUrlForWebAppRequest(apiKey, broker);
        tradeItApi.getOAuthLoginPopupUrlForWebApp(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForWebAppResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForWebAppResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void getOAuthLoginPopupUrlForTokenUpdate(String apiKey, String broker, String userId, String userToken, String deepLinkCallback, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForTokenUpdateRequest request = new TradeItOAuthLoginPopupUrlForTokenUpdateRequest(apiKey, broker, deepLinkCallback, userId, userToken);
        tradeItApi.getOAuthLoginPopupURLForTokenUpdate(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForTokenUpdateResponse, String>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> response) {
                callback.onSuccess(response.body().oAuthURL);
            }
        });
    }

    public void linkBrokerWithOauthVerifier(String apiKey, String oAuthVerifier, final TradeItCallback<TradeItLinkedLogin> callback) {
        final TradeItOAuthAccessTokenRequest request = new TradeItOAuthAccessTokenRequest(apiKey, oAuthVerifier);
        tradeItApi.getOAuthAccessToken(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOAuthAccessTokenResponse, TradeItLinkedLogin>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOAuthAccessTokenResponse> response) {
                TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin(request, response.body());
                callback.onSuccess(linkedLogin);
            }
        });
    }

    public void unlinkBrokerAccount(String apiKey, TradeItLinkedLogin linkedLogin, final TradeItCallback callback) {
        TradeItUnlinkLoginRequest request = new TradeItUnlinkLoginRequest(apiKey, linkedLogin);
        tradeItApi.unlinkLogin(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void authenticate(String apiKey, TradeItLinkedLogin linkedLogin, final AuthenticationCallback<TradeItAuthenticateResponse, ?> callback) {
        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(apiKey, linkedLogin);

        tradeItApi.authenticate(authenticateRequest).enqueue(new Callback<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                callback.onResponse(call, response);
            }

            public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void answerSecurityQuestion(String sessionToken, TradeItAnswerSecurityQuestionRequest request, final Callback<TradeItAuthenticateResponse> callback) {
        injectSession(sessionToken, request);
        tradeItApi.answerSecurityQuestion(request).enqueue(new Callback<TradeItAuthenticateResponse>() {
            public void onResponse(Call<TradeItAuthenticateResponse> call, Response<TradeItAuthenticateResponse> response) {
                callback.onResponse(call, response);
            }
            public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });

    }

    public void keepSessionAlive(String sessionToken, final TradeItCallback<TradeItResponse> callback) {
        TradeItRequestWithSession request = new TradeItRequestWithSession();
        injectSession(sessionToken, request);
        tradeItApi.keepSessionAlive(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void closeSession(String sessionToken, final TradeItCallback<TradeItResponse> callback) {
        TradeItRequestWithSession request = new TradeItRequestWithSession();
        injectSession(sessionToken, request);
        tradeItApi.closeSession(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void previewStockOrEtfOrder(String sessionToken, TradeItPreviewStockOrEtfOrderRequest request, final TradeItCallback<TradeItPreviewStockOrEtfOrderResponse> callback) {
        injectSession(sessionToken, request);
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

    public void placeStockOrEtfOrder(String sessionToken, String orderId, final TradeItCallback<TradeItPlaceStockOrEtfOrderResponse> callback) {
        TradeItPlaceStockOrEtfOrderRequest request = new TradeItPlaceStockOrEtfOrderRequest(orderId);
        injectSession(sessionToken, request);
        tradeItApi.placeStockOrEtfOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItPlaceStockOrEtfOrderResponse, TradeItPlaceStockOrEtfOrderResponse>(callback) {

            @Override
            public void onSuccessResponse(Response<TradeItPlaceStockOrEtfOrderResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getAccountOverview(String sessionToken, String accountNumber, final TradeItCallback<TradeItAccountOverviewResponse> callback) {
        TradeItGetAccountOverviewRequest request = new TradeItGetAccountOverviewRequest(accountNumber);
        injectSession(sessionToken, request);
        tradeItApi.getAccountOverview(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItAccountOverviewResponse, TradeItAccountOverviewResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItAccountOverviewResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getPositions(String sessionToken, String accountNumber, final TradeItCallback<List<TradeItPosition>> callback) {
        TradeItGetPositionsRequest request = new TradeItGetPositionsRequest(accountNumber, null);
        injectSession(sessionToken, request);
        tradeItApi.getPositions(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetPositionsResponse, List<TradeItPosition>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetPositionsResponse> response) {
                callback.onSuccess(response.body().positions);
            }
        });
    }

    public void getAllOrderStatus(String sessionToken, String accountNumber, final TradeItCallback<List<OrderStatusDetails>> callback) {
        TradeItGetAllOrderStatusRequest request = new TradeItGetAllOrderStatusRequest(accountNumber);
        injectSession(sessionToken, request);
        tradeItApi.getAllOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, List<OrderStatusDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                callback.onSuccess(response.body().orderStatusDetailsList);
            }
        });
    }

    public void getSingleOrderStatus(String sessionToken, String accountNumber, String orderNumber, final TradeItCallback<OrderStatusDetails> callback) {
        TradeItGetSingleOrderStatusRequest request = new TradeItGetSingleOrderStatusRequest(accountNumber, orderNumber);
        injectSession(sessionToken, request);
        tradeItApi.getSingleOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void cancelOrder(String sessionToken, String accountNumber, String orderNumber, final TradeItCallback<OrderStatusDetails> callback) {
        TradeItCancelOrderRequest request = new TradeItCancelOrderRequest(accountNumber, orderNumber);
        injectSession(sessionToken, request);
        tradeItApi.cancelOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void getAllTransactionsHistory(String sessionToken, String accountNumber, final TradeItCallback<List<TransactionDetails>>  callback) {
        TradeItGetAllTransactionsHistoryRequest request = new TradeItGetAllTransactionsHistoryRequest(accountNumber);
        injectSession(sessionToken, request);

        tradeItApi.getAllTransactionsHistory(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetAllTransactionsHistoryResponse, List<TransactionDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetAllTransactionsHistoryResponse> response) {
                callback.onSuccess(response.body().transactionHistoryDetailsList);
            }
        });
    }

    public TradeItEnvironment getEnvironment() {
        return environment;
    }
}
