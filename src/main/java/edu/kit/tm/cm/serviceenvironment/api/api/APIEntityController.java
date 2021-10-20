package edu.kit.tm.cm.serviceenvironment.api.api;

import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.APIResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.ReleaseResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.api.DTOMapper.DTOMapper;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InternalServerErrorException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.NotFoundException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;
import edu.kit.tm.cm.serviceenvironment.api.logic.operations.APIOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class APIEntityController implements APIEntityApi {
    @Autowired
    private APIOperation apiOperation;

    @Override
    public Collection<APIResponseDTO> getAPIs() {
        Collection<API> apis =apiOperation.getAllAPIs();
        Collection<APIResponseDTO> responseDTOS = new ArrayList<>();
        for (API elem : apis) {
            try {
                Collection<Release> releases = apiOperation.getAllReleasesOfAPI(elem.getId());
                responseDTOS.add(DTOMapper.convertToAPIResponseDTO(elem, releases));
            } catch (InvalidArgumentException e) {
                APIResponseDTO resp = new APIResponseDTO();
                resp.setReleases(new HashMap<>());
                resp.setName(elem.getName());
                resp.setDescription(elem.getDescription());
                resp.setId(elem.getId());
                responseDTOS.add(resp);
            }
        }

        return responseDTOS;
    }

    @Override
    public Collection<ReleaseResponseDTO> getAllReleasesOfApi(String id) {
        try {
            return apiOperation.getAllReleasesOfAPI(id).stream().map(DTOMapper::convertToReleaseResponseDTO).collect(Collectors.toCollection(ArrayList::new));
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ReleaseResponseDTO getRelease(String apiId, String releaseId) {
        try {
            return DTOMapper.convertToReleaseResponseDTO(apiOperation.getReleaseById(apiId, releaseId));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteRelease(String apiId, String releaseId) {
        try {
            apiOperation.deleteRelease(apiId, releaseId);
        } catch (InternalServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteAPI(String id) {
        try {
            apiOperation.deleteAPI(id);
        } catch (InternalServerErrorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updateAPI(String id, Map<String, Object> fields) {

        try {
            apiOperation.updateAPI(id, fields);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public void updateRelease(String apiId, String releaseId, Map<String, Object> fields) {
        try {
            apiOperation.updateRelease(apiId, releaseId, fields);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InvalidArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
