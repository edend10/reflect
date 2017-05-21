package mirror.bathroom.service.sitting;

import mirror.bathroom.dao.BathroomSittingDto;

import java.util.List;

public class BathroomSittingStatsResponse {

    private List<BathroomSittingDto> sittings;

    public BathroomSittingStatsResponse(List<BathroomSittingDto> sittings) {
        this.sittings = sittings;
    }

    public List<BathroomSittingDto> getSittings() {
        return sittings;
    }

    public void setSittings(List<BathroomSittingDto> sittings) {
        this.sittings = sittings;
    }
}
