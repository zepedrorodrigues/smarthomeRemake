package smarthome.ddd;

public interface DomainEntity<ID extends DomainId> {
	ID getIdentity();
}
