package cc.ethon.jprocess.factory;

import cc.ethon.jprocess.common.OperatingSystemDetection;
import cc.ethon.jprocess.common.OperatingSystemDetection.OperatingSystem;
import cc.ethon.jprocess.factory.linux.LinuxSystemFactory;
import cc.ethon.jprocess.factory.win32.Win32SystemFactory;
import cc.ethon.jprocess.process.ProcessApi;

public abstract class SystemFactory {

	private static SystemFactory instance;

	protected SystemFactory() {
	}

	public static SystemFactory getInstance() {
		if (instance == null) {
			if (OperatingSystemDetection.getOperatingSystem() == OperatingSystem.Win32) {
				instance = new Win32SystemFactory();
			} else if (OperatingSystemDetection.getOperatingSystem() == OperatingSystem.Linux) {
				instance = new LinuxSystemFactory();
			}
		}
		return instance;
	}

	public abstract ProcessApi getProcessApi();

}
