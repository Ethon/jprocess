package cc.ethon.jprocess.factory;

import java.util.Comparator;

import cc.ethon.jprocess.process.ProcessSnapshot;
import cc.ethon.jprocess.process.win32.Win32ProcessSnapshot;

public class Win32SystemFactory extends SystemFactory {

	@Override
	public ProcessSnapshot createProcessSnapshot() {
		return new Win32ProcessSnapshot();
	}

	@Override
	public Comparator<String> getDefaultProcessNameComparator() {
		return String.CASE_INSENSITIVE_ORDER;
	}

}
