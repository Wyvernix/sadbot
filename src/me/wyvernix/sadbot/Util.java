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
	public static Object load(String fileName){
		ObjectInputStream inp = null;
		final String loadFile= "save\\"+fileName;
		Object returns = null;
		try {
			final File file = new File(loadFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    returns = inp.readObject();
		    System.out.println("Loaded "+loadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Couldn't find the data ("+ loadFile +")");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't find the data ("+ loadFile +")");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	
	public static boolean save(Object saveData, String fileName) {
		final String saveFile= "save\\"+fileName;
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(saveFile));
			out.writeObject(saveData);
			System.out.println("Saved "+saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
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
