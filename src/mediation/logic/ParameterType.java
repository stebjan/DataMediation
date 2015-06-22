package mediation.logic;

/**
 * Created by Honza on 17.6.15.
 */
public enum ParameterType {

    REQUEST("Request"),
    RESPONSE("Response");

    private final String type;

    ParameterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
