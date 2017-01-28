package cc.ethon.jprocess.process;

import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import cc.ethon.jprocess.common.SystemException;

public class ProcessListTest {

	private void assertFindBy(Process currentProcess, Optional<Process> maybeFound) throws SystemException {
		Assert.assertTrue("No process found", maybeFound.isPresent());
		Assert.assertEquals("Pid not equal", currentProcess.getPid(), maybeFound.get().getPid());
		Assert.assertEquals("ParentPid not equal", currentProcess.getParentPid(), maybeFound.get().getParentPid());
		Assert.assertEquals("Name not equal", currentProcess.getName(), maybeFound.get().getName());
		Assert.assertEquals("ExePath not equal", currentProcess.getExecutablePath(), maybeFound.get().getExecutablePath());
	}

	@Test
	public void testNotEmpty() throws Exception {
		Assert.assertFalse("ProcessList is empty", ProcessList.asList().isEmpty());
	}

	@Test
	public void testContainsCurrentProcess() throws Exception {
		final Set<Process> set = ProcessList.asSet();
		Assert.assertTrue("ProcessList does not contain current process", set.contains(ProcessList.getCurrentProcess()));
	}

	@Test
	public void testFindByPid() throws Exception {
		final Process currentProcess = ProcessList.getCurrentProcess();
		assertFindBy(currentProcess, ProcessList.findProcessByPid(currentProcess.getPid()));
	}

	@Test
	public void testFindByName() throws Exception {
		final Process currentProcess = ProcessList.getCurrentProcess();
		assertFindBy(currentProcess, ProcessList.findProcessByName(currentProcess.getName()));
	}

}
