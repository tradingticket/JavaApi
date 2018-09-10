package it.trade.api;

import it.trade.factory.TLS12SocketFactory;
import it.trade.model.TradeItErrorResult;
import it.trade.model.callback.AuthenticationCallback;
import it.trade.model.callback.DefaultCallbackWithErrorHandling;
import it.trade.model.callback.PreviewTradeCallback;
import it.trade.model.callback.TradeItCallback;
import it.trade.model.reponse.*;
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
    protected TradeItApi tradeItApi;

    public StatelessTradeItApiClient(TradeItEnvironment environment) {
        this(environment, null, false);
    }

    public StatelessTradeItApiClient(TradeItEnvironment environment, boolean forceTLS12) {
        this(environment, null, forceTLS12);
    }

    /**
     * Use this constructor if you want to add your custom headers to each request
     */
    public StatelessTradeItApiClient(TradeItEnvironment environment, Interceptor requestInterceptor) {
        this(environment, requestInterceptor, false);
    }

    public StatelessTradeItApiClient(TradeItEnvironment environment, Interceptor requestInterceptor, boolean forceTLS12) {
        this.tradeItApi = createTradeItApi(environment, requestInterceptor, forceTLS12);
    }

    protected TradeItApi createTradeItApi(TradeItEnvironment environment, final Interceptor requestInterceptor, boolean forceTLS12) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();

        httpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(30, TimeUnit.SECONDS);

        if (requestInterceptor != null) {
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

    public void getAvailableBrokers(
        String apiKey,
        final TradeItCallback<List<TradeItAvailableBrokersResponse.Broker>> callback
    ) {
        TradeItRequestWithKey request = new TradeItRequestWithKey(apiKey);
        tradeItApi.getAvailableBrokers(request).enqueue(
            new DefaultCallbackWithErrorHandling<
                TradeItAvailableBrokersResponse,
                List<TradeItAvailableBrokersResponse.Broker>
                >(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItAvailableBrokersResponse> response) {
                    callback.onSuccess(response.body().brokerList);
                }
            }
        );
    }

    public void getOAuthLoginPopupUrlForMobile(
        String apiKey,
        String broker,
        String deepLinkCallback,
        final TradeItCallback<String> callback
    ) {
        TradeItOAuthLoginPopupUrlForMobileRequest request = new TradeItOAuthLoginPopupUrlForMobileRequest(
            apiKey,
            broker,
            deepLinkCallback
        );
        tradeItApi.getOAuthLoginPopupUrlForMobile(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForMobileResponse, String>(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForMobileResponse> response) {
                    callback.onSuccess(response.body().oAuthURL);
                }
            }
        );
    }

    public void getOAuthLoginPopupUrlForWebApp(String apiKey, String broker, final TradeItCallback<String> callback) {
        TradeItOAuthLoginPopupUrlForWebAppRequest request = new TradeItOAuthLoginPopupUrlForWebAppRequest(
            apiKey,
            broker
        );
        tradeItApi.getOAuthLoginPopupUrlForWebApp(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItOAuthLoginPopupUrlForWebAppResponse, String>(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForWebAppResponse> response) {
                    callback.onSuccess(response.body().oAuthURL);
                }
            }
        );
    }

    public void getOAuthLoginPopupUrlForTokenUpdate(
        String apiKey,
        String broker,
        String userId,
        String userToken,
        String deepLinkCallback,
        final TradeItCallback<String> callback
    ) {
        TradeItOAuthLoginPopupUrlForTokenUpdateRequest request = new TradeItOAuthLoginPopupUrlForTokenUpdateRequest(
            apiKey,
            broker,
            deepLinkCallback,
            userId,
            userToken
        );
        tradeItApi.getOAuthLoginPopupURLForTokenUpdate(request).enqueue(
            new DefaultCallbackWithErrorHandling<
                TradeItOAuthLoginPopupUrlForTokenUpdateResponse,
                String
                >(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItOAuthLoginPopupUrlForTokenUpdateResponse> response) {
                    callback.onSuccess(response.body().oAuthURL);
                }
            }
        );
    }

    public void linkBrokerWithOauthVerifier(String apiKey, String oAuthVerifier, final TradeItCallback<TradeItLinkedLogin> callback) {
        final TradeItOAuthAccessTokenRequest request = new TradeItOAuthAccessTokenRequest(apiKey, oAuthVerifier);
        tradeItApi.getOAuthAccessToken(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItOAuthAccessTokenResponse, TradeItLinkedLogin>(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItOAuthAccessTokenResponse> response) {
                    TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin(request, response.body());
                    callback.onSuccess(linkedLogin);
                }
            }
        );
    }

    public void unlinkBrokerAccount(String apiKey, TradeItLinkedLogin linkedLogin, final TradeItCallback callback) {
        TradeItUnlinkLoginRequest request = new TradeItUnlinkLoginRequest(apiKey, linkedLogin);
        tradeItApi.unlinkLogin(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItResponse> response) {
                    callback.onSuccess(response.body());
                }
            }
        );
    }

    public void authenticate(
        String apiKey,
        TradeItLinkedLogin linkedLogin,
        final AuthenticationCallback<TradeItAuthenticateResponse, ?> callback
    ) {
        TradeItAuthenticateRequest authenticateRequest = new TradeItAuthenticateRequest(apiKey, linkedLogin);

        tradeItApi.authenticate(authenticateRequest).enqueue(new Callback<TradeItAuthenticateResponse>() {
            public void onResponse(
                Call<TradeItAuthenticateResponse> call,
                Response<TradeItAuthenticateResponse> response
            ) {
                callback.onResponse(call, response);
            }

            public void onFailure(Call<TradeItAuthenticateResponse> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void answerSecurityQuestion(TradeItAnswerSecurityQuestionRequest request, final Callback<TradeItAuthenticateResponse> callback) {
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
        request.sessionToken = sessionToken;
        tradeItApi.keepSessionAlive(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void closeSession(String sessionToken, final TradeItCallback<TradeItResponse> callback) {
        TradeItRequestWithSession request = new TradeItRequestWithSession();
        request.sessionToken = sessionToken;
        tradeItApi.closeSession(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItResponse, TradeItResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void previewStockOrEtfOrder(TradeItPreviewStockOrEtfOrderRequest request, final TradeItCallback<TradeItPreviewStockOrEtfOrderResponse> callback) {
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

    public void placeStockOrEtfOrder(TradeItPlaceStockOrEtfOrderRequest request, final TradeItCallback<TradeItPlaceStockOrEtfOrderResponse> callback) {
        tradeItApi.placeStockOrEtfOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItPlaceStockOrEtfOrderResponse, TradeItPlaceStockOrEtfOrderResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItPlaceStockOrEtfOrderResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getAccountOverview(TradeItGetAccountOverviewRequest request, final TradeItCallback<TradeItAccountOverviewResponse> callback) {
        tradeItApi.getAccountOverview(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItAccountOverviewResponse, TradeItAccountOverviewResponse>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItAccountOverviewResponse> response) {
                callback.onSuccess(response.body());
            }
        });
    }

    public void getPositions(TradeItGetPositionsRequest request, final TradeItCallback<List<TradeItPosition>> callback) {
        tradeItApi.getPositions(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetPositionsResponse, List<TradeItPosition>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetPositionsResponse> response) {
                callback.onSuccess(response.body().positions);
            }
        });
    }

    public void getAllOrderStatus(TradeItGetAllOrderStatusRequest request, final TradeItCallback<List<OrderStatusDetails>> callback) {
        tradeItApi.getAllOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, List<OrderStatusDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                callback.onSuccess(response.body().orderStatusDetailsList);
            }
        });
    }

    public void getSingleOrderStatus(TradeItGetSingleOrderStatusRequest request, final TradeItCallback<OrderStatusDetails> callback) {
        tradeItApi.getSingleOrderStatus(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void cancelOrder(TradeItCancelOrderRequest request, final TradeItCallback<OrderStatusDetails> callback) {
        tradeItApi.cancelOrder(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItOrderStatusResponse, OrderStatusDetails>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItOrderStatusResponse> response) {
                List<OrderStatusDetails> list = response.body().orderStatusDetailsList;
                OrderStatusDetails orderStatusDetails = list.isEmpty() ? null : list.get(0);
                callback.onSuccess(orderStatusDetails);
            }
        });
    }

    public void getAllTransactionsHistory(TradeItGetAllTransactionsHistoryRequest request, final TradeItCallback<List<TransactionDetails>> callback) {
        tradeItApi.getAllTransactionsHistory(request).enqueue(new DefaultCallbackWithErrorHandling<TradeItGetAllTransactionsHistoryResponse, List<TransactionDetails>>(callback) {
            @Override
            public void onSuccessResponse(Response<TradeItGetAllTransactionsHistoryResponse> response) {
                callback.onSuccess(response.body().transactionHistoryDetailsList);
            }
        });
    }

    public void previewCryptoOrder(
        TradeItPreviewCryptoOrderRequest request,
        final TradeItCallback<TradeItPreviewCryptoOrderResponse> callback
    ) {
        tradeItApi.previewCryptoOrder(request)
            .enqueue(
                new PreviewTradeCallback<TradeItPreviewCryptoOrderResponse, TradeItPreviewCryptoOrderResponse>(
                    callback
                ) {

                    @Override
                    public void onSuccessResponse(Response<TradeItPreviewCryptoOrderResponse> response) {
                        callback.onSuccess(response.body());
                    }

                    @Override
                    public void onErrorResponse(TradeItErrorResult errorResult) {
                        callback.onError(errorResult);
                    }
                }
            );
    }

    public void placeCryptoOrder(
        TradeItPlaceCryptoOrderRequest request,
        final TradeItCallback<TradeItPlaceCryptoOrderResponse> callback
    ) {
        tradeItApi.placeCryptoOrder(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItPlaceCryptoOrderResponse, TradeItPlaceCryptoOrderResponse>(
                callback
            ) {
                @Override
                public void onSuccessResponse(Response<TradeItPlaceCryptoOrderResponse> response) {
                    callback.onSuccess(response.body());
                }
            }
        );
    }

    public void getCryptoQuote(
        final TradeItCryptoQuoteRequest request,
        final TradeItCallback<TradeItCryptoQuoteResponse> callback
    ) {
        tradeItApi.getCryptoQuote(request).enqueue(
            new DefaultCallbackWithErrorHandling<TradeItCryptoQuoteResponse, TradeItCryptoQuoteResponse>(callback) {
                @Override
                public void onSuccessResponse(Response<TradeItCryptoQuoteResponse> response) {
                    callback.onSuccess(response.body());
                }
            }
        );
    }
}
