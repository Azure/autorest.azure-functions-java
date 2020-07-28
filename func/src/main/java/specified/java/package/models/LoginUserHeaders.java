package specified.java.package.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * The LoginUserHeaders model.
 */
@Fluent
public final class LoginUserHeaders {
    /*
     * The X-Rate-Limit property.
     */
    @JsonProperty(value = "X-Rate-Limit")
    private Integer xRateLimit;

    /*
     * The X-Expires-After property.
     */
    @JsonProperty(value = "X-Expires-After")
    private OffsetDateTime xExpiresAfter;

    /**
     * Get the xRateLimit property: The X-Rate-Limit property.
     * 
     * @return the xRateLimit value.
     */
    public Integer getXRateLimit() {
        return this.xRateLimit;
    }

    /**
     * Set the xRateLimit property: The X-Rate-Limit property.
     * 
     * @param xRateLimit the xRateLimit value to set.
     * @return the LoginUserHeaders object itself.
     */
    public LoginUserHeaders setXRateLimit(Integer xRateLimit) {
        this.xRateLimit = xRateLimit;
        return this;
    }

    /**
     * Get the xExpiresAfter property: The X-Expires-After property.
     * 
     * @return the xExpiresAfter value.
     */
    public OffsetDateTime getXExpiresAfter() {
        return this.xExpiresAfter;
    }

    /**
     * Set the xExpiresAfter property: The X-Expires-After property.
     * 
     * @param xExpiresAfter the xExpiresAfter value to set.
     * @return the LoginUserHeaders object itself.
     */
    public LoginUserHeaders setXExpiresAfter(OffsetDateTime xExpiresAfter) {
        this.xExpiresAfter = xExpiresAfter;
        return this;
    }
}
