package quirks;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodReferenceNameIntrospection {

  public static void main(String[] args) {
    // Prints "quirks/MethodReferenceNameIntrospection::foobar"
    System.out.println(name(MethodReferenceNameIntrospection::foobar));

    // Prints "quirks/MethodReferenceNameIntrospection::lambda$main$cefee661$1"
    System.out.println(name(() -> foobar()));
  }

  private interface SerializableCallable<V> extends Callable<V>, Serializable {

  }

  private static String foobar() {
    return "sup";
  }

  /**
   * http://stackoverflow.com/a/21879031
   */
  private static String name(SerializableCallable<?> callable) {
    for (Class<?> cl = callable.getClass(); cl != null; cl = cl.getSuperclass()) {
      try {
        final Method m = cl.getDeclaredMethod("writeReplace");
        m.setAccessible(true);
        final Object replacement = m.invoke(callable);
        if (!(replacement instanceof SerializedLambda)) {
          break; // custom interface implementation
        }
        final SerializedLambda l = (SerializedLambda) replacement;
        return l.getImplClass() + "::" + l.getImplMethodName();
      } catch (NoSuchMethodException ignored) {
      } catch (IllegalAccessException | InvocationTargetException e) {
        break;
      }
    }
    return "unknown property";
  }
}
