package dk.logb.dynamic.proxy.example;


import java.lang.annotation.Retention;
import java.util.EnumSet;

enum TransactionType {
   REQUIRED, NOT_SUPPORTED, SUPPORTS, MANDATORY, REQUIRES_NEW, NEVER, NESTED
}
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@interface Transactional {
    TransactionType value() default TransactionType.REQUIRED;
}

interface CustomerService {
    @Transactional(TransactionType.REQUIRED)
    void createCustomer(String name);
    @Transactional(TransactionType.NOT_SUPPORTED)
    void printCustomer(String name);
}
class CustomerServiceImpl implements  CustomerService {
    @Override
    public void createCustomer(String name) {
        System.out.println("\tCreating customer - as an example of transactional business logic: " + name);
    }

    @Override
    public void printCustomer(String name) {
        System.out.println("\tPrinting customer - as an example of non-transactional business logic: " + name);
    }
}

class CustomerServiceFactory {
    //get a dynamic proxy for the CustomerService
    //the proxy could be injected instead by a framework like Spring or CDI
    //the proxy will delegate to the CustomerServiceInvocationHandler which delegates to the CustomerServiceImpl


    public static CustomerService getCustomerService() {
        return (CustomerService) java.lang.reflect.Proxy.newProxyInstance(
                CustomerService.class.getClassLoader(),
                new Class[]{CustomerService.class},
                new CustomerServiceInvocationHandler(new CustomerServiceImpl()));
    }

    private static class CustomerServiceInvocationHandler implements java.lang.reflect.InvocationHandler {
        private final CustomerService customerService;
        public CustomerServiceInvocationHandler(CustomerService customerService) {
            this.customerService = customerService;
        }
        @Override
        public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args) throws Throwable {
            method.setAccessible(true);
            Transactional transactional = method.getAnnotation(Transactional.class);
            EnumSet<TransactionType> required = EnumSet.of(TransactionType.REQUIRED, TransactionType.REQUIRES_NEW);

            if (required.contains(transactional)) {
                System.out.println("Transaction started");
                Object result = method.invoke(customerService, args);
                System.out.println("Transaction committed");
                return result;
            } else {
                System.out.println("Transaction not started");
                Object result = method.invoke(customerService, args);
                System.out.println("No need to commit transaction");
                return result;
            }
        }
    }
}

public class TxProxy {

    public static void main(String[] args) {
        //we get a dynamic proxy for the CustomerService.
        //The proxy could be injected instead by a framework like Spring or CDI
        CustomerService customerService = CustomerServiceFactory.getCustomerService();
        customerService.createCustomer("Donald");
        customerService.printCustomer("Ada");
    }
}
