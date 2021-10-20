package edu.kit.tm.cm.serviceenvironment.api.api;

import edu.kit.tm.cm.serviceenvironment.api.api.DTOMapper.DTOMapper;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.NotFoundException;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitAPI;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;
import edu.kit.tm.cm.serviceenvironment.api.logic.operations.APISpecificationOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class APISpecificationController implements APISpecificationApi {
    private static final Logger log = LoggerFactory.getLogger(APISpecificationController.class);

    @Autowired
    private APISpecificationOperation apiSpecificationOperation;

    @Override
    public void submitAPI(@Valid SubmitAPI submitAPI) {
        log.info("POST /api-specification request=" + submitAPI.toString());
        try {
            API api = DTOMapper.convertToAPI(submitAPI);
            Release release = DTOMapper.convertToRelease(submitAPI.getSubmitRelease());
            apiSpecificationOperation.submitAPI(api, release);
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteAPI(String slug) {
        try {
            apiSpecificationOperation.deleteReleaseBySlug(slug);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
