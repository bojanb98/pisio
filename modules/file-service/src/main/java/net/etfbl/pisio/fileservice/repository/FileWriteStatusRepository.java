package net.etfbl.pisio.fileservice.repository;

import net.etfbl.pisio.fileservice.model.FileWriteStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileWriteStatusRepository extends CrudRepository<FileWriteStatus, String> {
}
