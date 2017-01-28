package cc.ethon.jprocess.factory.linux;

import java.io.File;

public class LinuxSystemParams {

	private static File procFsDir = new File("/proc");

	public static File getProcFsDir() {
		return procFsDir;
	}

	public static void setProcFsDir(File procFsDir) {
		LinuxSystemParams.procFsDir = procFsDir;
	}

}
