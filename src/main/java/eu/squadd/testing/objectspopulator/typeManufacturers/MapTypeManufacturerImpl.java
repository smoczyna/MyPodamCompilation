package eu.squadd.testing.objectspopulator.typeManufacturers;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Default collection type manufacturer.
 *
 * @author z094
 */
public class MapTypeManufacturerImpl extends AbstractTypeManufacturer<Map<Object, Object>> {

    @Override
    public Map<Object, Object> getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Class<?> mapType = attributeMetadata.getAttributeType();
        Map<Object, Object> retValue = null;

        if (SortedMap.class.isAssignableFrom(mapType)) {
            if (mapType.isAssignableFrom(TreeMap.class)) {
                retValue = new TreeMap<Object, Object>();
            }
        } else if (ConcurrentMap.class.isAssignableFrom(mapType)) {
            if (mapType.isAssignableFrom(ConcurrentHashMap.class)) {
                retValue = new ConcurrentHashMap<Object, Object>();
            }
        } else if (mapType.isAssignableFrom(HashMap.class)) {
            retValue = new HashMap<Object, Object>();
        }

        return retValue;
    }
}
