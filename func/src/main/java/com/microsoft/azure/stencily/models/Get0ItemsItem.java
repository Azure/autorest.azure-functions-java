package com.microsoft.azure.stencily.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for Get0ItemsItem.
 */
public final class Get0ItemsItem extends ExpandableStringEnum<Get0ItemsItem> {
    /**
     * Static value available for Get0ItemsItem.
     */
    public static final Get0ItemsItem AVAILABLE = fromString("available");

    /**
     * Static value pending for Get0ItemsItem.
     */
    public static final Get0ItemsItem PENDING = fromString("pending");

    /**
     * Static value sold for Get0ItemsItem.
     */
    public static final Get0ItemsItem SOLD = fromString("sold");

    /**
     * Creates or finds a Get0ItemsItem from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding Get0ItemsItem.
     */
    @JsonCreator
    public static Get0ItemsItem fromString(String name) {
        return fromString(name, Get0ItemsItem.class);
    }

    /**
     * @return known Get0ItemsItem values.
     */
    public static Collection<Get0ItemsItem> values() {
        return values(Get0ItemsItem.class);
    }
}
