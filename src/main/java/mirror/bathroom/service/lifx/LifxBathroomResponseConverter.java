package mirror.bathroom.service.lifx;

import mirror.bathroom.model.lifx.LifxLightBulbResponse;
import mirror.bathroom.model.response.BathroomResponse;
import mirror.bathroom.service.generic.BathroomResponseConverter;

public class LifxBathroomResponseConverter implements BathroomResponseConverter<LifxLightBulbResponse> {
    @Override
    public BathroomResponse convertResponse(LifxLightBulbResponse lifxResponse) {
        try {
            String state = lifxResponse.getResults().get(0).getStatus().equals("ok") ? "occupied" : "vacant";
            return new BathroomResponse(state);
        } catch (Exception e) {
            return new BathroomResponse("NA");
        }
    }
}
