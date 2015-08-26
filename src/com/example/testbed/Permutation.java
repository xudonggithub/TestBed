package com.example.testbed;

import java.io.File;
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
			System.out.println("---->get result:");
			printAll(srcList, permList, result);
			permList = null;
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
	
	public static int question1(int[] inputArray) {
		int length = inputArray.length;
		int ret = 0;
		boolean[] backupArray = new boolean[length];
		int index = 0;
		while(index < length && index >= 0) {
			backupArray[index] = true;
			index += inputArray[index];
			if(backupArray[index])
				return -1;
			ret ++;
		}
		return ret;
	}
	
	public static int question2(int N) {
		int ret = -1;
		if (N <= 0) {
			return ret;
		}
		String binaryStr = Integer.toBinaryString(N);
		System.out.println("binary string:"+binaryStr);
		char[] binaryChars = binaryStr.toCharArray();
		int p = 1, k=0, boundary = binaryChars.length / 2 + binaryChars.length % 2;
		boolean find = true;
		while(p< boundary) {
			k = 0;
			
			while(k< boundary && k+p < binaryChars.length) {
				if(binaryChars[k] != binaryChars[k + p]) {
					find = false;
					break;
				}
				k++;
			}
			if(find)
				return p;
			p++;
		}
		return ret;
	}
	public static void test(){
		 List<Integer> srcList = Arrays.asList(new Integer[]{1,2,3,4});
		 perm(srcList, new ArrayList<Integer>(), new HashSet<List<Integer>>());
		 int ret = question1(new int[]{2,3,-1,1,3});
		 int ret2 = question1(new int[]{1,1,-1,1});
		 System.out.println("ret="+ret+",ret2"+ret2);
		 int ret3 = question2(955);
		 int ret4 = question2(102);
		 System.out.println("ret3="+ret3+",ret4"+ret4);
	}
	
}
