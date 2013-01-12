package com.hyy.engine;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HbaseClient {

	private static Configuration conf = null;
	
	static {
		conf = HBaseConfiguration.create();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		HbaseClient hClient = new HbaseClient();
		
		// create table
		String tbName = "test";
		String[] cfs = {"cf1", "cf2"};
		hClient.createTable(tbName, cfs);

	}

	/**
	 * create table
	 * @param tableName
	 * @param cfs
	 * @throws IOException
	 */
	public void createTable(String tableName, String[] cfs) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			System.out.println("The table has existed.");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (String cf : cfs) {
				tableDesc.addFamily(new HColumnDescriptor(cf));
			}
			admin.createTable(tableDesc);
			System.out.println("The table created succeed.");
		}
	}
}
