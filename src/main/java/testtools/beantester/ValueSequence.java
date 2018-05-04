package testtools.beantester;

import testtools.beantester.internal.Creator;

/**
 *
 * @author stuart
 */
public class ValueSequence implements Creator {
    private final Object[] valueOrCreatorList;
    private int index;

    public ValueSequence(Object[] valueOrCreator) {
        this.valueOrCreatorList = valueOrCreator;
        this.index = 0;
    }
    
    @Override
    public Object create(Class parameterType, String beanName, String propertyName) {
        if ((valueOrCreatorList == null) || (valueOrCreatorList.length == 0)) {
            return null;
        }
        Object tmp = valueOrCreatorList[index];
        index++;
        if (index >= valueOrCreatorList.length) {
            index = 0;
        }
        return tmp;
    }
}
