package edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects;

import edu.kit.tm.cm.serviceenvironment.api.logic.model.APISpecification;
import edu.kit.tm.cm.serviceenvironment.api.logic.model.Status;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ReleaseResponseDTO {
    private String id;
    private Status status;
    private APISpecification apiSpecification;
    private String slug;
    private Date published;
    private String commitSha;
}
