package mirror.bathroom.model.lifx;

import mirror.bathroom.model.generic.ApiLightBulbResponse;

import java.util.List;

public class LifxLightBulbResponse implements ApiLightBulbResponse {
    List<LifxBreatheResult> results;

    public List<LifxBreatheResult> getResults() {
        return results;
    }

    public void setResults(List<LifxBreatheResult> results) {
        this.results = results;
    }
}
