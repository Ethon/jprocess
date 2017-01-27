package cc.ethon.jprocess.process.win32;

import java.util.Optional;

import cc.ethon.jprocess.common.win32.Win32Exception;
import cc.ethon.jprocess.common.win32.WinApi;
import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.process.ProcessSnapshot;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Win32ProcessSnapshot implements ProcessSnapshot {

	private static final DWORD TH32CS_SNAPPROCESS = new DWORD(0x00000002);

	private HANDLE handle = Kernel32.INVALID_HANDLE_VALUE;

	public void createSnapshot() throws Win32Exception {
		close();
		handle = Kernel32.INSTANCE.CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, new DWORD(0));
		if (handle == Kernel32.INVALID_HANDLE_VALUE) {
			throw Win32Exception.fromLastError("CreateToolhelp32Snapshot failed to create process snapshot");
		}
	}

	public Optional<Process> getFirstProcess() throws Win32Exception {
		final PROCESSENTRY32 entry = new PROCESSENTRY32();
		entry.dwSize = new DWORD(entry.size());
		if (!Kernel32.INSTANCE.Process32First(handle, entry)) {
			final int lastError = Kernel32.INSTANCE.GetLastError();
			if (lastError == Kernel32.ERROR_NO_MORE_FILES) {
				return Optional.empty();
			} else {
				throw Win32Exception.fromLastError("Process32First failed", lastError);
			}
		}
		return Optional.of((Process) new Win32Process(entry));
	}

	public Optional<Process> getNextProcess() {
		final PROCESSENTRY32 entry = new PROCESSENTRY32();
		entry.dwSize = new DWORD(entry.size());
		if (!Kernel32.INSTANCE.Process32Next(handle, entry)) {
			return Optional.empty();
		}
		return Optional.of((Process) new Win32Process(entry));
	}

	public void close() throws Win32Exception {
		if (handle != Kernel32.INVALID_HANDLE_VALUE) {
			WinApi.closeHandle(handle);
			handle = Kernel32.INVALID_HANDLE_VALUE;
		}
	}

	@Override
	public String toString() {
		return "Win32ProcessSnapshot [handle=" + handle + "]";
	}

}
