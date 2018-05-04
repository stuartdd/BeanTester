package testtools.beantester;

import testtools.beantester.internal.Creator;

/**
 * Java Bean Tester library May 2018
 * GitHub "https://github.com/stuartdd/beanUnitTester"
 * @author stuartdd
 */
public class ValueSequence implements Creator {
    private final Object[] valueOrCreatorList;
    private int index;

    public ValueSequence(Object[] valueOrCreator) {
        this.valueOrCreatorList = valueOrCreator;
        this.index = 0;
    }
    
    @Override
    public Object create(Class classUnderTest, String propertyName) {
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
