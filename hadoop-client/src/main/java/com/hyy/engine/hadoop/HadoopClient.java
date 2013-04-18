package com.hyy.engine.hadoop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HadoopClient {
	public static void main(String[] args) {
		String src = "d://person.thrift";
		String dst = "hdfs://10.12.12.97:9000/user/henry/input/qq3.txt";

		try {
			uploadToHdfs(src, dst);

			deleteFromHdfs(dst);

			dst = "hdfs://10.12.12.97:9000/user/henry/input";
			getDirectoryFromHdfs(dst);

			readFromHdfs(dst);
			
			System.out.println("SUCCESS");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	/**
	 * upload to hdfs
	 * 
	 * @param src
	 * @param dst
	 * @throws IOException
	 */
	private static void uploadToHdfs(String src, String dst) throws IOException {
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(dst), conf);

		fs.copyFromLocalFile(new Path(src), new Path(dst));

		fs.close();
	}

	/**
	 * read file from hdfs
	 * 
	 * @throws IOException
	 */
	private static void readFromHdfs(String dst) throws IOException {
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(dst), conf);

		FSDataInputStream hdfsInStream = fs.open(new Path(dst));
		
		FileStatus stat = fs.getFileStatus(new Path(dst));
        
        // create the buffer
        byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
        hdfsInStream.readFully(0, buffer);
        
		OutputStream out = new FileOutputStream("d:/qq-hdfs2.txt");

		out.write(buffer);

		out.close();

		hdfsInStream.close();

		fs.close();		
	}

	/**
	 * delete file from hdfs
	 * 
	 * @param dst
	 * @throws IOException
	 */
	private static void deleteFromHdfs(String dst) throws IOException {
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(dst), conf);

		fs.deleteOnExit(new Path(dst));

		fs.close();
	}

	/**
	 * list file from hdfs directory
	 * 
	 * @param dst
	 * @throws IOException
	 */
	private static void getDirectoryFromHdfs(String dst) throws IOException {
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(dst), conf);

		FileStatus fileList[] = fs.listStatus(new Path(dst));

		int size = fileList.length;

		for (int i = 0; i < size; i++) {
			System.out.println("name:" + fileList[i].getPath().getName() + " size:" + fileList[i].getLen());
		}

		fs.close();
	}
}
