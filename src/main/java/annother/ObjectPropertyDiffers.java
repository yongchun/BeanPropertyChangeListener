package annother;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The helper class for object differ
 */
public class ObjectPropertyDiffers {
    /**
     * Cache all differ
     */
    private static Map<Class, ObjectPropertyDiffer> differCaches = new ConcurrentHashMap<>();

    /**
     * Construct the object differ
     */
    public static ObjectPropertyDiffer getObjectDiffer(Class<?> cz, PropertyChangeListener... listeners) {

        if (cz == null) {
            throw new IllegalArgumentException("class is null");
        }

        if (differCaches.containsKey(cz)) {
            return differCaches.get(cz);
        }

        final ObjectPropertyDiffer differ = new ObjectPropertyDiffer(cz);

        Arrays.stream(cz.getDeclaredFields()).forEach(p -> {
            DiffProperty dp = p.getAnnotation(DiffProperty.class);

            if (dp != null) {
                differ.addProperty(p.getName(), dp.value().type);
            }
        });

        Arrays.stream(listeners).forEach(p -> differ.addPropertyChangeListener(p));

        // set to cache with atomic
        differCaches.putIfAbsent(cz, differ);

        // return the cached entry
        return differCaches.get(cz);
    }
}
