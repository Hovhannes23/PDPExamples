package com.example.demo;

import java.lang.reflect.Field;

public class ReflectionExample {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        Employee employee = new Employee();
        employee.setSalary(1000);
        employee.setName("Tom");
        employee.setEmail("tom@gmail.com");

        Field field = employee.getClass().getDeclaredField("name");
        field.setAccessible(true);
        String name = (String)field.get(employee);
        System.out.println(name);
        System.out.println(employee);

        field.set(employee, "Daniel");
        System.out.println(employee);

        Class clazz = Employee.class;
        Employee employee2 = (Employee)clazz.newInstance();
        employee2.setName("Jason");
        employee2.setSalary(1500);
        employee2.setEmail("ImSexyAndIKnowIt@gmail.com");
        System.out.println("employee2: " + employee2);
    }
}
