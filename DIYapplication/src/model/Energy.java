package model;
/**
 * Enum class for Energy. 
 * 
 * @author Tammy Vo
 * @version May 28th, 2019
 *
 */
public enum Energy {
	LOW(1), MEDIUM(2), HIGH(3);

	private final int value;

	Energy(final int newValue) {
		value = newValue;
	}
	
	/**
	 * Get enum value. 
	 * @author Tammy Vo
	 * @param theValue
	 * @return the energy value
	 */
	public static Energy getEnumVal(final int theValue) {
		return Energy.values()[theValue - 1];
	}
	
	/**
	 * Get value for energy.
	 * @author Tammy Vo
	 * @return value for energy.
	 */
	public int getValue() {
		return value;
	}
}
