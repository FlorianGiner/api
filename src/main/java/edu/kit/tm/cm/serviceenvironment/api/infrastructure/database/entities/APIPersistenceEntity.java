package edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "API")
public class APIPersistenceEntity {
    @Id
    @GeneratedValue(generator = "api-uuid")
    @GenericGenerator(name="api-uuid", strategy = "uuid")
    String id;

    private String name;

    private String repositoryLink;

    private String description;
}
