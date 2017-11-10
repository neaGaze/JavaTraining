package com.neagaze.imcs;

import com.neagaze.imcs.entities.Address;
import com.neagaze.imcs.entities.CardType;
import com.neagaze.imcs.entities.Customer;
import com.neagaze.imcs.entities.PaymentMethod;
import com.neagaze.imcs.service.ConcreteDbService;
import com.neagaze.imcs.service.DatabaseServiceInterface;
import com.neagaze.imcs.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by neaGaze on 11/8/17.
 */
public class App {

    private static Customer createCustomer() {

        Customer customer = new Customer();
        customer.setName("Michael Baptista");

        Address address = new Address();
        address.setAptNo("23");
        address.setStreetNo(715);
        address.setStreetName("Pioneer pkwy");
        address.setCity("Arlington");
        address.setState("TX");
        address.setZip(76001);
        customer.setAddress(address);
        address.setCustomer(customer);

        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setCardName("WELLS FARGO");
        paymentMethod.setCardNumber(new Long(566));
        paymentMethod.setCardType(CardType.CREDIT_CARD);
        paymentMethod.setDateFrom(new Date());
        paymentMethod.setCustomer(customer);

        PaymentMethod paymentMethod2 = new PaymentMethod();
        paymentMethod2.setCardName("CHASE");
        paymentMethod2.setCardNumber(new Long(6789000));
        paymentMethod2.setCardType(CardType.DEBIT_CARD);
        paymentMethod2.setDateFrom(new Date());
        paymentMethod2.setCustomer(customer);

        List<PaymentMethod> list = new ArrayList<PaymentMethod>();
        list.add(paymentMethod);
        list.add(paymentMethod2);
        customer.setPaymentMethodList(list);

        return customer;
    }

    public static void main(String[] args) {

        DatabaseServiceInterface service = new ConcreteDbService();
        Customer customer = createCustomer();

        Scanner scan = new Scanner(System.in);
        int id;
        boolean shouldContinueLoop = true;
        while(shouldContinueLoop) {
            System.out.println("1. Add customer\n2. Delete Customer\n3. Fetch Customer + Address + PaymentMethod\n" +
                "4. Fetch Customer + Address");
            id = scan.nextInt();

            switch (id) {
                case 1:
                    // adding customer
                    service.addCustomer(customer);
                    System.out.println("The customerId in main.app is: " + customer.getId());
                    break;

                case 2:
                    // now deleting
                    service.deleteCustomer(customer);
                    break;

                case 3:
                    // now fetching
                    System.out.println("What is the id of the customer you want to fetch?");
                    int custId = scan.nextInt();
                    Customer c1 = service.getCustomer(custId);
                    System.out.println(c1.getName());
                    System.out.println(c1.getAddress());
                    if(c1.getPaymentMethodList() != null)
                    for (PaymentMethod p : c1.getPaymentMethodList()) {
                        System.out.println(p);
                    }
                    break;

                case 4:
                    // now fetching
                    System.out.println("What is the id of the customer you want to fetch?");
                    int custId1 = scan.nextInt();
                    Customer c2 = service.getCustomerWithAddress(custId1);
                    System.out.println(c2.getName());
                    System.out.println(c2.getAddress());
                    break;

                case 9:
                    shouldContinueLoop = false;
                    break;

                default:
                    shouldContinueLoop = false;
                    break;
            }
        }
    }
}
