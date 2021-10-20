package edu.kit.tm.cm.serviceenvironment.api.logic.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class APISpecification {
    @NotNull
    private String specification;
    @NotNull
    private SpecificationType type;
}
