package bdtfinal;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseConn {

	private static final String TABLE_NAME = "country_population";
	private static final String CF_DEFAULT = "key";
	private static final String CF_COUNTRY = "country_info";
	private static final String CF_POPULATION = "population_of_years";
	
	private static Configuration config;

	public HBaseConn() throws IOException{
		createConnectionAndTable();
	}
	
	private void createConnectionAndTable() throws IOException
	{
		config = HBaseConfiguration.create();

		try (Connection connection = ConnectionFactory.createConnection(config);
				Admin admin = connection.getAdmin())
		{
			HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
			table.addFamily(new HColumnDescriptor(CF_DEFAULT));
			table.addFamily(new HColumnDescriptor(CF_COUNTRY));
			table.addFamily(new HColumnDescriptor(CF_POPULATION));

			if (admin.tableExists(table.getTableName()))
			{
				admin.disableTable(table.getTableName());
				admin.deleteTable(table.getTableName());
			}
			admin.createTable(table);
		}
	}
	
	public void saveDataToHbase(List<PopulationDTO> data) throws IOException{
		if (data.isEmpty()) {
			System.out.println("Empty Data");
            return;
        }
        for (PopulationDTO e : data) {
            HTable dsTable = new HTable(config, TABLE_NAME);
    		Put row = new Put(Bytes.toBytes(e.getId()));
    		
    		row.add(Bytes.toBytes(CF_DEFAULT), Bytes.toBytes("id"),Bytes.toBytes(e.getId()));
    		row.add(Bytes.toBytes(CF_COUNTRY), Bytes.toBytes("area"),Bytes.toBytes(e.getArea()));
    		row.add(Bytes.toBytes(CF_COUNTRY), Bytes.toBytes("countryName"),Bytes.toBytes(e.getCountryName()));
    		row.add(Bytes.toBytes(CF_COUNTRY), Bytes.toBytes("density"),Bytes.toBytes(e.getDensity()));
    		row.add(Bytes.toBytes(CF_COUNTRY), Bytes.toBytes("growthRate"),Bytes.toBytes(e.getGrowthRate()));
    		row.add(Bytes.toBytes(CF_POPULATION), Bytes.toBytes("population1970"),Bytes.toBytes(e.getPopulation1970()));
    		row.add(Bytes.toBytes(CF_POPULATION), Bytes.toBytes("population2015"),Bytes.toBytes(e.getPopulation2015()));
    		row.add(Bytes.toBytes(CF_POPULATION), Bytes.toBytes("population2020"),Bytes.toBytes(e.getPopulation2020()));
    		row.add(Bytes.toBytes(CF_POPULATION), Bytes.toBytes("population2022"),Bytes.toBytes(e.getPopulation2022()));
    		
    		dsTable.put(row);
        }
	}
	
}
