package edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Release")
public class ReleasePersistenceEntity {
    @Id
    @GeneratedValue(generator = "release-uuid")
    @GenericGenerator(name = "release-uuid", strategy = "uuid")
    String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private StatusPersistenceEnumeration status;

    @Column
    private Date published;

    private String slug;

    @Column(name = "commit_sha")
    private String commitSha;

    @Embedded
    private APISpecificationPersistenceEntity specification;

    @ManyToOne()
    @JoinColumn(name = "api_id", nullable = false)
    private APIPersistenceEntity api;
}
