package edu.kit.tm.cm.serviceenvironment.api.infrastructure.database.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

@Getter
@Setter
@Embeddable
public class APISpecificationPersistenceEntity {
    @Lob
    private String specification;
    @Enumerated(EnumType.STRING)
    private SpecificationTypePersistenceEnumeration type;
}
