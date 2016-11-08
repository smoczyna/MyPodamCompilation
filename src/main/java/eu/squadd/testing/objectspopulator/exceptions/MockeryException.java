/**
 *
 */
package eu.squadd.testing.objectspopulator.exceptions;

/**
 * The exception which occurs while PODAM is filling a POJO.
 *
 * @author mtedone
 *
 * @since 1.0.0
 *
 */
public class MockeryException extends RuntimeException {

    // ------------------->> Constants
    private static final long serialVersionUID = 1L;

    // ------------------->> Instance / Static variables
    // ------------------->> Constructors
    /**
     * Full constructor
     *
     * @param message The exception message
     * @param cause The error which caused this exception to be thrown
     */
    public MockeryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * String constructor.
     *
     * @param message The exception message
     */
    public MockeryException(String message) {
        super(message);
    }

}
