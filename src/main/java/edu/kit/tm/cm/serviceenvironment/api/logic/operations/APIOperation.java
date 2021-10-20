package edu.kit.tm.cm.serviceenvironment.api.logic.operations;

import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InternalServerErrorException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.NotFoundException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.APIRepository;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class APIOperation {

    private final APIRepository repository;
    private final ArrayList<String> apiFields;
    private final ArrayList<String> releaseFields;
    private final String ACTIVE = "ACTIVE";
    private final String DEPRECATED = "DEPRECATED";

    @Autowired
    public APIOperation(APIRepository repository) {
        this.repository = repository;
        apiFields = new ArrayList<>();
        apiFields.add("name");
        apiFields.add("description");
        releaseFields = new ArrayList<>();
        releaseFields.add("status");
    }

    public Collection<API> getAllAPIs() {
        return repository.findALl();
    }

    public Collection<Release> getAllReleasesOfAPI(String id) throws InvalidArgumentException {
        return repository.findAllReleasesOfAPI(id);
    }

    public void deleteAPI(String id) throws InternalServerErrorException {
        boolean success = repository.deleteApiById(id);
        if (!success)
            throw new InternalServerErrorException();
    }

    public void deleteRelease(String apiId, String releaseId) throws InternalServerErrorException {
        boolean success = repository.deleteRelease(apiId, releaseId);
        if (!success)
            throw new InternalServerErrorException();
    }

    public void updateAPI(String id, Map<String, Object> fields) throws NotFoundException, InvalidArgumentException {

        Optional<API> optionalAPI = repository.findById(id);
        API api = optionalAPI.orElseThrow(() -> new NotFoundException());

        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!apiFields.contains(key)) {
                throw new InvalidArgumentException();
            }
            Field field = ReflectionUtils.findField(API.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, api, value);
        }

        repository.updateAPI(api);

    }

    public void updateRelease(String apiId, String releaseId, Map<String, Object> fields)
            throws NotFoundException, InvalidArgumentException {

        Optional<API> optionalAPI = repository.findById(apiId);
        API api = optionalAPI.orElseThrow(() -> new NotFoundException());
        Optional<Release> optionalRelease = repository.findReleaseById(releaseId, apiId);
        Release release = optionalRelease.orElseThrow(() -> new NotFoundException());
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!releaseFields.contains(key)) {
                throw new InvalidArgumentException();
            }
            Field field = ReflectionUtils.findField(Release.class, key);
            field.setAccessible(true);
            if (value.equals(ACTIVE)) {
                ReflectionUtils.setField(field, release, Status.ACTIVE);
            } else if (value.equals(DEPRECATED)) {
                ReflectionUtils.setField(field, release, Status.DEPRECATED);
            } else {
                throw new InvalidArgumentException();
            }
        }
        repository.updateRelease(release, api);
    }

    public Release getReleaseById(String apiId, String releaseId)
            throws NotFoundException, InvalidArgumentException {
        Optional<Release> optionalRelease = repository.findReleaseById(releaseId, apiId);
        Release release = optionalRelease.orElseThrow(() -> new NotFoundException());

        return release;

    }
}
