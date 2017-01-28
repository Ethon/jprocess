package cc.ethon.jprocess.process.win32;

import java.util.Comparator;

import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.process.ProcessApi;
import cc.ethon.jprocess.process.ProcessSnapshot;

public class Win32ProcessApi implements ProcessApi {

	public static final Win32ProcessApi INSTANCE = new Win32ProcessApi();

	private Win32ProcessApi() {
	}

	@Override
	public Process getCurrentProcess() {
		return Win32Process.currentProcess();
	}

	@Override
	public ProcessSnapshot createProcessSnapshot() {
		return new Win32ProcessSnapshot();
	}

	@Override
	public Comparator<String> getDefaultNameComparator() {
		return String.CASE_INSENSITIVE_ORDER;
	}

}
