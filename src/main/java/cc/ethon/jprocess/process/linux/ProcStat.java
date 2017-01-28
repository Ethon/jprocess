package cc.ethon.jprocess.process.linux;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import cc.ethon.jprocess.common.SystemException;

public class ProcStat {

	// Expected format:
	// pid (comm) state ppid

	private static final Pattern PAREN_PATTERN = Pattern.compile("\\)");
	private static Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");

	private final File file;
	private int pid;
	private String command;
	private int parentPid;

	public ProcStat(File file) {
		this.file = file;
	}

	public void read() throws SystemException {
		try (final Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));) {
			// pid
			pid = scanner.nextInt();

			// command
			scanner.useDelimiter(PAREN_PATTERN);
			final String parenCommand = scanner.next();
			command = parenCommand.substring(1);
			scanner.useDelimiter(WHITESPACE_PATTERN);

			// Rest
			scanner.next(); // Skip state
			parentPid = scanner.nextInt();
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
