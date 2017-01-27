package cc.ethon.jprocess.common;

import java.util.Locale;

public class OperatingSystemDetection {

	public enum OperatingSystem {
		Win32
	}

	private static OperatingSystem operatingSystem;

	public static OperatingSystem getOperatingSystem() {
		if (operatingSystem == null) {
			final String name = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if (name.indexOf("win") >= 0 && name.indexOf("darwin") == -1) {
				operatingSystem = OperatingSystem.Win32;
			} else {
				throw new UnsupportedOperationException("Operating system '" + name + "' is not supported");
			}
		}
		return operatingSystem;
	}

}
