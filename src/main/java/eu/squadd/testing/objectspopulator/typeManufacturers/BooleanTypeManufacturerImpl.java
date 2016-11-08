package eu.squadd.testing.objectspopulator.typeManufacturers;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerBooleanValue;

/**
 * Default boolean type manufacturer.
 *
 * @author z094
 */
public class BooleanTypeManufacturerImpl extends AbstractTypeManufacturer<Boolean> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Boolean retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerBooleanValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerBooleanValue localStrategy = (ScannerBooleanValue) annotation;
                retValue = localStrategy.boolValue();

                break;
            }
        }

        if (retValue == null) {
            retValue = getBoolean(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a boolean/Boolean value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return a boolean/Boolean value
     */
    public Boolean getBoolean(AttributeMetadata attributeMetadata) {

        return Boolean.TRUE;
    }

}
