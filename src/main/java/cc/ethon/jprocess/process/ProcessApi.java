package cc.ethon.jprocess.process;

import java.util.Comparator;

public interface ProcessApi {

	public Process getCurrentProcess();

	public ProcessSnapshot createProcessSnapshot();

	public Comparator<String> getDefaultNameComparator();

}
