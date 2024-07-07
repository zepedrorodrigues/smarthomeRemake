package smarthome.ddd;

import java.util.Optional;

public abstract interface IRepository<ID extends DomainId, T extends AggregateRoot<ID>> {
  
  T save(T entity);

  Iterable<T> findAll();

  Optional<T> findByIdentity(ID id);

  boolean containsIdentity(ID id);
}