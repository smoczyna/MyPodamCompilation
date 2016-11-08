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
import eu.squadd.testing.objectspopulator.common.ScannerLongValue;

/**
 * Default int type manufacturer.
 *
 * @author z094
 */
public class LongTypeManufacturerImpl extends AbstractTypeManufacturer<Long> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(LongTypeManufacturerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Long retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerLongValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerLongValue longStrategy = (ScannerLongValue) annotation;

                String numValueStr = longStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {
                    try {
                        retValue = Long.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = ScannerConstants.THE_ANNOTATION_VALUE_STR
                                + numValueStr
                                + " could not be converted to a Long. An exception will be thrown.";
                        LOG.error(errMsg);
                        throw new IllegalArgumentException(errMsg, nfe);
                    }
                } else {

                    long minValue = longStrategy.minValue();
                    long maxValue = longStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getLongInRange(minValue, maxValue,
                            attributeMetadata);

                }

                break;

            }

        }

        if (retValue == null) {
            retValue = getLong(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a long/Long value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A long/Long value
	 *
     */
    public Long getLong(AttributeMetadata attributeMetadata) {

        return System.nanoTime();
    }

    /**
     * It returns a long/Long value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A long/Long value between min and max value (included).
     */
    public Long getLongInRange(long minValue, long maxValue,
            AttributeMetadata attributeMetadata) {

        return ScannerUtils.getLongInRange(minValue, maxValue);
    }

}
