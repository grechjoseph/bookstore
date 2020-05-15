package com.jg.bookstore.config.security.context;

import java.util.Objects;

/**
 * Manages a Context object for a current Thread (Api Call).
 */
public class ContextHolder {

    private static final ThreadLocal<Context> THREADLOCAL = new InheritableThreadLocal<>();

    public ContextHolder() {

    }

    public static Context get() {
        return Objects.isNull(THREADLOCAL.get()) ? new Context() : THREADLOCAL.get();
    }

    public static void set(Context context) {
        THREADLOCAL.set(context);
    }

    public static void clear() {
        THREADLOCAL.remove();
    }
}
