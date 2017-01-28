package cc.ethon.jprocess.factory;

import cc.ethon.jprocess.process.ProcessApi;
import cc.ethon.jprocess.process.win32.Win32ProcessApi;

public class Win32SystemFactory extends SystemFactory {

	@Override
	public ProcessApi getProcessApi() {
		return Win32ProcessApi.INSTANCE;
	}

}
