package com.netpay.fs.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.netpay.fs.models.Directory;
import com.netpay.fs.utils.DBUtils;

public class DataLoader implements ServletContextListener {
	
	private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent sce) {		
		// Before loading data, delete old records
		DBUtils.deleteAllDirs();
		
		loadData(sce.getServletContext(), "/WEB-INF/fileSystem.txt");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
	
	/**
	 * Parse given file and load hierarchical data into directory table
	 * */
	private void loadData(ServletContext sc, String filePath) {
		try (InputStream is = sc.getResourceAsStream(filePath)){
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			
			List<Directory> stack = new ArrayList<>();
			
			String line;
			while((line = reader.readLine()) != null) {
				String dirName = line.trim();
				if(dirName.length() == 0) {
					continue;
				}
				
				// Get depth of directory
				int numTabs = getNumberOfTabs(line);
				
				// Delete "/" and "\" characters from directory names
				dirName = dirName.replaceAll("(\\\\|/)", "");
				
				// Remove directories from stack that are at the same or lower level of tree
				for(int i=stack.size()-1; i>=numTabs; i--) {
					stack.remove(i);
				}

				Directory dir;
				if(stack.isEmpty()) {
					// Root directory
					dir = new Directory(dirName, null);
				} else {
					// NonRoot directory
					dir = new Directory(dirName, stack.get(stack.size()-1));
				}
				// Save directory and fill id field
				boolean success = DBUtils.saveDirectory(dir);
				if(success) {
					stack.add(dir);
				}
			}
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error(loadData)", e);
		}
	}
	
	/**
	 * Get number of preceding tabs
	 * */
	private int getNumberOfTabs(String line) {
		int n = 0;
		while(line.charAt(n) == '\t') {
			n++;
		}
		return n;
	}
}
