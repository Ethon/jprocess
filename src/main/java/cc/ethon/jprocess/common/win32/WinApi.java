package cc.ethon.jprocess.common.win32;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public final class WinApi {

	public static void closeHandle(HANDLE handle) throws Win32Exception {
		if (!Kernel32.INSTANCE.CloseHandle(handle)) {
			throw Win32Exception.fromLastError("CloseHandle failed with " + handle);
		}
	}

}
