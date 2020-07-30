package com.microsoft.azure.stencily.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for HostOptions.
 */
public final class HostOptions extends ExpandableStringEnum<HostOptions> {
    /**
     * Static value https://virtserver.swaggerhub.com/petstore/api/v1.0.0 for HostOptions.
     */
    public static final HostOptions HTTPS__VIRTSERVER_SWAGGERHUB_COM_PETSTORE_API_V1_0_0 = fromString("https://virtserver.swaggerhub.com/petstore/api/v1.0.0");

    /**
     * Static value http://virtserver.swaggerhub.com/petstore/api/v1.0.0 for HostOptions.
     */
    public static final HostOptions HTTP__VIRTSERVER_SWAGGERHUB_COM_PETSTORE_API_V1_0_0 = fromString("http://virtserver.swaggerhub.com/petstore/api/v1.0.0");

    /**
     * Creates or finds a HostOptions from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding HostOptions.
     */
    @JsonCreator
    public static HostOptions fromString(String name) {
        return fromString(name, HostOptions.class);
    }

    /**
     * @return known HostOptions values.
     */
    public static Collection<HostOptions> values() {
        return values(HostOptions.class);
    }
}
