package com.microsoft.azure.stencily.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ApiResponse model.
 */
@Fluent
public final class ApiResponse {
    /*
     * The code property.
     */
    @JsonProperty(value = "code")
    private Integer code;

    /*
     * The type property.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * The message property.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * Get the code property: The code property.
     * 
     * @return the code value.
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * Set the code property: The code property.
     * 
     * @param code the code value to set.
     * @return the ApiResponse object itself.
     */
    public ApiResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * Get the type property: The type property.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type property: The type property.
     * 
     * @param type the type value to set.
     * @return the ApiResponse object itself.
     */
    public ApiResponse setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the message property: The message property.
     * 
     * @return the message value.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set the message property: The message property.
     * 
     * @param message the message value to set.
     * @return the ApiResponse object itself.
     */
    public ApiResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
