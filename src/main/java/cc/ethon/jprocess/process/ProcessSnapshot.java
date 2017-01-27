package cc.ethon.jprocess.process;

import java.util.Optional;

import cc.ethon.jprocess.common.SystemException;

public interface ProcessSnapshot extends AutoCloseable {

	public void createSnapshot() throws SystemException;

	public Optional<Process> getFirstProcess() throws SystemException;

	public Optional<Process> getNextProcess() throws SystemException;

}
