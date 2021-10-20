package edu.kit.tm.cm.serviceenvironment.api.logic.model;

import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

public class ReleaseUnitTests {
    private Release release = new Release();

    @Test
    public void setSlug() throws InvalidArgumentException {
        assertThrows(InvalidArgumentException.class, () -> release.setSlug("tag/hello"));
        assertThrows(InvalidArgumentException.class, () -> release.setSlug("branch/hello"));
        assertThrows(InvalidArgumentException.class, () -> release.setSlug("unknown-prefix-hello"));

        release.setSlug("tag-hello");
        release.setSlug("branch-hello");
        release.setSlug("tag-hello/world");
    }

    @Test
    public void constructor() {
        Release release = new Release();
        assertThat(release.getStatus(), equalTo(Status.ACTIVE));

        // The set published time should be set to be near the current time.
        long now = System.currentTimeMillis() / 1000;
        assertThat(release.getPublished().getTime() / 1000, lessThanOrEqualTo(now));
        assertThat(release.getPublished().getTime() / 1000, greaterThan(now - 10));
    }
}
