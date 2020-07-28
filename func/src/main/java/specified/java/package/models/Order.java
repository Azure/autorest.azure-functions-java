package specified.java.package.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/**
 * The Order model.
 */
@Fluent
public final class Order {
    /*
     * The id property.
     */
    @JsonProperty(value = "id")
    private Long id;

    /*
     * The petId property.
     */
    @JsonProperty(value = "petId")
    private Long petId;

    /*
     * The quantity property.
     */
    @JsonProperty(value = "quantity")
    private Integer quantity;

    /*
     * The shipDate property.
     */
    @JsonProperty(value = "shipDate")
    private OffsetDateTime shipDate;

    /*
     * Order Status
     */
    @JsonProperty(value = "status")
    private OrderStatus status;

    /*
     * The complete property.
     */
    @JsonProperty(value = "complete")
    private Boolean complete;

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
     * @return the Order object itself.
     */
    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get the petId property: The petId property.
     * 
     * @return the petId value.
     */
    public Long getPetId() {
        return this.petId;
    }

    /**
     * Set the petId property: The petId property.
     * 
     * @param petId the petId value to set.
     * @return the Order object itself.
     */
    public Order setPetId(Long petId) {
        this.petId = petId;
        return this;
    }

    /**
     * Get the quantity property: The quantity property.
     * 
     * @return the quantity value.
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * Set the quantity property: The quantity property.
     * 
     * @param quantity the quantity value to set.
     * @return the Order object itself.
     */
    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Get the shipDate property: The shipDate property.
     * 
     * @return the shipDate value.
     */
    public OffsetDateTime getShipDate() {
        return this.shipDate;
    }

    /**
     * Set the shipDate property: The shipDate property.
     * 
     * @param shipDate the shipDate value to set.
     * @return the Order object itself.
     */
    public Order setShipDate(OffsetDateTime shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    /**
     * Get the status property: Order Status.
     * 
     * @return the status value.
     */
    public OrderStatus getStatus() {
        return this.status;
    }

    /**
     * Set the status property: Order Status.
     * 
     * @param status the status value to set.
     * @return the Order object itself.
     */
    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the complete property: The complete property.
     * 
     * @return the complete value.
     */
    public Boolean isComplete() {
        return this.complete;
    }

    /**
     * Set the complete property: The complete property.
     * 
     * @param complete the complete value to set.
     * @return the Order object itself.
     */
    public Order setComplete(Boolean complete) {
        this.complete = complete;
        return this;
    }
}
