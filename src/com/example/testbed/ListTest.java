package com.example.testbed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListTest {
	public static void testLinkedList() {
		List<Integer> addList = new ArrayList<Integer>();
		addList.add(1);
		addList.add(2);
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(3);
		list.add(4);
		list.add(5);
		list.addAll(0,addList);
		
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			System.out.println("value:"+integer);;
		}
		for (int i = 1; i < 1; i++) {
			System.out.println("test");
		}
	}
}
