package core.basesyntax.lib;

import core.basesyntax.dao.bet.BetDao;
import core.basesyntax.dao.user.UserDao;
import core.basesyntax.factory.Factory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Injector {

    public static Object getInstance(Class clazz) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException {

        Constructor constructor = clazz.getDeclaredConstructor();
        Object instance = constructor.newInstance();

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getAnnotation(Inject.class) != null) {
                field.setAccessible(true);
                if (field.getType().equals(BetDao.class)) {
                    field.set(instance, Factory.getBetDao());
                }
                if (field.getType().equals(UserDao.class)) {
                    field.set(instance, Factory.getUserDao());
                }
            }
        }
        return instance;
    }
}
