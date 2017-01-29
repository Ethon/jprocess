package cc.ethon.jprocess.process;

import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ProcessListTest {

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
		final Optional<Process> maybeFound = ProcessList.findProcessByPid(currentProcess.getPid());
		Assert.assertTrue("No process found", maybeFound.isPresent());
		Assert.assertEquals("Pid not equal", currentProcess.getPid(), maybeFound.get().getPid());
		Assert.assertEquals("ParentPid not equal", currentProcess.getParentPid(), maybeFound.get().getParentPid());
		Assert.assertEquals("Name not equal", currentProcess.getName(), maybeFound.get().getName());
		Assert.assertEquals("ExePath not equal", currentProcess.getExecutablePath(), maybeFound.get().getExecutablePath());
	}

	@Test
	public void testFindByName() throws Exception {
		final Process currentProcess = ProcessList.getCurrentProcess();
		final Optional<Process> maybeFound = ProcessList.findProcessByName(currentProcess.getName());
		Assert.assertTrue("No process found", maybeFound.isPresent());
		Assert.assertEquals("Name not equal", currentProcess.getName(), maybeFound.get().getName());
	}

}
