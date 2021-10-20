package edu.kit.tm.cm.serviceenvironment.api.infrastructure.database;

import edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.DatabaseMapper.APIMapper;
import edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities.APIPersistenceEntity;
import edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities.ReleasePersistenceEntity;
import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.API;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.APIRepository;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Release;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
public class APIDatabaseRepository implements APIRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Collection<API> findALl() {
        TypedQuery<APIPersistenceEntity> query = entityManager.createQuery("SELECT a FROM APIPersistenceEntity a",
                APIPersistenceEntity.class);
        Collection<APIPersistenceEntity> apis = query.getResultList();
        Collection<API> result = new ArrayList<>();
        for (APIPersistenceEntity elem : apis) {
            result.add(APIMapper.convertToAPI(elem));
        }
        return result;
    }

    @Override
    public Optional<API> findById(String id) {
        APIPersistenceEntity apiPersistenceEntity = entityManager.find(APIPersistenceEntity.class, id);
        if (apiPersistenceEntity == null)
            return Optional.empty();
        API api = APIMapper.convertToAPI(apiPersistenceEntity);
        return Optional.of(api);
    }

    @Override
    public Collection<Release> findAllReleasesOfAPI(String id) throws InvalidArgumentException {
        Collection<ReleasePersistenceEntity> releases = getReleasesOfAPI(id);
        Collection<Release> result = new ArrayList<>();
        for (ReleasePersistenceEntity elem : releases) {
            result.add(APIMapper.convertToRelease(elem));
        }
        return result;
    }

    private Collection<ReleasePersistenceEntity> getReleasesOfAPI(String id) {
        String sqlQuery = "SELECT a From ReleasePersistenceEntity a WHERE api_id ='" + id + "'";
        TypedQuery<ReleasePersistenceEntity> query = entityManager.createQuery(sqlQuery,
                ReleasePersistenceEntity.class);
        Collection<ReleasePersistenceEntity> releases = query.getResultList();
        return releases;
    }

    @Override
    public boolean deleteApiById(String id) {
        APIPersistenceEntity apiPersistenceEntity = entityManager.find(APIPersistenceEntity.class, id);

        if (apiPersistenceEntity.getId() == null) {
            return false;
        }
        Collection<ReleasePersistenceEntity> releases = getReleasesOfAPI(id);
        for (ReleasePersistenceEntity elem : releases) {
            entityManager.remove(elem);
        }
        entityManager.remove(apiPersistenceEntity);
        return true;
    }

    @Override
    public boolean deleteRelease(String apiId, String releaseId) {
        String sqlQuery = "SELECT a From ReleasePersistenceEntity a WHERE api_id ='" + apiId + "' and id='" + releaseId
                + "'";
        TypedQuery<ReleasePersistenceEntity> query = entityManager.createQuery(sqlQuery,
                ReleasePersistenceEntity.class);
        Collection<ReleasePersistenceEntity> releases = query.getResultList();
        if (releases.size() != 1)
            return false;
        for (ReleasePersistenceEntity elem : releases) {
            entityManager.remove(elem);
        }
        return true;
    }

    @Override
    public Optional<API> findAPIByLink(String repositoryLink) {
        TypedQuery<APIPersistenceEntity> query = entityManager.createQuery(
                "SELECT a FROM APIPersistenceEntity a WHERE repository_link='" + repositoryLink + "'",
                APIPersistenceEntity.class);
        Collection<APIPersistenceEntity> apis = query.getResultList();
        if (apis.size() == 0)
            return Optional.empty();
        Collection<API> result = new ArrayList<>();
        for (APIPersistenceEntity elem : apis) {
            result.add(APIMapper.convertToAPI(elem));
        }
        return result.stream().findFirst();
    }

    @Override
    public void createAPI(API api) {
        entityManager.persist(APIMapper.convertToAPIPersistenceEntity(api));
    }

    @Override
    public void createRelease(Release release, API api) {
        entityManager.persist(APIMapper.convertToReleasePersistenceEntity(release, api));
    }

    @Override
    public void updateAPI(API api) {
        entityManager.merge(APIMapper.convertToAPIPersistenceEntity(api));
    }

    @Override
    public Optional<Release> findReleaseById(String releaseId, String apiId) throws InvalidArgumentException {
        TypedQuery<ReleasePersistenceEntity> query = entityManager.createQuery(
                "SELECT a FROM ReleasePersistenceEntity a WHERE id='" + releaseId + "' and api_id='" + apiId + "'",
                ReleasePersistenceEntity.class);
        Collection<ReleasePersistenceEntity> releases = query.getResultList();
        if (releases.size() == 0)
            return Optional.empty();
        Collection<Release> result = new ArrayList<>();
        for (ReleasePersistenceEntity elem : releases) {
            result.add(APIMapper.convertToRelease(elem));
        }

        return result.stream().findFirst();
    }

    @Override
    public void updateRelease(Release release, API api) {
        entityManager.merge(APIMapper.convertToReleasePersistenceEntity(release, api));
    }

    @Override
    public boolean deleteReleaseBySlug(String slug) {
        String sqlQuery = "SELECT a From ReleasePersistenceEntity a WHERE slug ='" + slug + "'";
        TypedQuery<ReleasePersistenceEntity> query = entityManager.createQuery(sqlQuery,
                ReleasePersistenceEntity.class);
        Collection<ReleasePersistenceEntity> releases = query.getResultList();
        if (releases.size() == 0)
            return false;
        for (ReleasePersistenceEntity elem : releases) {
            entityManager.remove(elem);
        }
        return true;
    }
}
