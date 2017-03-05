package mirror.bathroom.client.generic;

import mirror.bathroom.model.generic.ApiLightBulbResponse;

public interface LightBulbClient {
    public ApiLightBulbResponse getLightState();

    boolean breatheEffectRed();
}
