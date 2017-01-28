package cc.ethon.jprocess.common.linux;

import com.sun.jna.Native;
import com.sun.jna.platform.unix.LibCAPI;

public interface LinuxLibC extends LibCAPI {

	public static final LinuxLibC INSTANCE = Native.loadLibrary("c", LinuxLibC.class);

	public int getpid();

}
