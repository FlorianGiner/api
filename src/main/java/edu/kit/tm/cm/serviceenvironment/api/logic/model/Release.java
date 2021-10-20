package edu.kit.tm.cm.serviceenvironment.api.logic.model;

import edu.kit.tm.cm.serviceenvironment.api.logic.exception.InvalidArgumentException;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class Release {
    private String id;
    private Status status;
    private APISpecification apiSpecification;
    @Setter(value = AccessLevel.NONE)
    private String slug;
    private Date published;
    private String commitSha;

    public Release() {
        this.status = Status.ACTIVE;
        this.published = new Date();
    }

    public void setSlug(String slug) throws InvalidArgumentException {
        String tagRegex = "tag-.*";
        String branchRegex = "branch-.*";

        if (!(slug.matches(tagRegex) || slug.matches(branchRegex))) {
            throw new InvalidArgumentException();
        }
        this.slug = slug;
    }
}
