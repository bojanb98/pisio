package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.UserJob;
import net.etfbl.pisio.fileservice.repository.UserJobRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserJobService {

    private final UserJobRepository userJobRepository;

    public UserJob getByJobId(String jobId) {
        return userJobRepository.findById(jobId).orElse(null);
    }

    public void persistUserJobs(UserJob userJob) {
        userJobRepository.save(userJob);
    }
}
