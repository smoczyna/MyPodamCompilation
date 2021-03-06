/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.testing.objectspopulator.typeManufacturers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;
import eu.squadd.testing.objectspopulator.common.ScannerDoubleValue;

/**
 *
 * @author z094
 */
public class BigDecimalTypeManufacturerImpl extends AbstractTypeManufacturer<BigDecimal> {

    private static final Logger LOG = LoggerFactory.getLogger(BigDecimalTypeManufacturerImpl.class);

    @Override
    public BigDecimal getType(DataProviderStrategy strategy, AttributeMetadata attributeMetadata, Map<String, Type> genericTypesArgumentsMap) {
        BigDecimal retValue = null;

        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {

            if (ScannerDoubleValue.class.isAssignableFrom(annotation.getClass())) {
                ScannerDoubleValue doubleStrategy = (ScannerDoubleValue) annotation;

                String numValueStr = doubleStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {

                    try {
                        retValue = BigDecimal.valueOf(Double.valueOf(numValueStr));
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
                    retValue = getBigDecimalInRange(minValue, maxValue, attributeMetadata);
                }
                break;
            }
        }
        if (retValue == null) {
            retValue = getBigDecimal(attributeMetadata);
        }
        return retValue;
    }

    public BigDecimal getBigDecimal(AttributeMetadata attributeMetadata) {
        double doubleVal;
        do {
            doubleVal = RANDOM.nextDouble();
        } while (doubleVal == 0.0);
        return BigDecimal.valueOf(doubleVal);
    }

    public BigDecimal getBigDecimalInRange(double minValue, double maxValue, AttributeMetadata attributeMetadata) {

        // This can happen. It's a way to specify a precise value
        if (minValue == maxValue) {
            return BigDecimal.valueOf(minValue);
        }
        double doubleValue;
        do {
            doubleValue = minValue + Math.random() * (maxValue - minValue + 1);
        } while (doubleValue > maxValue);
        return BigDecimal.valueOf(doubleValue);
    }
}
