package specified.java.test.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The Pet model.
 */
@Fluent
public final class Pet {
    /*
     * The id property.
     */
    @JsonProperty(value = "id")
    private Long id;

    /*
     * The category property.
     */
    @JsonProperty(value = "Category")
    private Category category;

    /*
     * The name property.
     */
    @JsonProperty(value = "name", required = true)
    private String name;

    /*
     * The photoUrls property.
     */
    @JsonProperty(value = "photoUrl", required = true)
    private List<String> photoUrls;

    /*
     * The tags property.
     */
    @JsonProperty(value = "tag")
    private List<Tag> tags;

    /*
     * pet status in the store
     */
    @JsonProperty(value = "status")
    private PetStatus status;

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
     * @return the Pet object itself.
     */
    public Pet setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the category property: The category property.
     * 
     * @return the category value.
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * Set the category property: The category property.
     * 
     * @param category the category value to set.
     * @return the Pet object itself.
     */
    public Pet setCategory(Category category) {
        this.category = category;
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
     * @return the Pet object itself.
     */
    public Pet setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the photoUrls property: The photoUrls property.
     * 
     * @return the photoUrls value.
     */
    public List<String> getPhotoUrls() {
        return this.photoUrls;
    }

    /**
     * Set the photoUrls property: The photoUrls property.
     * 
     * @param photoUrls the photoUrls value to set.
     * @return the Pet object itself.
     */
    public Pet setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    /**
     * Get the tags property: The tags property.
     * 
     * @return the tags value.
     */
    public List<Tag> getTags() {
        return this.tags;
    }

    /**
     * Set the tags property: The tags property.
     * 
     * @param tags the tags value to set.
     * @return the Pet object itself.
     */
    public Pet setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the status property: pet status in the store.
     * 
     * @return the status value.
     */
    public PetStatus getStatus() {
        return this.status;
    }

    /**
     * Set the status property: pet status in the store.
     * 
     * @param status the status value to set.
     * @return the Pet object itself.
     */
    public Pet setStatus(PetStatus status) {
        this.status = status;
        return this;
    }
}
