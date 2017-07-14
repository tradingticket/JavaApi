package it.trade.api;

import it.trade.model.TradeItErrorResult;
import it.trade.model.TradeItSecurityQuestion;
import it.trade.model.callback.AuthenticationCallback;
import it.trade.model.callback.TradeItCallback;
import it.trade.model.callback.TradeItCallbackWithSecurityQuestion;
import it.trade.model.callback.TradeItCallbackWithSecurityQuestionImpl;
import it.trade.model.reponse.*;
import it.trade.model.request.TradeItEnvironment;
import it.trade.model.request.TradeItLinkedLogin;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Integration tests
 */
public class TradeItApiClientTest {

    private TradeItApiClient apiClient;
    private CountDownLatch lock = new CountDownLatch(1);
    private static long EXPIRED_TIME = 10000l;
    private static final String QA_DUMMY_USER_ID = "fd11493156211607134a";
    private static final String QA_DUMMY_USER_TOKEN = "qvZT4GLQlKbWQAfHCxN71EwGZOpBFQSuO6WGjFPNn7zovNm0kMjqGv%2FQa9pqj2HRkgAAY0SPSuGdjjN0T8RJNFiGsS2p8CBxX6Y3jGZOUGNb%2F%2F5IlAsqTdc%2B%2F8iFyz5TeiEOVrNatFXanrLUG8JE7A%3D%3D";
    private static final String QA_DUMMY_SECURITY_USER_ID = "f3814932200965237cbf";
    private static final String QA_DUMMY_SECURITY_USER_TOKEN = "lFQes6DSPU4NzzGjMIzNeRLXZ%2FwKHW0hX%2BaNH8Rpna%2F68TX4ygkSPuv38UFfCCeVYuEuH62RczwvC%2Bsrx%2BWVgEqBQmjVHXPMLniZxfOFCrKq%2F8ks2szgaz4qXHXXLKzA8MitqrCeL%2B8lpSLOXvCsWA%3D%3D";


    @Before
    public void setUp() throws Exception {
        apiClient = new TradeItApiClient("tradeit-test-api-key", TradeItEnvironment.QA);
    }

    @Test
    public void getAvailableBrokers() throws Exception {
        apiClient.getAvailableBrokers(new TradeItCallback<List<TradeItAvailableBrokersResponse.Broker>>() {
            @Override
            public void onSuccess(List<TradeItAvailableBrokersResponse.Broker> brokerList) {
                lock.countDown();

                assertThat("The broker list is not empty", brokerList.isEmpty(), CoreMatchers.is(false));

                TradeItAvailableBrokersResponse.Broker broker = brokerList.get(0);
                TradeItAvailableBrokersResponse.Broker.BrokerInstrument instrument = broker.brokerInstruments.get(0);
                assertTrue(instrument.instrument.length() > 0);
            }

            @Override
            public void onError(TradeItErrorResult error) {
                assertThat("fails to get the broker list", error, CoreMatchers.nullValue());
                lock.countDown();
            }
        });
        boolean notExpired = lock.await(EXPIRED_TIME, TimeUnit.MILLISECONDS);
        assertThat("The call to getAvailableBrokers is not expired", notExpired, CoreMatchers.is(true));
    }

    @Test
    public void getOAuthLoginPopupUrlForMobile() throws Exception {
        apiClient.getOAuthLoginPopupUrlForMobile("Dummy", "exampleapp://tradeit", new TradeItCallback<String>() {
            @Override
            public void onSuccess(String oAuthUrl) {
                assertThat("The oAuthUrl is returned", (oAuthUrl != null && !oAuthUrl.isEmpty()), CoreMatchers.is(true));
                lock.countDown();
            }

            @Override
            public void onError(TradeItErrorResult error) {
                assertThat("fails to get the broker list", error, CoreMatchers.nullValue());
                lock.countDown();
            }
        });
        boolean notExpired = lock.await(EXPIRED_TIME, TimeUnit.MILLISECONDS);
        assertThat("The call to getOAuthLoginPopupUrlForMobile is not expired", notExpired, CoreMatchers.is(true));
    }

    @Test
    public void getOAuthLoginPopupUrlForWebApp() throws Exception {
        apiClient.getOAuthLoginPopupUrlForWebApp("Dummy", new TradeItCallback<String>() {
            @Override
            public void onSuccess(String oAuthUrl) {
                assertThat("The oAuthUrl is returned", (oAuthUrl != null && !oAuthUrl.isEmpty()), CoreMatchers.is(true));
                lock.countDown();
            }

            @Override
            public void onError(TradeItErrorResult error) {
                assertThat("fails to get the broker list", error, CoreMatchers.nullValue());
                lock.countDown();
            }
        });
        boolean notExpired = lock.await(EXPIRED_TIME, TimeUnit.MILLISECONDS);
        assertThat("The call to getOAuthLoginPopupUrlForWebApp is not expired", notExpired, CoreMatchers.is(true));
    }

    @Test
    public void authenticateDummyWithExistingUserIdAndToken() throws Exception {
        TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin("Dummy", QA_DUMMY_USER_ID, QA_DUMMY_USER_TOKEN);
        apiClient.authenticate(linkedLogin, new AuthenticationCallback<TradeItAuthenticateResponse, List<TradeItBrokerAccount>>(null, apiClient) {
            @Override
            protected void onSuccessResponse(Response<TradeItAuthenticateResponse> response) {
                assertThat("accounts are returned", !response.body().accounts.isEmpty(), CoreMatchers.is(true));
                apiClient.getAccountOverview(response.body().accounts.get(0).accountNumber, new TradeItCallback<TradeItAccountOverviewResponse>() {
                    @Override
                    public void onSuccess(TradeItAccountOverviewResponse accountOverviewResponse) {
                        assertThat("available cash is not null", accountOverviewResponse.accountOverview.availableCash, CoreMatchers.notNullValue());
                        apiClient.keepSessionAlive(new TradeItCallback<TradeItResponse>() {
                            @Override
                            public void onSuccess(TradeItResponse response) {
                                assertThat("keepSessionAlive returns a successful response", response.isSuccessful(), CoreMatchers.is(true));
                                lock.countDown();
                            }

                            @Override
                            public void onError(TradeItErrorResult error) {
                                assertThat("fails to keepSessionAlive", error, CoreMatchers.nullValue());
                                lock.countDown();
                            }
                        });
                    }

                    @Override
                    public void onError(TradeItErrorResult error) {
                        assertThat("fails to getAccountOverview", error, CoreMatchers.nullValue());
                        lock.countDown();
                    }
                });
            }

            @Override
            protected void onErrorResponse(TradeItErrorResult errorResult) {
                assertThat("fails to authenticate", errorResult, CoreMatchers.nullValue());
                lock.countDown();
            }
        });
        boolean notExpired = lock.await(EXPIRED_TIME, TimeUnit.MILLISECONDS);
        assertThat("The call to authenticate is not expired", notExpired, CoreMatchers.is(true));
    }

    @Test
    public void authenticateDummySecurityWithExistingUserIdAndToken() throws Exception {
        TradeItLinkedLogin linkedLogin = new TradeItLinkedLogin("Dummy", QA_DUMMY_SECURITY_USER_ID, QA_DUMMY_SECURITY_USER_TOKEN);
        final TradeItCallbackWithSecurityQuestion securityQuestion = new TradeItCallbackWithSecurityQuestionImpl<List<TradeItBrokerAccount>>() {
            @Override
            public void onSuccess(List<TradeItBrokerAccount> accounts) {
                assertThat("successful authentication after answering security question security question",  accounts, CoreMatchers.notNullValue());
                lock.countDown();
            }

            @Override
            public void onSecurityQuestion(TradeItSecurityQuestion securityQuestion) {
                assertThat("security question is not null",  securityQuestion, CoreMatchers.notNullValue());
                this.submitSecurityAnswer("tradingticket");
            }

            @Override
            public void onError(TradeItErrorResult error) {
                assertThat("fails to authenticate with security question", error, CoreMatchers.nullValue());
                lock.countDown();
            }
        };

        apiClient.authenticate(linkedLogin, new AuthenticationCallback<TradeItAuthenticateResponse, List<TradeItBrokerAccount>>(securityQuestion, apiClient) {
            @Override
            protected void onSuccessResponse(Response<TradeItAuthenticateResponse> response) {
                securityQuestion.onSuccess(response.body().accounts);
            }

            @Override
            protected void onErrorResponse(TradeItErrorResult errorResult) {
                securityQuestion.onError(errorResult);
            }
        });

        boolean notExpired = lock.await(EXPIRED_TIME, TimeUnit.MILLISECONDS);
        assertThat("The call to authenticateDummySecurityWithExistingUserIdAndToken is not expired", notExpired, CoreMatchers.is(true));
    }
}