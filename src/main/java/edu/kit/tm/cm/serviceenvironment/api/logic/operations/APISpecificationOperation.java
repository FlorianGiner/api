package edu.kit.tm.cm.serviceenvironment.api.logic.operations;

import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.NotFoundException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.APIRepository;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class APISpecificationOperation {

    private final APIRepository repository;

    @Autowired
    public APISpecificationOperation(APIRepository repository) {
        this.repository = repository;
    }

    public void submitAPI(API api, Release release) throws InvalidArgumentException {
        Optional<API> optionalAPIS = repository.findAPIByLink(api.getRepositoryLink());
        if (optionalAPIS.isEmpty()) {
            repository.createAPI(api);
            api = repository.findAPIByLink(api.getRepositoryLink()).get();
        } else {
            api = optionalAPIS.get();
        }
        repository.createRelease(release, api);
    }

    public void deleteReleaseBySlug(String slug) throws NotFoundException {
        boolean success = repository.deleteReleaseBySlug(slug);
        if (!success)
            throw new NotFoundException();
    }
}
