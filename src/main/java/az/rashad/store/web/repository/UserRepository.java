package az.rashad.store.web.repository;

import az.rashad.store.web.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

}
