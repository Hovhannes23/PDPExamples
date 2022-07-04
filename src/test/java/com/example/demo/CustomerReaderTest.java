package com.example.demo;

import com.example.demo.MockAndStub.Customer;
import com.example.demo.MockAndStub.CustomerReader;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerReaderTest {


    // изучаем работу mock и stub объектов
    @Test
    public void findFullName(){

        // создаем stub-объект
        Customer sampleCustomer = new Customer();
        sampleCustomer.setFirstName("Susan");
        sampleCustomer.setLastName("Ivanova");

        // создаем mock для подмены EntityManager
        EntityManager entityManager = mock(EntityManager.class);

        // заменяем результат выполнения метода stub-ом
        when(entityManager.find(Customer.class,1L)).thenReturn(sampleCustomer);

        // при тестировании класса CustomerReader заменяем его зависимость EntityManager  mock-объектом, чтобы
        // не зависеть от корректности его работы.
        CustomerReader customerReader = new CustomerReader();
        customerReader.setEntityManager(entityManager);

        // в результате тестируем работу метода findFullName, абстрагируясь от того, как мы обращаемся в БД и как
        // из БД достаются данные. В данном случае мы даже не настроили конфигурацию БД в классе CustomerReader.
        String fullName = customerReader.findFullName(1L);
        assertEquals("Susan Ivanova",fullName);
    }
}
