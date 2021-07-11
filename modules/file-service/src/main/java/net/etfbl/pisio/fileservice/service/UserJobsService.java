package net.etfbl.pisio.fileservice.service;

import lombok.AllArgsConstructor;
import net.etfbl.pisio.fileservice.model.UserJobs;
import net.etfbl.pisio.fileservice.repository.UserJobsRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserJobsService {

    private final UserJobsRepository userJobsRepository;

    public UserJobs getUserJobsByUsername(String username) {
        return userJobsRepository.findById(username).orElse(null);
    }

    public void persistUserJobs(UserJobs userJobs) {
        userJobsRepository.save(userJobs);
    }
}
