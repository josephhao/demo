package cn.cwiz.study.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HttpResultProxy  extends HttpResult implements InvocationHandler {

    private Object proxyobj;

    public HttpResultProxy(Object obj)
    {
        proxyobj = obj;
    }

    public static Object getProxy(Object obj)
    {
        Class<?> cls = obj.getClass();

        return Proxy.newProxyInstance(cls.getClassLoader(),
                cls.getInterfaces(), new HttpResultProxy(obj));
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        System.out.println("before calling " + method);

        if (args != null)
        {
            for (int i = 0; i < args.length; i++)
            {
                System.out.println(args[i] + "");
            }
        }
        Object object = method.invoke(proxyobj, args);

        System.out.println("after calling " + method);
        return object;
    }

}
