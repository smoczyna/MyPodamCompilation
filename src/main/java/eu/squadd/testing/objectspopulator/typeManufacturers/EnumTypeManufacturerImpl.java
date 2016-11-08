package eu.squadd.testing.objectspopulator.typeManufacturers;

import java.lang.reflect.Type;
import java.util.Map;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.api.ScannerUtils;

/**
 * Default Enum type manufacturer.
 *
 * @author z094
 */
public class EnumTypeManufacturerImpl extends AbstractTypeManufacturer<Enum<?>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Enum<?> getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Class<?> realAttributeType = attributeMetadata.getAttributeType();

        Enum<?> retValue = null;

        // Enum type
        int enumConstantsLength = realAttributeType.getEnumConstants().length;

        if (enumConstantsLength > 0) {
            int enumIndex = ScannerUtils.getIntegerInRange(0, enumConstantsLength)
                    % enumConstantsLength;
            retValue = (Enum<?>) realAttributeType.getEnumConstants()[enumIndex];
        }

        return retValue;
    }
}
