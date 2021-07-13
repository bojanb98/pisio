package net.etfbl.pisio.fileservice.repository;

import net.etfbl.pisio.fileservice.model.UserJob;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends CrudRepository<UserJob, String> {
}
