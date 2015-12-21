package com.example.testbed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;


import android.R.integer;


public class Permutation {
	private static void printAll(ArrayList<Integer> srcList, ArrayList<Integer>permList, Set<ArrayList<Integer>> result)
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
	}//List<Integer> srcList, List<Integer> permlist, Set<List<Integer>> resList
	public static void perm(ArrayList<Integer> srcList, ArrayList<Integer>permList, Set<ArrayList<Integer>> resList) {
		if(srcList == null || srcList.size() == 0 || permList == null)
			return;
		if(srcList.size() == 1){
			permList.add(srcList.get(0));
			srcList.clear();
			resList.add(new ArrayList<Integer>(permList));
			permList.clear();
			System.out.println("---->get result:");
			printAll(srcList, permList, resList);
			permList = null;
			srcList = null;
			return;
		}
		else {
			for(int i = 0; i<srcList.size(); i++) {
				Integer item = srcList.get(i);
				boolean bSame = false;
				for (int j = i-1; j >=0; j--) {
					if(item == srcList.get(j)){
						bSame = true;
						break;
					}
				}
				if(bSame)
					continue;
				ArrayList<Integer> tempSrcList = new ArrayList<Integer>(srcList);
				tempSrcList.remove(i);
				ArrayList<Integer> tempPermList =  new ArrayList<Integer>(permList);
				tempPermList.add(item);
				perm(tempSrcList, tempPermList, resList);
			}
			srcList.clear();
			srcList = null;
			permList.clear();
			permList = null;
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
			if(index < 0 || index >= length)
				return ret+1;
			if(backupArray[index])
				return -1;
			
			ret ++;
		}
		return ret;
	}
	
	public static int question22(int N) {
		int ret = -1;
		if (N <= 0) {
			return ret;
		}
		System.out.println("N="+Integer.toBinaryString(N));
		int binaryCount = Integer.toBinaryString(N).length();
		int boundary = binaryCount / 2 + binaryCount % 2;
		int count = 1, tempL, tempR;
		while(count < boundary) {
//			tempL = N << count;
//			System.out.println("count="+count+",tempL:"+Integer.toBinaryString(tempL));
			tempR = N >> count;
			System.out.println("count="+count+",tempR:"+Integer.toBinaryString(tempR));

			tempL = tempR | N;
			System.out.println("count="+count+",tempL:"+Integer.toBinaryString(tempL));
			if(tempL == N) {
				return count;
			}
			 count ++;
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
			find = true;
			while(k+p < binaryChars.length) {
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
	
	private static long question3() {
		ArrayList<Integer> srcList = new ArrayList<Integer>(Arrays.asList(new Integer[]{5,3,-1,5}));
		HashSet<ArrayList<Integer>> resultSet = new HashSet<ArrayList<Integer>>();
		perm(srcList, new ArrayList<Integer>(), resultSet);
		int index = 0;
		long max = 0, tempResult = 0;
		for (List<Integer> list : resultSet) {
			index = 0;
			tempResult = 0;
			while((index+1) < list.size()){
				tempResult += Math.abs(list.get(index) - list.get(++ index));
			}
			if(tempResult > max)
				max = tempResult;
		}
		return max;
	}
	
	public static void test(){
		 
		 int ret = question1(new int[]{2,3,-1,1,3});
		 int ret2 = question1(new int[]{1,1,-1,1});
		 System.out.println("ret="+ret+",ret2="+ret2);
		 int ret3 = question2(955);
		 int ret4 = question2(102);
		 int ret5 = question2(1022);
		 int ret33 = question22(955);
		 int ret44 = question22(1022);
		 System.out.println("ret3="+ret3+",ret4="+ret4+", ret5="+ret5);
		 System.out.println("ret33="+ret33+",ret44="+ret44);
//		 long ret5 = question3();
//		 System.out.println("ret5="+ret5);
	}
	
}
