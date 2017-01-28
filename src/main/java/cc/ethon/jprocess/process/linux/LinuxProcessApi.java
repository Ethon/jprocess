package cc.ethon.jprocess.process.linux;

import java.util.Comparator;

import cc.ethon.jprocess.factory.linux.LinuxSystemParams;
import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.process.ProcessApi;
import cc.ethon.jprocess.process.ProcessSnapshot;

public class LinuxProcessApi implements ProcessApi {

	private static final Comparator<String> DEFAULT_NAME_COMPARATOR = (s1, s2) -> s1.compareTo(s2);

	public static final LinuxProcessApi INSTANCE = new LinuxProcessApi();

	private LinuxProcessApi() {
	}

	@Override
	public Process getCurrentProcess() {
		return LinuxProcess.getCurrentProcess();
	}

	@Override
	public ProcessSnapshot createProcessSnapshot() {
		return new LinuxProcessSnapshot(LinuxSystemParams.getProcFsDir());
	}

	@Override
	public Comparator<String> getDefaultNameComparator() {
		return DEFAULT_NAME_COMPARATOR;
	}
}
