package smarthome.domain.actuator.vo;

import smarthome.ddd.ValueObject;

import java.util.Objects;

/**
 * LoadState is a value object that represents the state of a load in the smart home system.
 * It encapsulates a boolean value that indicates whether the load is turned on.
 */
public class LoadState implements ValueObject {
    private boolean isLoadTurnedOn = false;

    /**
     * Constructs a new LoadState with the specified state.
     *
     * @param isLoadTurnedOn the state of the load, where true indicates that the load is turned on
     */
    public LoadState(boolean isLoadTurnedOn) {
        this.isLoadTurnedOn = isLoadTurnedOn;
    }

    /**
     * Returns the state of the load.
     *
     * @return true if the load is turned on, false otherwise
     */
    public boolean getLoadState() {

        return isLoadTurnedOn;
    }

    /**
     * Compares this LoadState to the specified object.
     * The result is true if and only if the argument is not null and is a LoadState object that represents the same
     * load state as this object.
     *
     * @param o the object to compare this LoadState against
     * @return true if the given object represents a LoadState equivalent to this LoadState, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoadState loadState = (LoadState) o;
        return isLoadTurnedOn == loadState.isLoadTurnedOn;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as
     * those provided by HashMap.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(isLoadTurnedOn);
    }
}