package com.microsoft.azure.stencily.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for PetStatus.
 */
public final class PetStatus extends ExpandableStringEnum<PetStatus> {
    /**
     * Static value available for PetStatus.
     */
    public static final PetStatus AVAILABLE = fromString("available");

    /**
     * Static value pending for PetStatus.
     */
    public static final PetStatus PENDING = fromString("pending");

    /**
     * Static value sold for PetStatus.
     */
    public static final PetStatus SOLD = fromString("sold");

    /**
     * Creates or finds a PetStatus from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding PetStatus.
     */
    @JsonCreator
    public static PetStatus fromString(String name) {
        return fromString(name, PetStatus.class);
    }

    /**
     * @return known PetStatus values.
     */
    public static Collection<PetStatus> values() {
        return values(PetStatus.class);
    }
}
