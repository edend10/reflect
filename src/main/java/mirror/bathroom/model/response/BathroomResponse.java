package mirror.bathroom.model.response;

public class BathroomResponse {
    private String state;

    public BathroomResponse(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
