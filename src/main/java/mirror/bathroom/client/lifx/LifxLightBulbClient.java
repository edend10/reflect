package mirror.bathroom.client.lifx;

import mirror.bathroom.client.generic.LightBulbClient;
import mirror.bathroom.model.lifx.LifxLightBulbResponse;
import mirror.bathroom.model.lifx.LifxLightBulbResponseOld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LifxLightBulbClient implements LightBulbClient {

    private static final String LIFX_ENDPOINT = "https://api.lifx.com/v1/lights/all";
    private static final Logger LOGGER = LoggerFactory.getLogger(LifxLightBulbClient.class);
    private static final String AUTH_HEADER_NAME = "Authorization";
    private static final String AUTH_HEADER_VAL_PREFIX = "Bearer";
    private static final String BREATHE_ENDPOINT_SUFFIX = "effects/breathe";
    private static final String LIFX_RESPONSE_STATUS_OK = "ok";
    private static final String PERIOD_PARAM_NAME = "period";
    private static final String CYCLES_PARAM_NAME = "cycles";
    private static final String COLOR_PARAM_NAME = "color";
    private static final String COLOR_PARAM_RED = "red";
    private static final int CYCLE_PARAM_ONE = 1;
    private static final int PERIOD_PARAM_ONE = 1;

    private final RestTemplate restTemplate;
    private String apiKey;
    private HttpHeaders headers;

    public LifxLightBulbClient(RestTemplate restTemplate, String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    private LifxLightBulbResponseOld getLightStateOld() {
        String url = LIFX_ENDPOINT;
        HttpHeaders headers = createHeaders();
        HttpEntity<String> params = new HttpEntity<>(headers);

        try {
            ResponseEntity<LifxLightBulbResponseOld[]> jsonResponse = restTemplate.exchange(url, HttpMethod.GET, params, LifxLightBulbResponseOld[].class);
            if (jsonResponse.getStatusCode() == HttpStatus.OK) {
                return jsonResponse.getBody()[0];
            } else {
                LOGGER.warn("Lifx http call failed with status {}: {}",
                        jsonResponse.getStatusCode().value(), jsonResponse.getStatusCode().getReasonPhrase());
                return new LifxLightBulbResponseOld();
            }
        } catch (Exception e) {
            LOGGER.warn("Lifx http call failed:", e);
            return new LifxLightBulbResponseOld();
        }
    }

    @Override
    public LifxLightBulbResponse getLightState() {
        String url = String.format("%s/%s", LIFX_ENDPOINT, "state");
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("power", "on");
        HttpEntity<?> params = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<LifxLightBulbResponse> response = restTemplate.exchange(url, HttpMethod.PUT, params, LifxLightBulbResponse.class);
            if (response.getStatusCode().equals(HttpStatus.OK) || response.getStatusCode().equals(HttpStatus.MULTI_STATUS)) {
                return response.getBody();
            } else {
                LOGGER.warn("Lifx http call failed with status {}: {}",
                        response.getStatusCode().value(), response.getStatusCode().getReasonPhrase());
                return new LifxLightBulbResponse();
            }
        } catch (Exception e) {
            LOGGER.warn("Lifx http call failed:", e);
            return new LifxLightBulbResponse();
        }
    }

    @Override
    public boolean breatheEffectRed() {
        String url = String.format("%s/%s", LIFX_ENDPOINT, BREATHE_ENDPOINT_SUFFIX);
        HttpHeaders headers = createHeaders();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(PERIOD_PARAM_NAME, PERIOD_PARAM_ONE);
        body.add(CYCLES_PARAM_NAME, CYCLE_PARAM_ONE);
        body.add(COLOR_PARAM_NAME, COLOR_PARAM_RED);
        HttpEntity<?> params = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<LifxLightBulbResponse> response = restTemplate.exchange(url, HttpMethod.POST, params, LifxLightBulbResponse.class);
            if (response.getStatusCode().equals(HttpStatus.OK) || response.getStatusCode().equals(HttpStatus.MULTI_STATUS)) {
                LifxLightBulbResponse breatheResponse = response.getBody();
                if (!breatheResponse.getResults().isEmpty() && breatheResponse.getResults().get(0).getStatus().equals(LIFX_RESPONSE_STATUS_OK)) {
                    return true;
                } else {
                    LOGGER.warn("Lifx http call succeeded but effect returned with status: {}", breatheResponse.getResults().get(0).getStatus());
                    return false;
                }
            } else {
                LOGGER.warn("Lifx http call failed with status {}: {}",
                        response.getStatusCode().value(), response.getStatusCode().getReasonPhrase());
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("Lifx http call failed:", e);
            return false;
        }
    }


    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER_NAME, String.format("%s %s", AUTH_HEADER_VAL_PREFIX, apiKey));
        return headers;
    }
}
