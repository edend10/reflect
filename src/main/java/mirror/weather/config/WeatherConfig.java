package mirror.weather.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import mirror.weather.client.darksky.DarkSkyWeatherClientImpl;
import mirror.weather.client.generic.WeatherClient;
import mirror.weather.model.generic.ApiWeatherResponse;
import mirror.weather.rest.WeatherStompController;
import mirror.weather.service.WeatherService;
import mirror.weather.service.darksky.DarkSkyWeatherResponseConverterImpl;
import mirror.weather.service.generic.WeatherResponseConverter;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import mirror.weather.rest.WeatherController;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootConfiguration
@PropertySource("classpath:reflect.properties")
@PropertySource("classpath:reflect-key.properties")
public class WeatherConfig {
    @Value("${modules.timezone}")
    private String timezone;

    @Value("${modules.weather.latlong}")
    private String latLong;

    @Value("${weather.apikey}")
    private String weatherApiKey;

    @Bean
    public WeatherController weatherController(WeatherService weatherService) {
        return new WeatherController(weatherService);
    }

    @Bean
    public WeatherService weatherService(WeatherClient weatherClient,
                                         WeatherResponseConverter weatherResponseConverter) {
        return new WeatherService(weatherClient, weatherResponseConverter, latLong);
    }

    @Bean
    public WeatherClient weatherClient(RestTemplate restTemplate) {
        return new DarkSkyWeatherClientImpl(restTemplate, weatherApiKey);
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (certificate, authType) -> true).build();

        CloseableHttpClient client = HttpClients.custom()
                .setSslcontext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(client);

        return new RestTemplate(requestFactory);
    }

    @Bean
    public WeatherResponseConverter weatherResponseConverter() {
        return new DarkSkyWeatherResponseConverterImpl(timezone);
    }
}
