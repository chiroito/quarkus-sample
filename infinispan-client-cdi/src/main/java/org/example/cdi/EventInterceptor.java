package org.example.cdi;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.annotation.Priority;

@Interceptor
@WriteEvent
@Priority(Interceptor.Priority.APPLICATION)
public class EventInterceptor {

    @AroundInvoke
    public Object logEvent(InvocationContext context) throws Exception {
        System.out.println("Before method: " + context.getMethod().getName());
        try {
            // メソッド本来の処理を実行
            return context.proceed();
        } finally {
            System.out.println("After method: " + context.getMethod().getName());
        }
    }
}
