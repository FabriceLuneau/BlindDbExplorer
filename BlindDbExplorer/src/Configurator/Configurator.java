package Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Luneau
 *
 *         configuration file * Load paramaters from properties files
 */

public final class Configurator {
	private final static String EXTENSION = ".conf";
	private static final String NAMECONFKEY = "name";
	
	private Properties properties = new Properties();
	private String filename = null;
	private String path; 

		private Configurator() {
	}
		
		public Configurator(String path) {
			this.path = path;
		}

	public Configurator(String path, String filePath) throws FileNotFoundException, IOException {
		this.path = path;
		this.filename = filePath;
		load();
	}

	public void put(String key, String value) {
		properties.put(key, value);
	}

	public boolean isSet(String key) {
		try {
			properties.get(key);
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public String get(String key) throws NullPointerException {
		return properties.get(key).toString();
	}
	
	public void load() throws IOException, FileNotFoundException {
		FileInputStream input = new FileInputStream(path + filename);

		try {
			properties.load(input);
		} finally {
			input.close();
		}
	}

	public boolean delete() {
		File file = new File(path + filename);
		return file.delete();
	}
	
	public String generateFilename() {
		return filename = properties.get(NAMECONFKEY).toString().trim() + Configurator.EXTENSION;
	}

	public boolean save() throws FileNotFoundException, IOException {
		try{
			if(generateFilename().compareTo(Configurator.EXTENSION) == 0) {
				return 	false;
			}
			}catch(NullPointerException e) {
		return false;
	}

	FileOutputStream output = new FileOutputStream(path + filename);properties.store(output,path + filename);output.close();

	return true;
	}
	
	@Override
	public String toString() {
		try {
			return properties.get("name").toString();
		} catch (NullPointerException e) {
			return "undefined name";
		}
	}
	}