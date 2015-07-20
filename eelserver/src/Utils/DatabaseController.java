package els.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import els.commController.Utils;

public class DatabaseController {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	public static final String ANSI_CYAN = "\u001B[36m";

	public DatabaseController() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			// Setup the connection with the DB
			String url = "jdbc:mysql://IMI-elab1.imi.kit.edu:3306/energylab?autoReconnect=true";
			connect =  DriverManager.getConnection(url,"vrpraktikant", "vrp2014");
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
			// throw e;
		}
	}
	
//	public void getListOfSensors(ArrayList<Sensor> sensors){
//		try {
//			// Result set get the result of the SQL query
//			resultSet = statement.executeQuery("select * from energylab.sensor_code_map");
//			writeResultSet(resultSet);
//			
//			while (resultSet.next()) {
//				sensors.add(new Sensor(resultSet.getInt("id")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void getListOfSensors(ArrayList<byte[]> sensors){
		try {
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from energylab.sensor_code_map");
			writeResultSet(resultSet);
			while (resultSet.next()) {
				sensors.add(Utils.intToByteArray(resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getAllSensors() {
		ArrayList<Integer> activeSensors = new ArrayList<Integer>();
		try {
			// Result set - get the result of the SQL query
			resultSet = statement.executeQuery("select * from energylab.sensor_code_map");
			//writeResultSet(resultSet);
			//resultSet.first();
			while (resultSet.next()) {
				activeSensors.add(resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activeSensors;
	}

	public void getLatestValues(){
		String query = "SELECT * FROM energylab.sensor_log WHERE (sensor_id, time) IN (SELECT sensor_id, MAX(time) FROM energylab.sensor_log GROUP BY sensor_id)";
               
			try {
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery(query);
			//writeResultSet(resultSet);
			
			while (resultSet.next()) {
				//sensors.add(new Sensor(resultSet.getInt("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void testValues(){
		while(true){
			for(int i = 1; i<=10;i++){
				int sensor = (int)(Math.random() * 12);
				int data = (int)(Math.random() * 100);
				
				try {
					preparedStatement = connect.prepareStatement("insert into energylab.sensor_log values (?, ?,default)");
					preparedStatement.setInt(1,sensor);
					preparedStatement.setInt(2,data);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}


	}
	
	private void writeResultSet(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String code = resultSet.getString("code");

			System.out.println("ID: " + id + " with code " + code);
		}

	}
	/*
	public void insertSensorData(int sensor, int data){
		String msg = "-------------------------Logging Sensor Data-------------------------\n";
		try {
			preparedStatement = connect.prepareStatement("insert into energylab.sensor_log values (?, ?,default)");
			preparedStatement.setInt(1,sensor);
			preparedStatement.setInt(2,data);
			preparedStatement.executeUpdate();
			msg += "Sensor: " + sensor + ", Value: " + data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(msg);
	}
	*/

	// You need to close the resultSet
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	public void insertSensorData(int sensor, float data) {
		String msg = ANSI_CYAN+"-----------------Logging Sensor Data-----------------\n";
		try {
			preparedStatement = connect.prepareStatement("insert into energylab.sensor_log values (?, ?,default)");
			preparedStatement.setInt(1,sensor);
			preparedStatement.setFloat(2,data);
			preparedStatement.executeUpdate();
			msg += "Sensor: " + sensor + ", Value: " + data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(msg);
		
	}
}
