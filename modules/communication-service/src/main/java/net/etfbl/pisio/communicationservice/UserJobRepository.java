package net.etfbl.pisio.communicationservice;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends CrudRepository<UserJob, String> {
}
