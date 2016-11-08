package eu.squadd.testing.objectspopulator.typeManufacturers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerFloatValue;

/**
 * Default float type manufacturer.
 *
 * @author z094
 */
public class FloatTypeManufacturerImpl extends AbstractTypeManufacturer<Float> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(FloatTypeManufacturerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Float getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Float retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerFloatValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerFloatValue floatStrategy = (ScannerFloatValue) annotation;

                String numValueStr = floatStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {
                    try {
                        retValue = Float.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = ScannerConstants.THE_ANNOTATION_VALUE_STR
                                + numValueStr
                                + " could not be converted to a Float. An exception will be thrown.";
                        LOG.error(errMsg);
                        throw new IllegalArgumentException(errMsg, nfe);
                    }
                } else {

                    float minValue = floatStrategy.minValue();
                    float maxValue = floatStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getFloatInRange(minValue, maxValue,
                            attributeMetadata);

                }

                break;

            }

        }

        if (retValue == null) {
            retValue = getFloat(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a float/Float value.
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A float/Float value
     */
    public Float getFloat(AttributeMetadata attributeMetadata) {

        float retValue;
        do {
            retValue = RANDOM.nextFloat();
        } while (retValue == 0.0f);
        return retValue;
    }

    /**
     * It returns a float/Float value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A float/Float value between min and max value (included).
     */
    public Float getFloatInRange(float minValue, float maxValue,
            AttributeMetadata attributeMetadata) {

        // This can happen. It's a way to specify a precise value
        if (minValue == maxValue) {
            return minValue;
        }
        float retValue;
        do {
            retValue = (float) (minValue
                    + Math.random() * (maxValue - minValue + 1));
        } while (retValue > maxValue);
        return retValue;
    }

}
