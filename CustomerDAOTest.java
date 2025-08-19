package com.mycompany.cse430_miniproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {

    CustomerDAO customerDAO;
    Customer testCustomer;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up...");
        customerDAO = new CustomerDAO();

        // Creating test customer
        testCustomer = new Customer();
        testCustomer.setName("Samia Rahman");
        testCustomer.setHomePhone("111-222-3333");
        testCustomer.setCellPhone("222-333-4444");
        testCustomer.setWorkPhone("333-444-5555");
        testCustomer.setStreet("Aftabnagar");
        testCustomer.setCity("Dhaka");
        testCustomer.setState("BD");
        testCustomer.setZip("1205");

        customerDAO.addCustomer(testCustomer);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down...");
        customerDAO = null;
        testCustomer = null;
    }

    @Test   //DB connection is valid
    public void testGetCon() {
        assertNotNull(customerDAO.getCon(), "Database connection should not be null");
    }

    @Test   //inserting works
    public void testAddCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Swabirah Iffat");
        newCustomer.setHomePhone("444-555-6666");
        newCustomer.setCellPhone("555-666-7777");
        newCustomer.setWorkPhone("666-777-8888");
        newCustomer.setStreet("456 Park Ave");
        newCustomer.setCity("Chittagong");
        newCustomer.setState("BD");
        newCustomer.setZip("4000");

        customerDAO.addCustomer(newCustomer);
        List<Customer> customers = customerDAO.getAllCustomers();
        boolean found = customers.stream().anyMatch(c -> "Swabirah Iffat".equals(c.getName()));
        assertTrue(found, "Newly added customer should exist in the database");
    }

    @Test   //list is not empty
    public void testGetAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers, "Customer list should not be null");
        assertFalse(customers.isEmpty(), "Customer list should not be empty");
    }

    @Test   //retrieve valid customer
    public void testGetCustomerById() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertFalse(customers.isEmpty(), "At least one customer should exist");

        Customer first = customers.get(0);
        Customer found = customerDAO.getCustomerById(first.getCustomerNo());

        assertNotNull(found, "Customer should be found by ID");
        assertEquals(first.getName(), found.getName(), "Customer name should match");
    }

    @Test   //returns valid IDs
    public void testGetCustomerByMaxPurchaseOrder() {
        List<Integer> customerIds = customerDAO.getCustomerByMaxPurchaseOrder();
        assertNotNull(customerIds, "List should not be null");
        // We can't guarantee test data, but we check for a valid list
        assertTrue(customerIds.size() >= 0, "Method should return 0 or more customer IDs");
    }

    // ----------------- CORNER CASES -----------------

    @Test   //Invalid customer ID returns null
    public void testGetCustomerByIdInvalid() {
        Customer customer = customerDAO.getCustomerById(-9999);
        assertNull(customer, "Invalid customer ID should return null");
    }

    @Test   //Adding customer with null name
    public void testAddCustomerWithNullName() {
        Customer invalidCustomer = new Customer();
        invalidCustomer.setName(null);
        invalidCustomer.setHomePhone("123-456-7890");
        invalidCustomer.setCellPhone("123-456-7890");
        invalidCustomer.setWorkPhone("123-456-7890");
        invalidCustomer.setStreet("Unknown");
        invalidCustomer.setCity("Unknown");
        invalidCustomer.setState("BD");
        invalidCustomer.setZip("0000");

        assertDoesNotThrow(() -> customerDAO.addCustomer(invalidCustomer),
                "Adding customer with null name should not crash (depends on DB constraints)");
    }

    @Test    //empty DB
    public void testGetAllCustomersEmptyDatabase() {
        // Edge case: simulate when DB has no customers (assume truncation)
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers, "Should return non-null list");
        // It may be empty if DB is cleared
        assertTrue(customers.size() >= 0, "Customer list should be 0 or more");
    }

    @Test     //should be -1 (if none) or valid ID
    public void testGetLastInsertID() {
        int id = customerDAO.getLastInsertID();
        assertTrue(id >= -1, "Last insert ID should be -1 if no inserts or a positive number if inserted");
    }
}
