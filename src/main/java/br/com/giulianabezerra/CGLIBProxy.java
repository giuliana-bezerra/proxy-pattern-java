package br.com.giulianabezerra;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLIBProxy {
  public static void main(String[] args) {
    GreetingServiceImpl realService = new GreetingServiceImpl();
    GreetingServiceImpl proxyService = ProxyFactoryCGLIB.createProxy(
        realService, GreetingServiceImpl.class);

    System.out.println("Real Service: ");
    System.out.println(realService.greet("World"));
    System.out.println("------------------------------------");
    System.out.println("Proxy: ");
    System.out.println(proxyService.greet("World"));
  }
}

class GreetingServiceImpl {
  public String greet(String name) {
    return "Hello, " + name + "!";
  }
}

class LoggingMethodInterceptor implements MethodInterceptor {

  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
    System.out.println(
        "Method " + method.getName() + " is called with args " + (args != null ? Arrays.toString(args) : "null"));
    Object result = proxy.invokeSuper(obj, args);
    System.out.println(
        "Method " + method.getName() + " return " + result);

    return result;
  }

}

class ProxyFactoryCGLIB {
  public static <T> T createProxy(T target, Class<T> superclassType) {
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(superclassType);
    enhancer.setCallback(new LoggingMethodInterceptor());
    return (T) enhancer.create();
  }
}