

public class DeltaDTO {


    private Double delta;

    public DeltaDTO(Double delta) {
        if (delta == null || delta <= 0) {
            throw new IllegalArgumentException();
        }
        this.delta = delta;
    }

    public Double getDelta() {
        return delta;
    }
}
