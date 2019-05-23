package cn.cwiz.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnoTest {

    public static void main(String[] args) throws ClassNotFoundException {
        // 要使用到反射中的相关内容
        Class c = Anno.class;
        Method[] method = c.getMethods();
        boolean flag = c.isAnnotationPresent(ForClass.class);
        if (flag) {
            ForClass first = (ForClass) c.getAnnotation(ForClass.class);
            System.out.println("ForClass Annotation:" + first.value() + "\n");
        }

        List<Method> list = new ArrayList<>();
        for (int i = 0; i < method.length; i++) {
            list.add(method[i]);
        }

        for (Method m : list) {
            ForMethod forMethod = m.getAnnotation(ForMethod.class);
            if (forMethod == null)
                continue;

            System.out.println("ForMethod annotation's\nname:\t" + forMethod.name() + "\nurl:\t" + forMethod.url());
        }

        List<Field> fieldList = new ArrayList<Field>();
        for (Field f : c.getDeclaredFields()) {// 访问所有字段
            ForField k = f.getAnnotation(ForField.class);
            System.out.println("----ForField anno: " + k.value());
        }
    }
}
