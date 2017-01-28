package cc.ethon.jprocess.process;

import java.io.File;

import cc.ethon.jprocess.common.SystemException;

public interface Process {

	public int getPid();

	public int getParentPid();

	public String getName();

	public File getExecutablePath() throws SystemException;

}
