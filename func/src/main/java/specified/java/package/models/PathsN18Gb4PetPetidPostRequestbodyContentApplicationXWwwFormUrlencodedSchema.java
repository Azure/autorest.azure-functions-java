package specified.java.package.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The
 * PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema
 * model.
 */
@Fluent
public final class PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema {
    /*
     * Updated name of the pet
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * Updated status of the pet
     */
    @JsonProperty(value = "status")
    private String status;

    /**
     * Get the name property: Updated name of the pet.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: Updated name of the pet.
     * 
     * @param name the name value to set.
     * @return the
     * PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema
     * object itself.
     */
    public PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the status property: Updated status of the pet.
     * 
     * @return the status value.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Set the status property: Updated status of the pet.
     * 
     * @param status the status value to set.
     * @return the
     * PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema
     * object itself.
     */
    public PathsN18Gb4PetPetidPostRequestbodyContentApplicationXWwwFormUrlencodedSchema setStatus(String status) {
        this.status = status;
        return this;
    }
}
