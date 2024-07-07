package smarthome.persistence;

public enum PersistenceUnitName {

    PERSISTENCE_UNIT_NAME("smarthome");

    private final String persistenceUnitName;

    PersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }
}
