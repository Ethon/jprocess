package cc.ethon.jprocess.common.win32;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public final class WinApi {

	public static void closeHandle(HANDLE handle) throws Win32Exception {
		if (!Kernel32.INSTANCE.CloseHandle(handle)) {
			throw Win32Exception.fromLastError("CloseHandle failed with " + handle);
		}
	}

	public static String queryFullProcessImageName(HANDLE processHandle, int flags) throws Win32Exception {
		final IntByReference size = new IntByReference(Kernel32.MAX_PATH);
		final char[] name = new char[Kernel32.MAX_PATH];
		if (!Kernel32.INSTANCE.QueryFullProcessImageName(processHandle, flags, name, size)) {
			throw Win32Exception.fromLastError("QueryFullProcessImageName failed for " + processHandle);
		}
		return String.copyValueOf(name, 0, size.getValue()).intern();
	}

}
