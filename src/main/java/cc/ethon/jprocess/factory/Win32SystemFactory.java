package cc.ethon.jprocess.factory;

import cc.ethon.jprocess.process.ProcessSnapshot;
import cc.ethon.jprocess.process.win32.Win32ProcessSnapshot;

public class Win32SystemFactory extends SystemFactory {

	@Override
	public ProcessSnapshot createProcessSnapshot() {
		return new Win32ProcessSnapshot();
	}

}
