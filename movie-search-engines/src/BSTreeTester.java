import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class BSTreeTester {
    BSTree<Integer> myIntTree;
    BSTree<String> myStringTree;

    @org.junit.Before
    public void setUp() throws Exception {
        myIntTree = new BSTree<>();
        myIntTree.insert(50);
        myIntTree.insert(10);
        myIntTree.insert(70);
        myIntTree.insert(30);
        myIntTree.insert(5);
        myIntTree.insert(80);
        myIntTree.insert(55);
        myIntTree.insert(60);
        myIntTree.insert(40);
        myStringTree = new BSTree<>();
        myStringTree.insert("E");
        myStringTree.insert("A");
        myStringTree.insert("D");
        myStringTree.insert("F");
        myStringTree.insert("C");
        myStringTree.insert("B");
    }

    @org.junit.Test
    public void getRoot() {
        assertEquals(new Integer(50), myIntTree.getRoot().getKey());
        BSTree<Integer> myTestTree = new BSTree<>();
        assertNull(myTestTree.getRoot());
        assertEquals("E", myStringTree.getRoot().getKey());
    }

    @org.junit.Test
    public void getSize() {
        assertEquals(9, myIntTree.getSize());
        BSTree<Integer> myTestTree = new BSTree<>();
        assertEquals(0, myTestTree.getSize());
        assertEquals(6, myStringTree.getSize());
    }

    @org.junit.Test
    public void insert() {
        assertTrue(myIntTree.insert(100));;
        BSTree<Integer> myTestTree = new BSTree<>();
        myTestTree.insert(120);
        assertTrue(myTestTree.findKey(120));
        assertFalse(myStringTree.insert("E"));
    }

    @org.junit.Test (expected = NullPointerException.class)
    public void insertNull() throws NullPointerException {
        myIntTree.insert(null);
    }

    @org.junit.Test
    public void findKey() {
        assertTrue(myIntTree.findKey(50));
        BSTree<Integer> myTestTree = new BSTree<>();
        assertFalse(myTestTree.findKey(50));
        assertTrue(myStringTree.findKey("A"));
    }

    @org.junit.Test (expected = NullPointerException.class)
    public void findKeyNull() throws NullPointerException {
        myIntTree.findKey(null);
    }

    @org.junit.Test
    public void insertData() {
        myIntTree.insertData(50, 123);
        LinkedList<Integer> expected1 = new LinkedList<>();
        expected1.add(123);
        assertEquals(expected1, myIntTree.findDataList(50));
        LinkedList<String> expected2 = new LinkedList<>();
        expected2.add("hello");
        myStringTree.insertData("A", "hello");
        assertEquals(expected2, myStringTree.findDataList("A"));
        BSTree<Integer> myTestTree = new BSTree<>();
        myTestTree.insert(3);
        LinkedList<Integer> expected3 = new LinkedList<>();
        myTestTree.insertData(3, 0);
        myTestTree.insertData(3, 0);
        expected3.add(0);
        expected3.add(0);
        assertEquals(expected3, myTestTree.findDataList(3));
    }

    @org.junit.Test (expected = NullPointerException.class)
    public void insertDataNullKey() throws NullPointerException{
        myIntTree.insertData(70, null);
    }

    @org.junit.Test (expected = NullPointerException.class)
    public void insertDataNullData() throws NullPointerException{
        myIntTree.insertData(null, 123);
    }

    @org.junit.Test (expected = IllegalArgumentException.class)
    public void insertDataBadKey() throws NullPointerException{
        myIntTree.insertData(77, 123);
    }

    @org.junit.Test
    public void findDataList() {
        myIntTree.insertData(50, 777);
        LinkedList<Integer> expected1 = new LinkedList<>();
        expected1.add(777);
        assertEquals(expected1, myIntTree.findDataList(50));

        LinkedList<String> expected2 = new LinkedList<>();
        expected2.add("hi");
        myStringTree.insertData("B", "hi");
        assertEquals(expected2, myStringTree.findDataList("B"));

        BSTree<Integer> myTestTree = new BSTree<>();
        myTestTree.insert(88);
        LinkedList<Integer> expected3 = new LinkedList<>();
        myTestTree.insertData(88, 56);
        myTestTree.insertData(88, 79);
        expected3.add(56);
        expected3.add(79);
        assertEquals(expected3, myTestTree.findDataList(88));
    }

    @org.junit.Test (expected = NullPointerException.class)
    public void findDataListKeyNull() throws NullPointerException{
        myIntTree.insertData(50, 777);
        myIntTree.findDataList(null);
    }

    @org.junit.Test (expected = IllegalArgumentException.class)
    public void findDataListBadKey() throws IllegalArgumentException{
        myIntTree.insertData(50, 777);
        myIntTree.insertData(0, 777);
    }

    @org.junit.Test
    public void findHeight() {
        assertEquals(3, myIntTree.findHeight());
        assertEquals(4, myStringTree.findHeight());
        BSTree<Integer> myTestTree = new BSTree<>();
        assertEquals(-1, myTestTree.findHeight());
    }

    @org.junit.Test
    public void iterator() {
        Iterator<Integer> myIntTreeIter = myIntTree.iterator();
        myIntTreeIter.next();
        myIntTreeIter.next();
        assertEquals(new Integer(30), myIntTreeIter.next());
        Iterator<String> myStringTreeIter = myStringTree.iterator();
        assertEquals("A", myStringTreeIter.next());
        BSTree<Integer> myTestTree = new BSTree<>();
        myTestTree.insert(3);
        myTestTree.insert(2);
        myTestTree.insert(1);
        Iterator<Integer> myTestTreeIter = myTestTree.iterator();
        myTestTreeIter.next();
        myTestTreeIter.next();
        assertEquals(new Integer(3), myTestTreeIter.next());
    }

    @org.junit.Test
    public void intersection() {
    }

    @org.junit.Test
    public void levelMax() {
    }
}