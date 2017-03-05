package mirror.bathroom.service.generic;

import mirror.bathroom.model.response.BathroomResponse;

public interface BathroomResponseConverter<T> {
    BathroomResponse convertResponse(T lightState);
}
