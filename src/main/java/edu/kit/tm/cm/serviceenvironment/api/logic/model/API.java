package edu.kit.tm.cm.serviceenvironment.api.logic.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class API {
    private String id;
    private String name;
    private String repositoryLink;
    private String description;
}
