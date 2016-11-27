/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
						
						if (value.length() == 0)
							continue;
						
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
