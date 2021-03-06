package eu.squadd.testing.objectspopulator.typeManufacturers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.squadd.testing.objectspopulator.api.AttributeMetadata;
import eu.squadd.testing.objectspopulator.api.DataProviderStrategy;
import eu.squadd.testing.objectspopulator.common.ScannerConstants;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Default byte type manufacturer.
 *
 * @author z094
 */
public class GenericTypeManufacturerImpl extends AbstractTypeManufacturer<Object> {

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(GenericTypeManufacturerImpl.class);

    @Override
    public Object getType(DataProviderStrategy strategy,
            AttributeMetadata attributeMetadata,
            Map<String, Type> genericTypesArgumentsMap) {

        Type[] genericArgs = attributeMetadata.getAttrGenericArgs();

        if (null == genericArgs) {
            throw new IllegalArgumentException("For generic type arguments, "
                    + "the attribute metadata should contain a non null Type[]");

        }

        Type genericAttributeType = attributeMetadata.getAttributeGenericType();

        Class<?> attributeType = attributeMetadata.getAttributeType();

        Type paremeterType = null;
        if (genericAttributeType instanceof ParameterizedType) {
            ParameterizedType parametrized = (ParameterizedType) genericAttributeType;
            Type[] arguments = parametrized.getActualTypeArguments();
            if (arguments.length > 0) {
                paremeterType = arguments[0];
            }
        } else if (attributeType.getTypeParameters().length > 0) {
            paremeterType = attributeType.getTypeParameters()[0];
        }

        if (paremeterType != null) {

            if (null == genericTypesArgumentsMap) {
                throw new IllegalArgumentException("The type arguments map in the wrapper cannot be null");
            }

            AtomicReference<Type[]> elementGenericTypeArgs
                    = new AtomicReference<Type[]>(ScannerConstants.NO_TYPES);
            return TypeManufacturerUtil.resolveGenericParameter(paremeterType,
                    genericTypesArgumentsMap, elementGenericTypeArgs);
        } else {
            LOG.error("{} is missing generic type argument, supplied {} {}",
                    genericAttributeType, genericTypesArgumentsMap,
                    Arrays.toString(genericArgs));
            return null;
        }
    }
}
