package annother;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Diff the object properties
 */
public class ObjectPropertyDiffer {
    /**
     * Bound class
     */
    private final Class<?> cz;

    /**
     * All property change listeners
     */
    private final List<PropertyChangeListener> listeners = new ArrayList<>();

    /**
     * Bound property tuple
     */
    private final Map<String, PropertyTuple> tupleMap = new HashMap<>();

    /**
     * Constructor
     */
    public ObjectPropertyDiffer(Class<?> cz) {
        if (cz == null) {
            throw new IllegalArgumentException("null");
        }

        this.cz = cz;
    }

    /**
     * Add property
     */
    public void addProperty(String property, int type) {
        if (!tupleMap.containsKey(property)) {

            PropertyTuple tuple = new PropertyTuple(cz, type, property);

            tupleMap.put(property, tuple);
        }
    }

    /**
     * Add listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    /**
     * Diff the old and new instance
     */
    public void diff(Object oldObj, Object newObj, PropertyChangeListener... listeners) {

        if (oldObj == null || newObj == null) {
            throw new IllegalArgumentException("null.");
        }

        for (PropertyTuple p : tupleMap.values()) {

            Object oldValue = p.getValue(oldObj);
            Object newValue = p.getValue(newObj);

            if (oldValue == null && newValue == null) {
                continue;
            }

            if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
                continue;
            }

            PropertyChangeEvent event = new TypedPropertyChangeEvent(
                    newObj,
                    p.fieldName,
                    p.type,
                    oldValue,
                    newValue,
                    p.isNumber());

            // fire change event with special listeners
            firePropertyChangeEvent(event, listeners);

            // fire the change event
            firePropertyChangeEvent(event);
        }
    }

    /**
     * Fire the change event with special listeners
     */
    private void firePropertyChangeEvent(PropertyChangeEvent event, PropertyChangeListener... listeners) {
        Arrays.stream(listeners).forEach(p -> p.propertyChange(event));
    }

    /**
     * Fire the change event
     */
    private void firePropertyChangeEvent(PropertyChangeEvent event) {
        this.listeners.forEach(p -> p.propertyChange(event));
    }
}
