package com.example.testbed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.R.integer;


public class Permutation {
	private static void printAll(List<Integer> srcList, List<Integer>permList, Set<List<Integer>> result)
	{
		String srcString = "";
		String permString = "";
		String resString = "";
		for(Integer src:srcList)
			srcString = srcString.concat(src.toString());
		System.out.println("srcString:"+srcString);
		if(permList != null){
			for(Integer src:permList)
				permString = permString.concat(src.toString());
			System.out.println("permString:"+permString);
		}
		for (List<Integer> list : result) {
			for(Integer src:list)
				resString = resString.concat(src.toString());
			resString = resString.concat(",");
		}
		System.out.println("resString :"+resString);
	}
	public static void perm(List<Integer> srcList, List<Integer>permList, Set<List<Integer>> result) {
		System.out.println("--------------------- perm start -----------------");
		printAll(srcList, permList, result);
		
		if(srcList == null || srcList.size() == 0 || permList == null)
			return;
		if(srcList.size() == 1){
			permList.add(srcList.get(0));
			srcList.clear();
			result.add(new ArrayList<Integer>(permList));
			permList.clear();
			System.out.println("get result:");
			printAll(srcList, permList, result);
			return;
		}
		else {
			for(int i = 0; i<srcList.size(); i++) {
				ArrayList<Integer> tempSrcList = new ArrayList<Integer>(srcList.size()-1);
				Integer item = srcList.get(i);
				int k=0;
				while(k<srcList.size())
				{
					if(srcList.get(k) != item){
						tempSrcList.add(srcList.get(k));
					}
					k++;
				}
				ArrayList<Integer> tempPermList =  new ArrayList<Integer>(permList);
				tempPermList.add(item);
				perm(tempSrcList, tempPermList, result);
			}
		}
			
	}
	public static void test(){
		 List<Integer> srcList = Arrays.asList(new Integer[]{1,2,3,4});
		 perm(srcList, new ArrayList<Integer>(), new HashSet<List<Integer>>());
	}
}
