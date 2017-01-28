package cc.ethon.jprocess.process.win32;

import java.io.File;
import java.util.Optional;

import cc.ethon.jprocess.common.LazySystemValue;
import cc.ethon.jprocess.common.NativeStringUtil;
import cc.ethon.jprocess.common.SystemException;
import cc.ethon.jprocess.common.win32.WinApi;
import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.process.ProcessList;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Win32Process implements Process {

	private static Win32Process currentProcess = null;

	private final int pid;
	private final LazySystemValue<Integer> parentPid;
	private final LazySystemValue<String> name;
	private final LazySystemValue<File> executablePath;

	private static LazySystemValue<File> queryFullProcessImageName(int pid) {
		return new LazySystemValue<File>(() -> {
			final HANDLE handle = new ProcessHandleBuilder(pid).processQueryLimitedInformation().open();
			try {
				return new File(WinApi.queryFullProcessImageName(handle, 0));
			} finally {
				WinApi.closeHandle(handle);
			}
		});
	}

	public static Win32Process currentProcess() {
		if (currentProcess == null) {
			currentProcess = new Win32Process(Kernel32.INSTANCE.GetCurrentProcessId());
		}
		return currentProcess;
	}

	public Win32Process(int pid) {
		this.pid = pid;
		parentPid = new LazySystemValue<Integer>(() -> {
			try {
				final Optional<Process> process = ProcessList.findProcessByPid(pid);
				return process.get().getParentPid();
			} catch (final SystemException e) {
				throw e;
			} catch (final Exception e) {
				throw new RuntimeException("Unexpected exception", e);
			}
		});
		this.executablePath = queryFullProcessImageName(pid);
		this.name = new LazySystemValue<String>(() -> executablePath.getValue().getName());
	}

	public Win32Process(PROCESSENTRY32 entry) {
		this.pid = entry.th32ProcessID.intValue();
		this.parentPid = new LazySystemValue<Integer>(entry.th32ParentProcessID.intValue());
		this.name = new LazySystemValue<String>(() -> NativeStringUtil.decodeZeroTerminatedChars(entry.szExeFile));
		this.executablePath = queryFullProcessImageName(pid);
	}

	@Override
	public int getPid() {
		return pid;
	}

	@Override
	public int getParentPid() throws SystemException {
		return parentPid.getValue().intValue();
	}

	@Override
	public String getName() throws SystemException {
		return name.getValue();
	}

	@Override
	public File getExecutablePath() throws SystemException {
		return executablePath.getValue();
	}

}
