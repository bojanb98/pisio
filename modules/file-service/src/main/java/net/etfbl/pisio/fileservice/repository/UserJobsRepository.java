package net.etfbl.pisio.fileservice.repository;

import net.etfbl.pisio.fileservice.model.UserJobs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobsRepository extends CrudRepository<UserJobs, String> {
}
