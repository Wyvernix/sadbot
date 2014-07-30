package me.wyvernix.sadbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream; 

public class Util {
	private static final String slash = System.getProperty("file.separator");
	
	public synchronized static Object load(String fileName){
		ObjectInputStream inp = null;
		final String loadFile= "save"+slash+fileName;
		Object returns = null;
		try {
			final File file = new File(loadFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    returns = inp.readObject();
		    System.out.println(">Loaded "+loadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(">File not found ("+ loadFile +")");
			me.wyvernix.sadbot.newGUI.logError(e);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(">Data corrupt ("+ loadFile +")");
			me.wyvernix.sadbot.newGUI.logError(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			me.wyvernix.sadbot.newGUI.logError(e);
		} finally {
			if(inp != null) {
				try {
					inp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return returns;
	}
	
	public synchronized static boolean save(Object saveData, String fileName) {
		final String saveFile= "save"+slash+fileName;
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(saveFile));
			out.writeObject(saveData);
			System.out.println(">Saved "+saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			me.wyvernix.sadbot.newGUI.logError(e);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			me.wyvernix.sadbot.newGUI.logError(e);
			return false;
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return true;
	}
}
