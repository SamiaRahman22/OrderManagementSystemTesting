package com.mycompany.cse430_miniproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class OrderItemTest {

    private OrderItem orderItem;
    private StockItem stockItem;

    @BeforeEach
    public void setUp() {
        stockItem = new StockItem(101, "Pen", 10.0);
        orderItem = new OrderItem(1, 101, 5, 1001, stockItem);
    }

    // Constructor Tests 

    @Test // Constructor with stock item number, quantity, and PO number
    public void testConstructorWithStockItemOnly() {
        OrderItem oi = new OrderItem(101, 3, 1001);
        assertEquals(101, oi.getStockItemNumber());
        assertEquals(3, oi.getNumberOfItems());
        assertEquals(1001, oi.getPoNumber());
    }

    //Getter Tests

    @Test // Getters should return correct values
    public void testGetters() {
        assertEquals(1, orderItem.getOrderItemNumber());
        assertEquals(101, orderItem.getStockItemNumber());
        assertEquals(5, orderItem.getNumberOfItems());
        assertEquals(1001, orderItem.getPoNumber());
        assertEquals(stockItem, orderItem.getStockItem());
    }

    //Setter Tests

    @Test // Setters should update all values correctly
    public void testSetters() {
        StockItem newStockItem = new StockItem(102, "Pencil", 5.0);
        orderItem.setOrderItemNumber(2);
        orderItem.setStockItemNumber(102);
        orderItem.setNumberOfItems(10);
        orderItem.setPoNumber(2002);
        orderItem.setStockItem(newStockItem);

        assertEquals(2, orderItem.getOrderItemNumber());
        assertEquals(102, orderItem.getStockItemNumber());
        assertEquals(10, orderItem.getNumberOfItems());
        assertEquals(2002, orderItem.getPoNumber());
        assertEquals(newStockItem, orderItem.getStockItem());
    }

    //Business Logic Tests-

    @Test // Total should calculate quantity * price
    public void testGetTotal() {
        double expected = 5 * 10.0;
        assertEquals(expected, orderItem.getTotal());
    }

    @Test // Calling getTotal with null StockItem should throw exception
    public void testGetTotalWhenStockItemIsNull() {
        OrderItem oi = new OrderItem(101, 2, 2001);
        assertThrows(NullPointerException.class, oi::getTotal);
    }

    //Corner Case Tests

    @Test // Total should be 0 when quantity is 0
    public void testZeroQuantity() {
        orderItem.setNumberOfItems(0);
        assertEquals(0.0, orderItem.getTotal());
    }

    @Test // Negative quantity should not produce positive total
    public void testNegativeQuantity() {
        orderItem.setNumberOfItems(-5);
        assertTrue(orderItem.getTotal() <= 0);
    }

    @Test // Null stock item should throw exception when calculating total
    public void testNullStockItem() {
        orderItem.setStockItem(null);
        assertThrows(NullPointerException.class, orderItem::getTotal);
    }

    @Test // Very large quantity should not crash and should produce a valid total
    public void testLargeQuantity() {
        orderItem.setNumberOfItems(Integer.MAX_VALUE);
        double total = orderItem.getTotal();
        assertTrue(total > 0);
    }

    @Test // Zero price stock item should make total 0
    public void testZeroPriceStockItem() {
        StockItem freeItem = new StockItem(103, "Gift", 0.0);
        orderItem.setStockItem(freeItem);
        orderItem.setNumberOfItems(10);
        assertEquals(0.0, orderItem.getTotal());
    }

    @Test // Negative price stock item should not produce a positive total
    public void testNegativePriceStockItem() {
        StockItem faultyItem = new StockItem(104, "Buggy Item", -50.0);
        orderItem.setStockItem(faultyItem);
        orderItem.setNumberOfItems(2);
        assertTrue(orderItem.getTotal() <= 0);
    }
}
