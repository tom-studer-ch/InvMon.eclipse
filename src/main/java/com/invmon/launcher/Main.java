package com.invmon.launcher;

import java.io.File;
import java.util.ArrayList;


public class Main {

	public static final boolean WIN = System.getProperty("os.name").toUpperCase().contains("WINDOWS");

	public static final boolean MAC = ! WIN;

	
	@SuppressWarnings("restriction")
	public static void main(String[] args) {
		
    	File currentDirectory = new File(".").getAbsoluteFile();
		System.out.println("Running InvMon launcher with args:");
		for (String s : args) {
			System.out.println("  " + s);
		}
    	System.out.println("InvMon running in directory: " + currentDirectory.getAbsolutePath());

    	
    	// Set-up InvMon home and data directory
    	
    	String invMonHomeFromArgs = args != null && args.length > 0 ? args[args.length - 1] : null;
    	
    	String invMonHome = System.getProperty("com.invmon.home");
    	if (isEmpty(invMonHome)) {
    		invMonHome = invMonHomeFromArgs;
    	}
    	
    	if (isEmpty(invMonHome)) {
    		invMonHome = concatPaths(System.getProperty("user.home"), "InvMon-data");
    	}		
		System.setProperty("com.invmon.home", invMonHome);
    	System.out.println("InvMon home is: " + invMonHome);
    	
		String environment = System.getProperty("com.invmon.environment");
    	if (isEmpty(environment)) {
    		if (MAC) {
    			// On Mac: appname.app/Contents/MacOS/.
    			environment = currentDirectory.getParentFile().getParentFile().getParentFile().getName();
    			if (environment.endsWith(".app")) {
    				environment = environment.substring(0, environment.length() - 4);
    			}
    		} else {
    			environment = currentDirectory.getParentFile().getName();
    		}
			System.setProperty("com.invmon.environment", environment);
    	}
		System.out.println("InvMon environment is: " + environment);

		String invMonDataDir = concatPaths(invMonHome, environment);
		System.out.println("InvMon data directory is: " + invMonDataDir);

		// Set default system properties
		
		System.setProperty("-Dlog4j.configurationFile", "log4j2.xml");
		System.setProperty("-Dorg.apache.logging.log4j.simplelog.StatusLogger.level", "WARN");
		
		// Assemble args array
		
		String splashBaseName = MAC ? "../../img/splash/splash" : "img/splash/splash";
		String splashScreen = splashBaseName + ".png";
		String initialSplashScreen = splashBaseName + "-initial.png";
		if (new File(initialSplashScreen).exists()) splashScreen = initialSplashScreen;
		
		ArrayList<String> launcherArgs = new ArrayList<>();
		launcherArgs.add("-name");
		launcherArgs.add("InvMon");
		launcherArgs.add("-os");
		launcherArgs.add(MAC ? "macosx" : "win32");
		launcherArgs.add("-ws");
		launcherArgs.add(MAC ? "cocoa" : "win32");
		launcherArgs.add("-arch");
		launcherArgs.add(getArchitecture());
		launcherArgs.add("-data");
		launcherArgs.add(concatPaths(invMonDataDir, "view-layout"));
		launcherArgs.add("-showsplash");
		launcherArgs.add(splashScreen);
		launcherArgs.add("-consoleLog");

		// launcherArgs.add("-debug"); // Generates equinox launcher debug output
		
		
		// Launch the equinox launcher
		System.out.println("Launching equinox launcher");
		org.eclipse.equinox.launcher.Main.main(launcherArgs.toArray(new String[launcherArgs.size()]));
	}
	
	private static String getArchitecture() {
		String arch = System.getProperty("os.arch");
		if ("amd64".equalsIgnoreCase(arch)) {
			return "x86_64";
		}
		return arch;
	}
	
	private static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	public static String concatPaths(String first, String second) {
		StringBuilder b = new StringBuilder(first);
		if (!first.endsWith(File.separator)) b.append(File.separator);
		b.append(second);
		return b.toString();
	}

}
