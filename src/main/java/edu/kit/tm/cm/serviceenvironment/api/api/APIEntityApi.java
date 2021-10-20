package edu.kit.tm.cm.serviceenvironment.api.api;

import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.APIResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.ReleaseResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RequestMapping(path = "v1/api")
public interface APIEntityApi {

    @GetMapping
    Collection<APIResponseDTO> getAPIs();

    @DeleteMapping("/{id}")
    void deleteAPI(@PathVariable String id);

    @PatchMapping("/{id}")
    void updateAPI(@PathVariable String id, @RequestBody Map<String, Object> fields);

    @GetMapping("/{id}/release")
    Collection<ReleaseResponseDTO> getAllReleasesOfApi(@PathVariable String id);

    @GetMapping("/{apiId}/release/{releaseId}")
    ReleaseResponseDTO getRelease(@PathVariable String apiId, @PathVariable String releaseId);

    @DeleteMapping("/{apiId}/release/{releaseId}")
    void deleteRelease(@PathVariable String apiId, @PathVariable String releaseId);

    @PatchMapping("/{apiId}/release/{releaseId}")
    void updateRelease(@PathVariable String apiId, @PathVariable String releaseId,
            @RequestBody Map<String, Object> fields);

}
