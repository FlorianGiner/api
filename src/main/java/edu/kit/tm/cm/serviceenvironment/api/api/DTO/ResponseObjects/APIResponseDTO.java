package edu.kit.tm.cm.serviceenvironment.api.api.DTO.ResponseObjects;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class APIResponseDTO {
    private String id;
    private String name;
    private String repositoryLink;
    private String description;
    Map<String, String> releases;
}
