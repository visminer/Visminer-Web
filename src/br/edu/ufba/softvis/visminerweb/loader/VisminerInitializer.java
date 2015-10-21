package br.edu.ufba.softvis.visminerweb.loader;

import br.edu.ufba.softvis.visminer.main.VisMiner;
import br.edu.ufba.softvis.visminerweb.util.FileUtils;

public class VisminerInitializer {

	public static VisMiner visminer;

	public static boolean configOK() {
		return (visminer != null);
	}

	public static void configure() {
		if (visminer == null) {
			visminer = new VisMiner();

			if (visminer.setDBConfig(FileUtils.getDBConfigFilePath())) {
				System.out.println("Configuration OK!");
			} else {
				System.out.println("Configuration FAILED!");
			}
		}
	}
	
	public static void reset() {
		visminer = null;
	}

}
