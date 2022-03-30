package com.uclan.MealManagement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileManager {

	private static File logFile;

	public static void openLog(String logFilePath) {
		// Opens the log file, or creates one if one doesn't exist already.
		logFile = new File(logFilePath + "/Meal Management Log.txt");
	}

	public static void writeToLog(String logString) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		try {
			FileWriter fr = new FileWriter(logFile, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write("[" + dtf.format(now) + "] " + logString + "\r\n");
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logWarning(String logString) {
		writeToLog("WARNING: " + logString);
	}

	public static void logError(String logString) {
		writeToLog("ERROR: " + logString);
	}

	public static void logDebug(String logString) {
		writeToLog("DEBUG: " + logString);
	}
}
