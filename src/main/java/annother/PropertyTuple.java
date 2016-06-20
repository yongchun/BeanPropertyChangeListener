package annother;

import java.lang.reflect.Field;

/**
 * Property tuple
 */
public class PropertyTuple {
    /**
     * Field name
     */
    public final String fieldName;
    /**
     * Property type
     */
    public final int type;
    /**
     * Field pointer
     */
    private Field field;

    /**
     * Constructor
     */
    public PropertyTuple(Class<?> clz, int type, String fieldName) {
        this.fieldName = fieldName;
        this.type = type;

        try {

            this.field = clz.getDeclaredField(fieldName);

            // set access
            this.field.setAccessible(true);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getValue(Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isNumber() {
        final Class t = field.getType();

        return t == long.class || t == int.class || t == byte.class || t == short.class
                || Number.class.isAssignableFrom(t);
    }
}
