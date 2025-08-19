package com.mycompany.cse430_miniproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockItemTest {

    StockItem item;

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up...");
        item = new StockItem();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Tearing down...");
        item = null;
    }

    @Test // Default constructor values
    public void testDefaultConstructor() {
        System.out.println("testDefaultConstructor");
        assertEquals(0, item.getItemNumber());
        assertEquals("", item.getItemDescription());
        assertEquals(0.0, item.getItemPrice());
        assertEquals(0, item.getQuantity());
        assertNull(item.getUnit());
    }

    @Test // Constructor with all fields
    public void testParameterizedConstructorWithAllFields() {
        System.out.println("testParameterizedConstructorWithAllFields");
        StockItem newItem = new StockItem(1, "Pen", 10.5, Unit.PIECE, 100);
        assertEquals(1, newItem.getItemNumber());
        assertEquals("Pen", newItem.getItemDescription());
        assertEquals(10.5, newItem.getItemPrice());
        assertEquals(Unit.PIECE, newItem.getUnit());
        assertEquals(100, newItem.getQuantity());
    }

    @Test // Constructor without unit and quantity
    public void testParameterizedConstructorWithoutUnitAndQuantity() {
        System.out.println("testParameterizedConstructorWithoutUnitAndQuantity");
        StockItem newItem = new StockItem(2, "Notebook", 30.0);
        assertEquals(2, newItem.getItemNumber());
        assertEquals("Notebook", newItem.getItemDescription());
        assertEquals(30.0, newItem.getItemPrice());
        assertNull(newItem.getUnit());
        assertEquals(0, newItem.getQuantity());
    }

    @Test // Set & get item number
    public void testSetAndGetItemNumber() {
        System.out.println("testSetAndGetItemNumber");
        item.setItemNumber(5);
        assertEquals(5, item.getItemNumber());
    }

    @Test // Set & get description
    public void testSetAndGetItemDescription() {
        System.out.println("testSetAndGetItemDescription");
        item.setItemDescription("Eraser");
        assertEquals("Eraser", item.getItemDescription());
    }

    @Test // Set & get price
    public void testSetAndGetItemPrice() {
        System.out.println("testSetAndGetItemPrice");
        item.setItemPrice(15.75);
        assertEquals(15.75, item.getItemPrice());
    }

    @Test // Set & get unit
    public void testSetAndGetUnit() {
        System.out.println("testSetAndGetUnit");
        item.setUnit(Unit.PIECE);
        assertEquals(Unit.PIECE, item.getUnit());
    }

    @Test // Set & get quantity
    public void testSetAndGetQuantity() {
        System.out.println("testSetAndGetQuantity");
        item.setQuantity(500);
        assertEquals(500, item.getQuantity());
    }

    //CORNER CASES

    @Test // Negative item number
    public void testSetNegativeItemNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            item.setItemNumber(-1);
        }, "Negative item number should not be allowed");
    }

    @Test // Negative price
    public void testSetNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            item.setItemPrice(-50.0);
        }, "Negative price should throw exception");
    }

    @Test // Negative quantity
    public void testSetNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            item.setQuantity(-10);
        }, "Negative quantity should not be allowed");
    }

    @Test // Very large quantity
    public void testSetLargeQuantity() {
        item.setQuantity(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, item.getQuantity(), "Should handle very large quantities");
    }

    @Test // Null description
    public void testSetNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            item.setItemDescription(null);
        }, "Null description should not be allowed");
    }

    @Test // Empty description
    public void testSetEmptyDescription() {
        item.setItemDescription("");
        assertEquals("", item.getItemDescription(), "Empty description should be stored");
    }
}
