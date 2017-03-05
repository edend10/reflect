package mirror.bathroom.model.lifx;

import java.util.List;

public class LifxBreatheResponse {
    List<LifxBreatheResult> results;

    public List<LifxBreatheResult> getResults() {
        return results;
    }

    public void setResults(List<LifxBreatheResult> results) {
        this.results = results;
    }
}
