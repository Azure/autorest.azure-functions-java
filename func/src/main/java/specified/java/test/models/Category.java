package specified.java.test.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Category model.
 */
@Fluent
public final class Category {
    /*
     * The id property.
     */
    @JsonProperty(value = "id")
    private Long id;

    /*
     * The name property.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Get the id property: The id property.
     * 
     * @return the id value.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set the id property: The id property.
     * 
     * @param id the id value to set.
     * @return the Category object itself.
     */
    public Category setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the name property: The name property.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: The name property.
     * 
     * @param name the name value to set.
     * @return the Category object itself.
     */
    public Category setName(String name) {
        this.name = name;
        return this;
    }
}
