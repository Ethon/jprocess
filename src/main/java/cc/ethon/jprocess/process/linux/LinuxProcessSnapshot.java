package cc.ethon.jprocess.process.linux;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import cc.ethon.jprocess.common.SystemException;
import cc.ethon.jprocess.process.Process;
import cc.ethon.jprocess.process.ProcessSnapshot;

public class LinuxProcessSnapshot implements ProcessSnapshot {

	private static final Pattern PID_PATTERN = Pattern.compile("\\d+$");

	private final File procFsDir;
	private List<File> processDirs;
	private int index;

	public LinuxProcessSnapshot(File procFsDir) {
		this.procFsDir = procFsDir;
	}

	@Override
	public void createSnapshot() throws SystemException {
		final File[] files = procFsDir.listFiles();
		if (files == null) {
			throw new SystemException("ProcFs dir '" + procFsDir + "' is not valid");
		}
		processDirs = Arrays.stream(files).filter(file -> PID_PATTERN.matcher(file.getName()).matches()).collect(Collectors.toList());
		index = 0;
	}

	@Override
	public Optional<Process> getFirstProcess() throws SystemException {
		index = 0;
		return getNextProcess();
	}

	@Override
	public Optional<Process> getNextProcess() throws SystemException {
		if (index < processDirs.size()) {
			final File current = processDirs.get(index++);
			final int pid = Integer.valueOf(current.getName());
			return Optional.of(new LinuxProcess(pid, current));
		}
		return Optional.empty();
	}

	@Override
	public void close() {
	}

}
