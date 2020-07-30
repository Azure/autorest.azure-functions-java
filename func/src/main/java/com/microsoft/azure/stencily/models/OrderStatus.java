package com.microsoft.azure.stencily.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/**
 * Defines values for OrderStatus.
 */
public final class OrderStatus extends ExpandableStringEnum<OrderStatus> {
    /**
     * Static value placed for OrderStatus.
     */
    public static final OrderStatus PLACED = fromString("placed");

    /**
     * Static value approved for OrderStatus.
     */
    public static final OrderStatus APPROVED = fromString("approved");

    /**
     * Static value delivered for OrderStatus.
     */
    public static final OrderStatus DELIVERED = fromString("delivered");

    /**
     * Creates or finds a OrderStatus from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding OrderStatus.
     */
    @JsonCreator
    public static OrderStatus fromString(String name) {
        return fromString(name, OrderStatus.class);
    }

    /**
     * @return known OrderStatus values.
     */
    public static Collection<OrderStatus> values() {
        return values(OrderStatus.class);
    }
}
