package eu.squadd.testing.objectspopulator.typeManufacturers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import eu.squadd.testing.objectspopulator.common.ScannerDoubleValue;

/**
 * Default double type manufacturer.
 *
 * @author z094
 */
public class DoubleTypeManufacturerImpl extends AbstractTypeManufacturer<Double> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(DoubleTypeManufacturerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Double retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerDoubleValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerDoubleValue doubleStrategy = (ScannerDoubleValue) annotation;

                String numValueStr = doubleStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {

                    try {
                        retValue = Double.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = ScannerConstants.THE_ANNOTATION_VALUE_STR
                                + numValueStr
                                + " could not be converted to a Double. An exception will be thrown.";
                        LOG.error(errMsg);
                        throw new IllegalArgumentException(errMsg, nfe);
                    }

                } else {

                    double minValue = doubleStrategy.minValue();
                    double maxValue = doubleStrategy.maxValue();

                    // Sanity check
                    if (minValue > maxValue) {
                        maxValue = minValue;
                    }

                    retValue = getDoubleInRange(minValue, maxValue,
                            attributeMetadata);
                }

                break;

            }

        }

        if (retValue == null) {
            retValue = getDouble(attributeMetadata);
        }

        return retValue;
    }

    /**
     * It returns a double/Double value
     *
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return a double/Double value
     */
    public Double getDouble(AttributeMetadata attributeMetadata) {

        double retValue;
        do {
            retValue = RANDOM.nextDouble();
        } while (retValue == 0.0);
        return retValue;
    }

    /**
     * It returns a double/Double value between min and max value (included).
     *
     * @param minValue The minimum value for the returned value
     * @param maxValue The maximum value for the returned value
     * @param attributeMetadata attribute metadata for instance to be fetched
     * @return A double/Double value between min and max value (included)
     */
    public Double getDoubleInRange(double minValue, double maxValue,
            AttributeMetadata attributeMetadata) {

        // This can happen. It's a way to specify a precise value
        if (minValue == maxValue) {
            return minValue;
        }
        double retValue;
        do {
            retValue = minValue + Math.random() * (maxValue - minValue + 1);
        } while (retValue > maxValue);
        return retValue;
    }

}
