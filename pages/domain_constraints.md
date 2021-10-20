# **API** Domain Constraints 

```
context API inv:
      self.id -> notEmpty() implies not(exists(api:API | api.id = self.id))
      self.repositoryLink -> notEmpty() implies not(exists(api:API | api.repositoryLink = self.repositoryLink))

context Release inv:
      self.id -> notEmpty() implies not(exists(release:Release | release.id = self.id))
      self.status -> notEmpty()
      self.slug -> hasAnyPrefix(["branch/master", "tag/"])
      self.specification -> notEmpty()

context APISpecification inv:
      self.specification -> notEmpty()
      self.type -> notEmpty()

context API::addAPISpecification(submittedAPI SubmitAPI) : (newRelease Release)
pre:  submittedAPI.submitRelease.slug.hasAnyPrefix(["branch-master", "tag-"])
post: count(api | api.repositoryLink = submittedAPI.repositoryLink) = 1 and
      let nowSeconds : Integer = now().unixSeconds in
            nowSeconds + 10 > newRelease.published.unixSeconds >= nowSeconds
```

## API Context

An API is identified by its id, which is why it has to be set and unique. The repository link is required to group new releases with the same repositoryLink inside the same API, which is why it has to be set and unique.

For API::addAPISpecification, the slug provided with the new release has to be prefixed with "branch/master" or "tag/" so that only branches starting with master* and tags are recorded in the domain microservice.

After API::addAPISpecification, there should exist exactly one API object with the same repositoryLink as the one passed in within submittedAPI, which means that if none existed before, then afterwards one exists. The published date of the new release, which is set server-side for consistency, should not differ much from the current local time to ensure newRelease.published represents the time the release was imported.

## Release Context

A Release is identified by its id, which is why it has to be set and unique. The status is required to distinguish between active and deprecated APIs in the frontend. The specification is required because without it the release would not make sense existing. Also the Release is there to hold the APISpecification. The releases' slug that represents a master branch or tag has to follow the slug-string format.

## APISpecification Context

An APISpecification requires specification data because the specification is what is important about an APISpecification. The type is required for handling to be able to distinguish between gRPC and OpenAPI specifications.
