package eu.squadd.testing.objectspopulator.api;

import java.lang.reflect.Type;

/**
 * Adapter pattern for boilerplate code when creating an external factory
 *
 * @author Marco Tedone
 *
 * @since 5.2.1
 */
public abstract class AbstractExternalFactory implements ScannerFactory {

    @Override
    public <T> T manufacturePojoWithFullData(Class<T> pojoClass,
            Type... genericTypeArgs) {
        return this.manufacturePojo(pojoClass, genericTypeArgs);
    }

    @Override
    public DataProviderStrategy getStrategy() {
        return null;
    }

    @Override
    public ScannerFactory setStrategy(DataProviderStrategy strategy) {
        return this;
    }

    @Override
    public ClassInfoStrategy getClassStrategy() {
        return null;
    }

    @Override
    public ScannerFactory setClassStrategy(
            ClassInfoStrategy classInfoStrategy) {
        return this;
    }

    @Override
    public ScannerFactory getExternalFactory() {
        return null;
    }

    @Override
    public ScannerFactory setExternalFactory(ScannerFactory externalFactory) {
        return this;
    }

}
