package edu.kit.tm.cm.serviceenvironment.api.api.DTOMapper;

import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitAPI;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitRelease;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.APIResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.ReleaseResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DTOMapper {


    public static APIResponseDTO convertToAPIResponseDTO(API api, Collection<Release> releases) {
        APIResponseDTO apiResponseDTO = new APIResponseDTO();

        apiResponseDTO.setId(api.getId());
        apiResponseDTO.setDescription(api.getDescription());
        apiResponseDTO.setName(api.getName());
        apiResponseDTO.setRepositoryLink(api.getRepositoryLink());
        Map<String, String> releaseMap = new HashMap<>();
        for (Release elem: releases) {
            releaseMap.put(elem.getId(), elem.getSlug());
        }
        apiResponseDTO.setReleases(releaseMap);

        return apiResponseDTO;
    }


    public static ReleaseResponseDTO convertToReleaseResponseDTO(Release release) {
        ReleaseResponseDTO releaseResponseDTO = new ReleaseResponseDTO();
        releaseResponseDTO.setPublished(release.getPublished());
        releaseResponseDTO.setApiSpecification(release.getApiSpecification());
        releaseResponseDTO.setSlug(release.getSlug());
        releaseResponseDTO.setStatus(release.getStatus());
        releaseResponseDTO.setId(release.getId());
        releaseResponseDTO.setCommitSha(release.getCommitSha());
        return releaseResponseDTO;
    }


    public static Release convertToRelease(SubmitRelease submitRelease) throws InvalidArgumentException {
        Release release = new Release();
        release.setApiSpecification(submitRelease.getApiSpecification());
        release.setSlug(submitRelease.getSlug());
        release.setCommitSha(submitRelease.getCommitSha());
        return release;
    }

    public static API convertToAPI(SubmitAPI submitAPI) {
        API api = new API();
        api.setRepositoryLink(submitAPI.getRepositoryLink());
        return api;
    }
}
