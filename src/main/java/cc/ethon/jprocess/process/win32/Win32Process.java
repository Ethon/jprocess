package cc.ethon.jprocess.process.win32;

import java.io.File;

import cc.ethon.jprocess.common.LazySystemValue;
import cc.ethon.jprocess.common.NativeStringUtil;
import cc.ethon.jprocess.common.SystemException;
import cc.ethon.jprocess.common.win32.WinApi;
import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.util.LazyValue;

import com.sun.jna.platform.win32.Tlhelp32.PROCESSENTRY32;
import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Win32Process implements Process {

	private final int pid;
	private final int parentPid;
	private final LazyValue<String> name;
	private final LazySystemValue<File> executablePath;

	public Win32Process(PROCESSENTRY32 entry) {
		this.pid = entry.th32ProcessID.intValue();
		this.parentPid = entry.th32ParentProcessID.intValue();
		this.name = new LazyValue<String>(() -> NativeStringUtil.decodeZeroTerminatedChars(entry.szExeFile));
		this.executablePath = new LazySystemValue<File>(() -> {
			final HANDLE handle = new ProcessHandleBuilder(pid).processQueryLimitedInformation().open();
			try {
				return new File(WinApi.queryFullProcessImageName(handle, 0));
			} finally {
				WinApi.closeHandle(handle);
			}
		});
	}

	@Override
	public int getPid() {
		return pid;
	}

	@Override
	public int getParentPid() {
		return parentPid;
	}

	@Override
	public String getName() {
		return name.getValue();
	}

	@Override
	public File getExecutablePath() throws SystemException {
		return executablePath.getValue();
	}

}
