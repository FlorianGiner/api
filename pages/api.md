# API Specification
## Domain Microservice API - API
| Method | Endpoint                          | Request Body | Response        | Description                       |
| ------ | --------------------------------- | ------------ | --------------- | --------------------------------- |
| GET    | /api                              |              | API list        | Get a list of all APIs            |
| GET    | /api/{api_id}                     |              | API             | Get the API with a matching ID    |
| PATCH  | /api/{api_id}                     | PatchAPI     | success/failure | Update the API with a matching ID |
| DELETE | /api/{api_id}                     |              | success/failure | DELETE the API with a matching ID |
| GET    | /api/{api_id}/release             |              | Release list    | Get a list of all Releases        |
| PATCH  | /api/{api_id}/release/{releaseID} | PatchRelease | success/failure | Update a specific Release         |
| DELETE | /api/{api_id}/release/{releaseID} |              | success/failure | Delete a specific Release         |
| POST   | /api-specification                | SubmitAPI    | success/failure | Add an API specification          |

[OpenAPI Specification](../api/openapi.yaml)

A PatchAPI enables the setting of name and description for an existing API. The limitation to neither set id nor repositoryLink provides consistency.

A PatchRelease enables the setting of the status for an existing release. This enables the deprecation of releases. Other fields are prohibited to avoid the introduction of inconsistencies.

A SubmitAPI enables the creation of new releases for an API, which is referenced by the given repositoryLink. Besides the repository link, a SubmitRelease can be provided containing the release slug (branch/master*, tag/*) and the API specification. In case no API with the given repositoryLink exists, a new one is also created.
