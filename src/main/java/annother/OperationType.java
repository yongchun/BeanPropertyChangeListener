package annother;

/**
 *
 *
 */
public enum OperationType {

    NAME(0),

    AGE(1),

    GENDER(2),
    DESC(3);

    /**
     * the bound type
     */
    public final int type;

    OperationType(int type) {
        this.type = type;
    }

}
