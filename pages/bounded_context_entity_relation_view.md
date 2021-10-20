# Bounded Context Entity Relation View

The bounded context entity relation view consists of two entities, one value object and two enums. The shared entity API is the central point of the domain. An API can have one or multiple Releases. A Release has a Status, which refers to the life cycle status of the Release. The Release could be either active or deprecated. Every Release has one APISpecification, which describes the Release. In order to render the APISpecification. It is necessary to store its type. The SpecificationType could be either a file of type gRPC or OpenAPI.

![API Entity Relation View placeholder](../figures/api_erp.svg)

## API
Represents an API that offers certain business capabilities. Can have multiple Releases. There can only be one API per GitLab Repository. The repositoryLink is the link to the GitLab Repository. The identifier id is used as a primary key instead of the repositoryLink to simplify the REST API requests, which will be implemented later. The name and the description fields are provider-set values, which describe the API to developers.

## Release
Represents a specific version of an API. Every Release is associated with a git reference name, for example "master", which is stored in the slug field as "tag-\<version\>" or "branch-master_*". The commit the specific git reference name points to is saved in the commit_sha field. The field name has the suffix "_sha" to give credit to the possibility, that commits may be referenced using a different hash. The status field denotes whether the Release is either currently in use or about to be deleted (deprecated). The field published is filled server-side in order for the database to be consistent. The identifier id is used as a primary key because a branch will always have the same slug, which makes that field not usable as such.

## APISpecification
Contains documentation information about the Release. The specification property holds the contents of file, while type contains the information, if it is a gRPC or OpenAPI.
