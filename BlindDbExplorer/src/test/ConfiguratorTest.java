package test;

import java.io.IOException;

import Configurator.Configurator;
import Configurator.ConfiguratorManager;

public class ConfiguratorTest {
	public static void main(String[] args) {
		try {
			ConfiguratorManager cm = new ConfiguratorManager();
			cm.loadAll();

			for (Configurator c : cm) {
				System.out.println(c);
				System.out.println(c.get("url"));
				//System.out.println(c.get("login"));
				//System.out.println(c.get("url"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}