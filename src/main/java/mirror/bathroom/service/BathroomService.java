package mirror.bathroom.service;

import mirror.bathroom.client.generic.LightBulbClient;
import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.generic.BathroomResponseConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BathroomService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BathroomService.class);
    private final LightBulbClient client;
    private final BathroomResponseConverter responseConverter;

    public BathroomService(LightBulbClient client, BathroomResponseConverter responseConverter) {
        this.client = client;
        this.responseConverter = responseConverter;
    }

    public BathroomResponse getBathroomState() {
        LOGGER.debug("Getting bathroom state");
        return responseConverter.convertResponse(client.getLightState());
    }

    public void signalUrgent() {
        LOGGER.info("Signalling urgent");
        client.breatheEffectRed();
    }
}
