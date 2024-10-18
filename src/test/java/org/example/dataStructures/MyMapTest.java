package org.example.dataStructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {

    MyMap<Integer,Integer> newMap;


    @BeforeEach
    void setUp() {
     newMap = new MyMap<>();
    }
    @Test
    void testSize(){
        assertEquals(0,newMap.size());
    }
    @Test
    void testAdding(){
        newMap.put(1,2);
        newMap.put(2,3);
        assertEquals(2,newMap.size());
        assertFalse(newMap.isEmpty());
        newMap.put(2,4);
        assertEquals(2,newMap.size());
        assertEquals(4,newMap.get(2));
    }
    @Test
    void testIsEmpty(){
        assertTrue(newMap.isEmpty());
    }
    @Test
    void testGet(){
        newMap.put(1,2);
        newMap.put(2,3);
        assertEquals(2,newMap.get(1));
        assertEquals(3,newMap.get(2));
        assertNull(newMap.get(4));
    }
    @Test
    void testRemove(){
        newMap.put(1,2);
        newMap.put(2,3);
        newMap.remove(2);
        assertEquals(1,newMap.size());
        assertFalse(newMap.containsValue(3));
        assertFalse(newMap.containsKey(2));
        assertTrue(newMap.containsValue(2));
        assertTrue(newMap.containsKey(1));
    }
    @Test
    void testKeys(){
        newMap.put(1,2);
        newMap.put(2,3);
        newMap.put(3,4);
        ArrayList<Integer> keys = new ArrayList<>();
        keys.add(1);
        keys.add(2);
        keys.add(3);
        ArrayList<Integer> val = new ArrayList<>();
        val.add(2);
        val.add(3);
        val.add(4);

        assertEquals(keys,newMap.keys());
        assertEquals(val,newMap.values());
    }
    @Test
    void testClear(){
        newMap.put(1,2);
        newMap.put(2,3);
        assertFalse(newMap.isEmpty());
        newMap.clear();
        assertTrue(newMap.isEmpty());
    }

}