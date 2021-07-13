package net.etfbl.pisio.communicationservice;

import lombok.AllArgsConstructor;
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
