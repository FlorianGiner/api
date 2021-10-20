package edu.kit.tm.cm.serviceenvironment.api;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

import edu.kit.tm.cm.serviceenvironment.api.api.APIEntityController;
import edu.kit.tm.cm.serviceenvironment.api.api.APISpecificationController;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitAPI;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects.SubmitRelease;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.APIResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects.ReleaseResponseDTO;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.APISpecification;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.SpecificationType;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Status;

import java.util.Date;

@SpringBootTest(properties = { "spring.jpa.hibernate.ddl-auto=create" }, webEnvironment = WebEnvironment.RANDOM_PORT)
class ApiApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(APISpecificationController.class);

	@Test
	void initTest() {
		assertThat(controller).isNotNull();

		log.info("baseURL = " + baseURL());
	}

	// Can access properties on the controller here.
	@Autowired
	private APIEntityController controller;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate rest;

	private String baseURL() {
		return "http://localhost:" + port + "/v1";
	}

	private void debugPrintGet(String endpoint) {
		assertThat(rest.getForEntity(baseURL() + endpoint, String.class).getBody()).isEqualTo("");
	}

	private void checkPublishedIsNearNow(Date published) {
		// The set published time should be set to be near the current time.
		long now = System.currentTimeMillis() / 1000;
		assertThat(published.getTime() / 1000).isLessThanOrEqualTo(now);
		assertThat(published.getTime() / 1000).isGreaterThan(now - 10);
	}

	@Test
	void fullAPIIntegrationTest() {
		var apiSpec = new APISpecification();
		apiSpec.setSpecification("grpc-spec");
		apiSpec.setType(SpecificationType.GRPC);

		var reqRel = new SubmitRelease();
		reqRel.setSlug("branch-main");
		reqRel.setCommitSha("123");
		reqRel.setApiSpecification(apiSpec);

		var req = new SubmitAPI();
		req.setRepositoryLink("my-repo");
		req.setSubmitRelease(reqRel);

		// Make POST request.
		assertThat(rest.postForEntity(baseURL() + "/api-specification", req, String.class).getStatusCode())
				.isEqualTo(HttpStatus.OK);

		// Get the newly created API and Release ID with its corresponding slug.
		ResponseEntity<APIResponseDTO[]> resp = rest.getForEntity(baseURL() + "/api", APIResponseDTO[].class);
		assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
		var gotBody = resp.getBody();

		// Exactly 1 API
		assertThat(gotBody.length).isEqualTo(1);
		var got = gotBody[0];

		// Check API values for defaults and constraints.
		assertThat(got.getId()).isNotNull();
		assertThat(got.getId()).isNotEmpty();
		assertThat(got.getRepositoryLink()).isEqualTo(req.getRepositoryLink());
		assertThat(got.getDescription()).isNull();
		assertThat(got.getName()).isNull();

		// Map of ID-slug pairs.
		var gotRelMap = got.getReleases();

		// Exactly one new release.
		assertThat(gotRelMap.size()).isEqualTo(1);

		// The one value is the slug that was passed.
		assertThat(gotRelMap).containsValue(reqRel.getSlug());

		// Check that the generated Release ID.
		var gotNewRelID = gotRelMap.keySet().iterator().next();
		assertThat(gotNewRelID).isNotNull();
		assertThat(gotNewRelID).isNotEmpty();

		// Get releases.
		ResponseEntity<ReleaseResponseDTO> respRels = rest
				.getForEntity(baseURL() + "/api/" + got.getId() + "/release/" + gotNewRelID, ReleaseResponseDTO.class);
		assertThat(respRels.getStatusCode()).isEqualTo(HttpStatus.OK);
		var gotRel = respRels.getBody();

		var wantRelease = new ReleaseResponseDTO();
		// ID is set server-side.
		wantRelease.setId(gotNewRelID);
		wantRelease.setCommitSha(reqRel.getCommitSha());
		wantRelease.setSlug(reqRel.getSlug());
		wantRelease.setStatus(Status.ACTIVE);
		// Published is set server-side.
		wantRelease.setPublished(gotRel.getPublished());
		wantRelease.setApiSpecification(req.getSubmitRelease().getApiSpecification());

		assertThat(gotRel).isEqualTo(wantRelease);

		// Check the date was set correctly server-side.
		checkPublishedIsNearNow(gotRel.getPublished());
	}
}
