package edu.kit.tm.cm.serviceenvironment.api.api.DTO.RequestObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class SubmitAPI {
    @NotNull
    private String repositoryLink;
    @Valid
    @NotNull
    private SubmitRelease submitRelease;
}
