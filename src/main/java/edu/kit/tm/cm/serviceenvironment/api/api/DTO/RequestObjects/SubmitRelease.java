package edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects;

import edu.kit.tm.cm.serviceenvironment.api.logic.model.APISpecification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class SubmitRelease {
    @NotNull
    private String slug;
    @NotNull
    private String commitSha;
    @Valid
    @NotNull
    private APISpecification apiSpecification;
}
