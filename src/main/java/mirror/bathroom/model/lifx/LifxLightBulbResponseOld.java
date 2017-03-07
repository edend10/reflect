package mirror.bathroom.model.lifx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mirror.bathroom.model.generic.ApiLightBulbResponse;

import java.util.List;

public class LifxLightBulbResponseOld implements ApiLightBulbResponse {
    private String connected;

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}
