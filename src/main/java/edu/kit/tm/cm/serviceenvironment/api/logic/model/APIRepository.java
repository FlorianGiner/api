package edu.kit.tm.cm.serviceenvironment.api.logic.model;

import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;

import java.util.Collection;
import java.util.Optional;

public interface APIRepository {

    Collection<API> findALl();

    Optional<API> findById(String id);

    Collection<Release> findAllReleasesOfAPI(String id) throws InvalidArgumentException;

    boolean deleteApiById(String id);

    boolean deleteRelease(String apiId, String releaseId);

    Optional<API> findAPIByLink(String repositoryLink);

    void createAPI(API api);

    void createRelease(Release release, API api);

    void updateAPI(API api);

    Optional<Release> findReleaseById(String releaseId, String apiId) throws InvalidArgumentException;

    void updateRelease(Release release, API api);

    boolean deleteReleaseBySlug(String slug);
}
