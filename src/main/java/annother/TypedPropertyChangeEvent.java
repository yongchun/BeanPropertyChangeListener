package annother;

import java.beans.PropertyChangeEvent;

/**
 * The extends class with type
 */
public class TypedPropertyChangeEvent extends PropertyChangeEvent {

    /**
     * Property type
     */
    public int type;

    /**
     * The property is number?
     */
    public boolean isNumber;

    /**
     * Constructor
     */
    public TypedPropertyChangeEvent(Object source, String propertyName, int type, Object oldValue, Object newValue,
                                    boolean isNumber) {

        super(source, propertyName, oldValue, newValue);
        this.type = type;
        this.isNumber = isNumber;
    }
}
