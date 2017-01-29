package cc.ethon.jprocess.process.linux;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cc.ethon.jprocess.common.SystemException;
import cc.ethon.jprocess.util.StreamReaderUtil;

public class ProcStat {

	// Expected format:
	// pid (comm) state ppid

	private final File file;
	private int pid;
	private String command;
	private int parentPid;

	public ProcStat(File file) {
		this.file = file;
	}

	public void read() throws SystemException {
		try (InputStream in = new BufferedInputStream(new FileInputStream(file), 64)) {
			pid = StreamReaderUtil.readDecimalInt(in);
			command = StreamReaderUtil.readEnclosedString(in, '(', ')');
			StreamReaderUtil.skip(in, 3); // Skip ' S '
			parentPid = StreamReaderUtil.readDecimalInt(in);
		} catch (final IOException e) {
			throw new SystemException("Failed to read stat file '" + file + "'", e);
		}
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getParentPid() {
		return parentPid;
	}

	public void setParentPid(int parentPid) {
		this.parentPid = parentPid;
	}

}
