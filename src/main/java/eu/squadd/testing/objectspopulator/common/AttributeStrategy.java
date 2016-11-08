/**
 *
 */
package eu.squadd.testing.objectspopulator.common;

import eu.squadd.testing.objectspopulator.exceptions.MockeryException;

/**
 * Generic contract for attribute-level data provider strategies.
 *
 * @author mtedone
 *
 */
public interface AttributeStrategy<T> {

    /**
     * It returns a value of the given type
     *
     * @return A value of the given type
     *
     * @throws MockeryException If an exception occurred while assigning
     * the value specified by this strategy
     */
    T getValue();

}
