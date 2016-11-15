package Configurator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public final class ConfiguratorManager implements Iterable<Configurator> {
	private ArrayList<Configurator> configuratorsList = new ArrayList<Configurator>();
	private final String path;

	public ConfiguratorManager() {
		this.path = "conf/";
	}

	public ConfiguratorManager(String path) {
		this.path = path;
	}

	public void loadAll() throws FileNotFoundException, IOException {
		for (String filename : getConfigurationsFilenameList())
			configuratorsList.add(new Configurator(path, filename));
	}

	public ArrayList<Configurator> getconfiguratorsList() {
		return configuratorsList;
	}

	private ArrayList<String> getConfigurationsFilenameList() {
		ArrayList<String> filenameList = new ArrayList<String>();

		File file = new File(path);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					String filename = files[i].getName();

					filenameList.add(filename);
				}
			}
		}
		return filenameList;
	}

	private boolean isConfigurationFileExits(String filename) {
		File file = new File(path + filename);

		return file.isFile();
	}

	@Override
	public Iterator<Configurator> iterator() {
		return configuratorsList.iterator();
	}

	public Configurator add() {
		Configurator configurator = new Configurator(path);
		configuratorsList.add(configurator);
		return configurator;
	}

	public boolean remove(Configurator configurator) {
		return configuratorsList.remove(configurator);
	}

	public boolean delete(Configurator configurator) {
		if (configurator.delete()) {
			return remove(configurator);
		}
		return false;
	}

	public Configurator[] toArray() {
		return configuratorsList.toArray(new Configurator[configuratorsList.size()]);
	}
}