package cc.ethon.jprocess.process.win32;

import cc.ethon.jprocess.common.win32.Win32Exception;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class ProcessHandleBuilder {

	private final int processId;
	private int access;

	public ProcessHandleBuilder(int processId) {
		this.processId = processId;
		this.access = 0;
	}

	public ProcessHandleBuilder processQueryLimitedInformation() {
		access |= Kernel32.PROCESS_QUERY_LIMITED_INFORMATION;
		return this;
	}

	public HANDLE open() throws Win32Exception {
		if (processId == Kernel32.INSTANCE.GetCurrentProcessId()) {
			return Kernel32.INSTANCE.GetCurrentProcess();
		}
		final HANDLE handle = Kernel32.INSTANCE.OpenProcess(access, false, processId);
		if (handle == null) {
			throw Win32Exception.fromLastError("OpenProcess failed for " + processId + " with flags " + Integer.toBinaryString(access));
		}
		return handle;
	}

}
