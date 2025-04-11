package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {

	public static String getPropertyString(String fileName) throws IOException {
		
		String connStr = null;
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream(fileName);
		props.load(fis);
		
		String protocol = props.getProperty("protocol");
		String hostname = props.getProperty("hostname");
        String port = props.getProperty("port");
        String dbname = props.getProperty("dbname");
        String user = props.getProperty("user");
        String password = props.getProperty("password");
		
        connStr = protocol+"//"+hostname+":"+port+"/"+dbname+"?user="+user+"&password="+password;
		return connStr;
	}

}
