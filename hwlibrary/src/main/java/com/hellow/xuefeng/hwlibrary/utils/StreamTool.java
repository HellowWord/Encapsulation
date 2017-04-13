package com.hellow.xuefeng.hwlibrary.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class StreamTool {

	/**
	 * 从输入流中读取数据
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		if (inStream == null) return new byte[0];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) outStream.write(buffer, 0, len);
		byte[] data = outStream.toByteArray();// 网页的二进制数据
		outStream.close();
		inStream.close();
		return data;
	}

	/**
	 * 将输入流写成文件
	 * 
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static File writeFileFromInputStream(InputStream inStream) throws Exception {
//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while ((len = inStream.read(buffer)) != -1) outStream.write(buffer, 0, len);
//		byte[] data = outStream.toByteArray();// 网页的二进制数据
//		outStream.close();
//		inStream.close();
		return null;
	}

	
}
