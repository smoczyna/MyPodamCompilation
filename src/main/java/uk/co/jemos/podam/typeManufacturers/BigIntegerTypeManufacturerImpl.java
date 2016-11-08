/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.jemos.podam.typeManufacturers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.jemos.podam.api.AttributeMetadata;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamUtils;
import uk.co.jemos.podam.common.PodamConstants;
import uk.co.jemos.podam.common.PodamLongValue;

/**
 *
 * @author smoczna
 */
public class BigIntegerTypeManufacturerImpl extends AbstractTypeManufacturer<BigInteger> {

    private static final Logger LOG = LoggerFactory.getLogger(BigIntegerTypeManufacturerImpl.class);

    @Override
    public BigInteger getType(DataProviderStrategy strategy, AttributeMetadata attributeMetadata, Map<String, Type> genericTypesArgumentsMap) {   
        Long longValue = null;
        for (Annotation annotation : attributeMetadata.getAttributeAnnotations()) {
            if (PodamLongValue.class.isAssignableFrom(annotation.getClass())) {
                PodamLongValue longStrategy = (PodamLongValue) annotation;
                String numValueStr = longStrategy.numValue();
                if (null != numValueStr && !numValueStr.isEmpty()) {
                    try {
                        longValue = Long.valueOf(numValueStr);
                    } catch (NumberFormatException nfe) {
                        String errMsg = PodamConstants.THE_ANNOTATION_VALUE_STR
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
                    longValue = getLongInRange(minValue, maxValue);
                }
                break;
            }
        }
        if (longValue == null) {
            longValue = getLong();
        }
        return BigInteger.valueOf(longValue);
    }
       
    private Long getLong() {
        return System.nanoTime();
    }

    private Long getLongInRange(long minValue, long maxValue) {
        return PodamUtils.getLongInRange(minValue, maxValue);
    }
    
}
