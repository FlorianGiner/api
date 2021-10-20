package edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.DatabaseMapper;

import edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities.*;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.*;

public class APIMapper {

    public static API convertToAPI(APIPersistenceEntity apiPersistenceEntity) {
        API api = new API();

        api.setDescription(apiPersistenceEntity.getDescription());
        api.setId(apiPersistenceEntity.getId());
        api.setName(apiPersistenceEntity.getName());
        api.setRepositoryLink(apiPersistenceEntity.getRepositoryLink());

        return api;
    }


    public static Release convertToRelease(ReleasePersistenceEntity releasePersistenceEntity) throws InvalidArgumentException {
        Release release = new Release();
        release.setStatus(convertToStatus(releasePersistenceEntity.getStatus()));
        release.setId(releasePersistenceEntity.getId());
        release.setApiSpecification(convertToApiSpecification( releasePersistenceEntity.getSpecification()));
        release.setSlug(releasePersistenceEntity.getSlug());
        release.setPublished(releasePersistenceEntity.getPublished());
        release.setCommitSha(releasePersistenceEntity.getCommitSha());
        return release;

    }

    private static APISpecification convertToApiSpecification(APISpecificationPersistenceEntity specification) {
        APISpecification apiSpecification= new APISpecification();
        apiSpecification.setSpecification(specification.getSpecification());
        apiSpecification.setType(convertToSpecificationType(specification.getType()));
        return apiSpecification;
    }


    public static Status convertToStatus(StatusPersistenceEnumeration statusPersistenceEnumeration) {

        if (statusPersistenceEnumeration == StatusPersistenceEnumeration.ACTIVE) return Status.ACTIVE;

        return Status.DEPRECATED;
    }

    public static StatusPersistenceEnumeration convertToStatusPersistenceEnumeration(Status status) {

        if (status == Status.ACTIVE) return StatusPersistenceEnumeration.ACTIVE;

        return StatusPersistenceEnumeration.DEPRECATED;

    }


    public static SpecificationType convertToSpecificationType(SpecificationTypePersistenceEnumeration specificationTypePersistenceEnumeration) {

        if (specificationTypePersistenceEnumeration == SpecificationTypePersistenceEnumeration.OPENAPI) return SpecificationType.OPENAPI;

        return SpecificationType.GRPC;
    }

    public static SpecificationTypePersistenceEnumeration convertToSpecificationTypePersistenceEnumeration(SpecificationType specificationType) {
        if (specificationType == SpecificationType.GRPC) return SpecificationTypePersistenceEnumeration.GRPPC;

        return SpecificationTypePersistenceEnumeration.OPENAPI;
    }

    public static APIPersistenceEntity convertToAPIPersistenceEntity(API api) {
        APIPersistenceEntity apiPersistenceEntity = new APIPersistenceEntity();
        apiPersistenceEntity.setRepositoryLink(api.getRepositoryLink());
        apiPersistenceEntity.setName(api.getName());
        apiPersistenceEntity.setDescription(api.getDescription());
        apiPersistenceEntity.setId(api.getId());
        return apiPersistenceEntity;
    }

    public static ReleasePersistenceEntity convertToReleasePersistenceEntity(Release release, API api) {
        ReleasePersistenceEntity releasePersistenceEntity = new ReleasePersistenceEntity();

        releasePersistenceEntity.setApi(convertToAPIPersistenceEntity(api));
        releasePersistenceEntity.setPublished(release.getPublished());
        releasePersistenceEntity.setStatus(convertToStatusPersistenceEnumeration(release.getStatus()));
        releasePersistenceEntity.setSlug(release.getSlug());
        releasePersistenceEntity.setId(release.getId());
        releasePersistenceEntity.setSpecification(convertToAPISpecificationPersistenceEntity(release.getApiSpecification()));
        releasePersistenceEntity.setCommitSha(release.getCommitSha());
        return releasePersistenceEntity;
    }

    private static APISpecificationPersistenceEntity convertToAPISpecificationPersistenceEntity(APISpecification apiSpecification) {

        APISpecificationPersistenceEntity apiSpecificationPersistenceEntity = new APISpecificationPersistenceEntity();
        apiSpecificationPersistenceEntity.setSpecification(apiSpecification.getSpecification());
        apiSpecificationPersistenceEntity.setType(convertToSpecificationTypePersistenceEnumeration(apiSpecification.getType()));

        return apiSpecificationPersistenceEntity;
    }
}
