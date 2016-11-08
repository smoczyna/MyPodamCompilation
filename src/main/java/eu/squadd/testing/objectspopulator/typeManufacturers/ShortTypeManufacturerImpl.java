package eu.squadd.testing.objectspopulator.typeManufacturers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerShortValue;

/**
 * Default short type manufacturer.
 *
 * @author z094
 */
public class ShortTypeManufacturerImpl extends AbstractTypeManufacturer<Short> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(ShortTypeManufacturerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Short getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Short retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerShortValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerShortValue shortStrategy = (ScannerShortValue) annotation;

                String numValueStr = shortStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {
                    try {
                        retValue = Short.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = "The precise value: "
                                + numValueStr
                                + " cannot be converted to a short type. An exception will be thrown.";
                        LOG.error(errMsg);
                        throw new IllegalArgumentException(errMsg, nfe);
                    }
                } else {

                    short minValue = shortStrategy.minValue();
                    short maxValue = shortStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getShortInRange(minValue, maxValue,
                            attributeMetadata);

                }

                break;

            }
        }

        if (retValue == null) {
            retValue = getShort(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a short/Short value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A short/Short value.
     */
    public Short getShort(AttributeMetadata attributeMetadata) {

        short retValue;
        do {
            retValue = (short) RANDOM.nextInt(Byte.MAX_VALUE);
        } while (retValue == 0);
        return retValue;
    }

    /**
     * It returns a short/Short value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A short/Short value between min and max value (included).
     */
    public Short getShortInRange(short minValue, short maxValue,
            AttributeMetadata attributeMetadata) {

        return (short) (minValue + Math.random() * (maxValue - minValue) + 0.5);
    }

}
