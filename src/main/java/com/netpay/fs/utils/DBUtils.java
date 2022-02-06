package com.netpay.fs.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.netpay.fs.conn.PostgresConn;
import com.netpay.fs.models.Directory;

public class DBUtils {
	
	private static final Logger logger = Logger.getLogger(DBUtils.class.getName());
	
	/**
	 * Get paths of directories that include given text (case insensitive)
	 * */
	public static List<String> searchDirectory(String text){
		String sql = "select * from path_mapping where dir_name ilike '%'||?||'%'";
		
		ArrayList<String> list = new ArrayList<>();
		
		try(Connection conn = PostgresConn.getConnection()){
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, text);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				String path = rs.getString("dir_path");
				list.add(path);
			}
			
			return list;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error(searchDirectory)", e);
			return list;
		}
	}
	
	/**
	 * Save given directory to database
	 * Assigns auto generated id to given directory object
	 * */
	public static boolean saveDirectory(Directory directory) {
		String sql = "insert into directory(name, parent_id) values (?, ?)";
		
		try(Connection conn = PostgresConn.getConnection()){
			PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, directory.getName());
			
			if(directory.getParent() != null) {
				statement.setInt(2, directory.getParent().getId());
			} else {
				statement.setNull(2, Types.INTEGER);
			}
			
			int result = statement.executeUpdate();
			
			if(result == 0) {
				return false;
			}
			
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()) {
				directory.setId(rs.getInt(1));
			}
			return true;
			
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error(createDirectory)", e);
			return false;
		}
	}
	
	/**
	 * Delete every directory record in database
	 * */
	public static void deleteAllDirs(){
		try(Connection conn = PostgresConn.getConnection()){
			Statement statement = conn.createStatement();
			statement.execute("delete from directory");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error(deleteAllDirs)", e);
		}
	}
}
