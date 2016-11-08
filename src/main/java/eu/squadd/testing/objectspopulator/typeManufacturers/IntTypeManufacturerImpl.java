package eu.squadd.testing.objectspopulator.typeManufacturers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.api.ScannerUtils;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerIntValue;

/**
 * Default int type manufacturer.
 *
 * @author z094
 */
public class IntTypeManufacturerImpl extends AbstractTypeManufacturer<Integer> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(IntTypeManufacturerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Integer retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerIntValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerIntValue intStrategy = (ScannerIntValue) annotation;

                String numValueStr = intStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {
                    try {
                        retValue = Integer.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = ScannerConstants.THE_ANNOTATION_VALUE_STR
                                + numValueStr
                                + " could not be converted to an Integer. An exception will be thrown.";
                        LOG.error(errMsg);
                        throw new IllegalArgumentException(errMsg, nfe);

                    }

                } else {

                    int minValue = intStrategy.minValue();
                    int maxValue = intStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getIntegerInRange(minValue, maxValue,
                            attributeMetadata);

                }

                break;

            }

        }

        if (retValue == null) {
            retValue = getInteger(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns an int/Integer value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A int/Integer value
     */
    public Integer getInteger(AttributeMetadata attributeMetadata) {

        Integer retValue;
        do {
            retValue = RANDOM.nextInt();
        } while (retValue.intValue() == 0);
        return retValue;
    }

    /**
     * It returns an int/Integer value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return An int/Integer value between min and max value (included).
     */
    public int getIntegerInRange(int minValue, int maxValue,
            AttributeMetadata attributeMetadata) {

        return ScannerUtils.getIntegerInRange(minValue, maxValue);
    }

}
