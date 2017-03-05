package mirror.bathroom.service.lifx;

import mirror.bathroom.model.lifx.LifxLightBulbResponse;
import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.generic.BathroomResponseConverter;

public class LifxBathroomResponseConverter implements BathroomResponseConverter<LifxLightBulbResponse> {
    @Override
    public BathroomResponse convertResponse(LifxLightBulbResponse lifxResponse) {
        String state = lifxResponse.getConnected().equals("true") ? "occupied" : "vacant";
        return new BathroomResponse(state);
    }
}
