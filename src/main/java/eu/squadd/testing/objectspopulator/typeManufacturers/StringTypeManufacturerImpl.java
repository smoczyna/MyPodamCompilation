package eu.squadd.testing.objectspopulator.typeManufacturers;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.api.ScannerUtils;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerStringValue;

/**
 * Default String type manufacturer.
 *
 * @author z094
 */
public class StringTypeManufacturerImpl extends AbstractTypeManufacturer<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        String retValue = null;

        List<Annotation> annotations = attributeMetadata.getAttributeAnnotations();

        if (annotations == null || annotations.isEmpty()) {

            retValue = getStringValue(attributeMetadata);

        } else {

            for (Annotation annotation : annotations) {

                if (!ScannerStringValue.class.isAssignableFrom(annotation
                        .getClass())) {
                    continue;
                }

                // A specific value takes precedence over the length
                ScannerStringValue podamAnnotation = (ScannerStringValue) annotation;

                if (podamAnnotation.strValue() != null
                        && podamAnnotation.strValue().length() > 0) {

                    retValue = podamAnnotation.strValue();

                } else {

                    retValue = getStringOfLength(
                            podamAnnotation.length(), attributeMetadata);

                }

            }

            if (retValue == null) {
                retValue = getStringValue(attributeMetadata);
            }

        }

        return retValue;
    }

    /**
     * It returns a string value
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A String of default length
     */
    public String getStringValue(AttributeMetadata attributeMetadata) {

        return getStringOfLength(ScannerConstants.STR_DEFAULT_LENGTH,
                attributeMetadata);
    }

    /**
     * It returns a String of {@code length} characters.
     *
     * @param length The number of characters required in the returned String
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A String of {@code length} characters
     */
    public String getStringOfLength(int length,
            AttributeMetadata attributeMetadata) {

        StringBuilder buff = new StringBuilder();

        while (buff.length() < length) {
            buff.append(ScannerUtils.getNiceCharacter());
        }

        return buff.toString();
    }

}
