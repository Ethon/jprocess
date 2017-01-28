package cc.ethon.jprocess.factory.linux;

import cc.ethon.jprocess.factory.SystemFactory;
import cc.ethon.jprocess.process.ProcessApi;
import cc.ethon.jprocess.process.linux.LinuxProcessApi;

public class LinuxSystemFactory extends SystemFactory {

	@Override
	public ProcessApi getProcessApi() {
		return LinuxProcessApi.INSTANCE;
	}

}
