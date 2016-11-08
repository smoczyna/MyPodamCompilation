/**
 *
 */
package eu.squadd.testing.objectspopulator.api;

import eu.squadd.testing.objectspopulator.common.AttributeStrategy;
import eu.squadd.testing.objectspopulator.common.ScannerCollection;

/**
 * A default Object strategy, just to provide a default to
 * {@link PodamCollection#collectionElementStrategy()}.
 *
 * @author mtedone
 *
 */
public class ObjectStrategy implements AttributeStrategy<Object> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        return new Object();
    }

}
