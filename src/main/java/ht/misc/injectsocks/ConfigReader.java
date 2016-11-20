package ht.misc.injectsocks;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class ConfigReader {
	private static ArrayList<String> prefixList = new ArrayList<String>();
	private static String socksHost = "0.0.0.0";
	private static int socksPort = 0;
	static {
		String fileName = System.getProperty("injectsocks.configfile");
		if (fileName != null && fileName.length() > 0) {
			System.out.println("INFO: injectsocks.configfile="+fileName);
			BufferedReader reader = null;
			try {
				reader= new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
				for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					line = line.trim();
					if (line.startsWith("#"))
						continue;
					
					String arr[] = line.split("=");
					if (arr.length >= 2) {
						String key = arr[0].trim();
						String value = arr[1].trim();
						
						if (key.equals("prefixList") && value.length() > 0) {
							for (String prefix : value.split(";")) {
								prefix = prefix.trim();
								if (prefix.length() > 0) {
									// change a/b/c to a.b.c
									prefix = prefix.replace('.', '/');
									prefixList.add(prefix);
								}
							}
						} else if (key.equals("socksHost")) {
							socksHost = value;
							System.out.println("INFO: injectsocks socksHost="+socksHost);
						} else if (key.equals("socksPort")) {
							socksPort = Integer.valueOf(value).intValue();
							System.out.println("INFO: injectsocks socksPort="+socksPort);
						}
					}
					prefixList.add(line.trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.err.println("WARNING: Can not find injectsocks config file");
		}
	}
	public static boolean isNeedInject(String className) {
		for (String prefix : prefixList) {
			if (className.startsWith(prefix))
				return true;
		}
		return false;
	}
	public static String getSocksHost() {
		return socksHost;
	}
	public static int getSocksPort() {
		return socksPort;
	}
}
