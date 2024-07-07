package smarthome.mapper;

/**
 * This class represents a DTO for a period of time.
 */
public class PeriodDTO {

    private final String start;
    private final String end;

    /**
     * Constructs an instance of PeriodDTO with the given start and end.
     *
     * @param start the start of the period.
     * @param end the end of the period.
     */
    public PeriodDTO(String start, String end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the start of the period.
     *
     * @return the start of the period.
     */
    public String getStart() {
        return start;
    }

    /**
     * Gets the end of the period.
     *
     * @return the end of the period.
     */
    public String getEnd() {
        return end;
    }
}
