package cz.fi.muni.pa165.enums;

public enum OrderState {

    RECEIVED, CANCELED, SHIPPED, DONE;

    /**
     * Checks the enum for a string contained within
     * 
     * @param valueString
     * @return true if the string is contained in the enum 
     */
    public static boolean contains(final String valueString) {
        for (OrderState os : OrderState.values()) {
            if (os.name().equals(valueString)) {
                return true;
            }
        }
        return false;
    }

}
