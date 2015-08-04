package com.example.testbed;

import java.nio.ByteBuffer;

import android.R.integer;
import android.renderscript.Byte2;

public class TestByteBuffer {
	public static void testCopyEfficiency() {
		int size = 1024*1024*2;
		byte[] srcBytes = new byte[size];
		for (int i = 0; i < srcBytes.length; i++) {
			srcBytes[i] = (byte)i;
		}
		
		long time = System.currentTimeMillis();
		byte[] desBytes = new byte[size];
		System.out.println("allocate byte array "+size+" spend:"+(System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		System.arraycopy(srcBytes, 0, desBytes, 0, desBytes.length);
		System.out.println("copy array "+size+" spend:"+(System.currentTimeMillis() - time));

		time = System.currentTimeMillis();
		ByteBuffer desBuffer = ByteBuffer.allocate(size);
		System.out.println("allocate ArrayByteBuffer "+size+" spend:"+(System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		desBuffer.put(srcBytes);
		System.out.println("copy ByteBuffer "+size+" spend:"+(System.currentTimeMillis() - time));
		
		time = System.currentTimeMillis();
		ByteBuffer desDirectBuffer = ByteBuffer.allocateDirect(size);
		System.out.println("allocate DirectByteBuffer "+size+" spend:"+(System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		desDirectBuffer.put(srcBytes);
		System.out.println("copy DirectByteBuffer "+size+" spend:"+(System.currentTimeMillis() - time));
	}
	
	public static void testByteBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(5);
		for (int i = 0; i < 4; i++) {
			buffer.put((byte)i);
		}
		System.out.println("position:"+buffer.position()+",limit:"+buffer.limit());
		byte[] byte1 = buffer.array();
		for (int b = 0; b < byte1.length; b++) {
			System.out.println("byte1["+b+"]:"+byte1[b]);
		}
		buffer.flip();
		buffer.limit(2);
		System.out.println("after flip, position:"+buffer.position()+",limit:"+buffer.limit());
		
		ByteBuffer sliceBuffer = buffer.slice();
		System.out.println("after slice, sliceBuffer capacity:"+sliceBuffer.capacity());
		byte[] sliceBytes = sliceBuffer.array();
		for (int b = 0; b < sliceBytes.length; b++) {
			System.out.println("sliceBytes["+b+"]:"+sliceBytes[b]);
		}
		
		byte[] byte2 = new byte[buffer.limit()-buffer.position()];
		buffer.get(byte2);
		for (int b = 0; b < byte2.length; b++) {
			System.out.println("byte2["+b+"]:"+byte2[b]);
		}
		
	}
	
	public static native void testDirectByteBuffer();
}
