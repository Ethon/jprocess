package cc.ethon.jprocess.factory;

import cc.ethon.jprocess.common.OperatingSystemDetection;
import cc.ethon.jprocess.common.OperatingSystemDetection.OperatingSystem;
import cc.ethon.jprocess.process.ProcessApi;

public abstract class SystemFactory {

	private static SystemFactory instance;

	protected SystemFactory() {
	}

	public static SystemFactory getInstance() {
		if (instance == null) {
			if (OperatingSystemDetection.getOperatingSystem() == OperatingSystem.Win32) {
				return new Win32SystemFactory();
			}
		}
		return instance;
	}

	public abstract ProcessApi getProcessApi();

}
