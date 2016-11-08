package eu.squadd.testing.objectspopulator.typeManufacturers;

import java.util.Random;

/**
 * Parent of all type manufacturer.
 *
 * @author z094
 */
public abstract class AbstractTypeManufacturer<T> implements TypeManufacturer<T> {

    /**
     * A RANDOM generator
     */
    protected static final Random RANDOM = new Random(System.currentTimeMillis());

}
