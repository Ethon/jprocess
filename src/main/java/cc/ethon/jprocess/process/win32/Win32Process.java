package cc.ethon.jprocess.process.win32;

import cc.ethon.jprocess.process.Process;

import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32;

public class Win32Process implements Process {

	private final PROCESSENTRY32 entry;

	public Win32Process(PROCESSENTRY32 entry) {
		super();
		this.entry = entry;
	}

	public int getPid() {
		return entry.th32ProcessID.intValue();
	}

}
