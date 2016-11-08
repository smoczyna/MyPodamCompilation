package eu.squadd.testing.objectspopulator.typeManufacturers;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.api.ScannerUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerCharValue;

/**
 * Default character type manufacturer.
 * 
 * @author z094
 */
public class CharTypeManufacturerImpl extends AbstractTypeManufacturer<Character> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Character getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Character retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerCharValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerCharValue annotationStrategy = (ScannerCharValue) annotation;

                char charValue = annotationStrategy.charValue();
                if (charValue != ' ') {
                    retValue = charValue;

                } else {

                    char minValue = annotationStrategy.minValue();
                    char maxValue = annotationStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getCharacterInRange(minValue, maxValue,
                            attributeMetadata);

                }

                break;

            }
        }

        if (retValue == null) {
            retValue = getCharacter(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a char/Character value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return a char/Character value
     */
    public Character getCharacter(AttributeMetadata attributeMetadata) {

        return ScannerUtils.getNiceCharacter();
    }

    /**
     * It returns a char/Character value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A char/Character value between min and max value (included).
     */
    public Character getCharacterInRange(char minValue, char maxValue,
            AttributeMetadata attributeMetadata) {

        return (char) (minValue + Math.random() * (maxValue - minValue) + 0.5);
    }

}
