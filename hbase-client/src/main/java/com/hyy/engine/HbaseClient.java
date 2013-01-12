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
	 * @thros IOException 
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
	
	/**
	 * delete table
	 * @param tableName
	 * @throws IOException
	 */
	public void deleteTable(String tableName) throws IOException {		
		HBaseAdmin admin = new HBaseAdmin(conf);
		admin.disableTable(tableName);
		admin.deleteTable(tableName);
		System.out.println("表删除成功.");
	}
	
	/**
	 * insert
	 * @param tableName
	 * @param cfs
	 */
	public void writeRow(String tableName, String[] cfs) {
		HTable table = new HTable(conf, tableName);
		Put put = new Put(Bytes.toBytes("rows1"));
		
		for (String cf : cfs) {
			put.add(Bytes.toBytes(cf), Bytes.toBytes(String.valueOf(1)), Bytes.toBytes("value_1"));
			table.put(put);
		}
	}
	
	/**
	 * delete
	 * @param tableName
	 * @param rowKey
	 */
	public void deleteRow(String tableName, String rowKey) {
		HTable table = new HTable(conf, tablename);
	    List list = new ArrayList();
	    Delete d1 = new Delete(rowkey.getBytes());
	    list.add(d1);
	    table.delete(list);
	    System.out.println("删除行成功！");
	}
	
	/**
	 * select 
	 * @param tableName
	 * @param rowKey
	 */
	public void selectRow(String tableName, String rowKey) {
		HTable table = new HTable(conf, tablename);
	    Get g = new Get(rowKey.getBytes());
	    Result rs = table.get(g);
	    for (KeyValue kv : rs.raw()) {
	        System.out.print(new String(kv.getRow()) + "  ");
	        System.out.print(new String(kv.getFamily()) + ":");
	        System.out.print(new String(kv.getQualifier()) + "  ");
	        System.out.print(kv.getTimestamp() + "  ");
	        System.out.println(new String(kv.getValue()));
	    }
	}
	
	/**
	 * list all data
	 * @param tableName
	 */
	public void scanner(String tableName) {
		HTable table = new HTable(conf, tablename);
        Scan s = new Scan();
        ResultScanner rs = table.getScanner(s);
        for (Result r : rs) {
            KeyValue[] kv = r.raw();
            for (int i = 0; i < kv.length; i++) {
                System.out.print(new String(kv[i].getRow()) + "  ");
                System.out.print(new String(kv[i].getFamily()) + ":");
                System.out.print(new String(kv[i].getQualifier()) + "  ");
                System.out.print(kv[i].getTimestamp() + "  ");
                System.out.println(new String(kv[i].getValue()));
            }
        }
	}
}
