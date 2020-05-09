package com.jg.bookstore.config.context;

import java.util.Objects;

public class ContextHolder {

    private static final ThreadLocal<Context> THREADLOCAL = new InheritableThreadLocal<>();

    public ContextHolder() {

    }

    public static Context getContext() {
        return Objects.isNull(THREADLOCAL.get()) ? new Context() : THREADLOCAL.get();
    }

    public static void setContext(Context context) {
        THREADLOCAL.set(context);
    }

    public static void clear() {
        THREADLOCAL.remove();
    }

}
