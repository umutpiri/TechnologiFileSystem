package com.netpay.fs.conn;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class PostgresConn {
	
	private static final Logger logger = Logger.getLogger(PostgresConn.class.getName());
	
	public static Connection getConnection(){
		try {
			InitialContext initialContext = new InitialContext();
			Context context = (Context) initialContext.lookup("java:comp/env");
			DataSource ds = (DataSource) context.lookup("connpool");
			return ds.getConnection();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error(getConnection)", e);
			return null;
		}
	}
}
