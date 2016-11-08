package eu.squadd.testing.objectspopulator.typeManufacturers;

import java.lang.reflect.Type;
import java.util.Map;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;

/**
 * Interface for a type manufacturer
 *
 * @author z094
 */
public interface TypeManufacturer<T> {

    /**
     * Returns a type value conforming to the annotations and the
     * AttributeMetadata provided.
     *
     * @param strategy The DataProviderStrategy
     * @param attributeMetadata The AttributeMetadata
     * @param genericTypesArgumentsMap map with generic types mapped to actual
     * types
     *
     * @return A type value conforming to the annotations and the
     * AttributeMetadata provided.
     */
    T getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap);
}
