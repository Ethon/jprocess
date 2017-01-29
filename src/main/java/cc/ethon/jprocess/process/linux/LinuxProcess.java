package cc.ethon.jprocess.process.linux;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cc.ethon.jprocess.common.LazySystemValue;
import cc.ethon.jprocess.common.SystemException;
import cc.ethon.jprocess.common.linux.LinuxLibC;
import cc.ethon.jprocess.factory.linux.LinuxSystemParams;
import cc.ethon.jprocess.process.Process;

public class LinuxProcess implements Process {

	private static LinuxProcess currentProcess;

	private int pid;
	private final LazySystemValue<ProcStat> stat;
	private final LazySystemValue<File> executablePath;

	public static LinuxProcess getCurrentProcess() {
		if (currentProcess == null) {
			currentProcess = new LinuxProcess(LinuxLibC.INSTANCE.getpid(), new File(LinuxSystemParams.getProcFsDir(), "self"));
		}
		return currentProcess;
	}

	public LinuxProcess(int pid, File processProcFsDir) {
		this.pid = pid;
		stat = new LazySystemValue<ProcStat>(() -> {
			final ProcStat procStat = new ProcStat(new File(processProcFsDir, "stat"));
			procStat.read();
			return procStat;
		});
		executablePath = new LazySystemValue<File>(() -> {
			final Path path = Paths.get(processProcFsDir.getPath(), "exe");
			try {
				return Files.readSymbolicLink(path).toFile();
			} catch (final Exception e) {
				throw new SystemException("Failed to get executable path for " + path, e);
			}
		});
	}

	@Override
	public int getPid() {
		return pid;
	}

	@Override
	public int getParentPid() throws SystemException {
		return stat.getValue().getParentPid();
	}

	@Override
	public String getName() throws SystemException {
		return stat.getValue().getCommand();
	}

	@Override
	public File getExecutablePath() throws SystemException {
		return executablePath.getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LinuxProcess other = (LinuxProcess) obj;
		if (pid != other.pid) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LinuxProcess [pid=" + pid + "]";
	}

}
