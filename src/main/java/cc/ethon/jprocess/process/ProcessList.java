package cc.ethon.jprocess.process;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import cc.ethon.jprocess.common.SystemPredicate;
import cc.ethon.jprocess.factory.SystemFactory;

public final class ProcessList {

	public static Process getCurrentProcess() {
		return SystemFactory.getInstance().getProcessApi().getCurrentProcess();
	}

	public static void forEach(Consumer<Process> consumer) throws Exception {
		try (ProcessSnapshot snapshot = SystemFactory.getInstance().getProcessApi().createProcessSnapshot()) {
			snapshot.createSnapshot();
			Optional<Process> current = snapshot.getFirstProcess();
			while (current.isPresent()) {
				consumer.accept(current.get());
				current = snapshot.getNextProcess();
			}
		}
	}

	public static List<Process> asList() throws Exception {
		final List<Process> list = new ArrayList<Process>();
		forEach(process -> list.add(process));
		return list;
	}

	public static Set<Process> asSet() throws Exception {
		final Set<Process> set = new HashSet<Process>();
		forEach(process -> set.add(process));
		return set;
	}

	public static Optional<Process> findProcess(SystemPredicate<Process> predicate) throws Exception {
		try (ProcessSnapshot snapshot = SystemFactory.getInstance().getProcessApi().createProcessSnapshot()) {
			snapshot.createSnapshot();
			Optional<Process> current = snapshot.getFirstProcess();
			while (current.isPresent()) {
				if (predicate.test(current.get())) {
					return Optional.of(current.get());
				}
				current = snapshot.getNextProcess();
			}
		}
		return Optional.empty();
	}

	public static Optional<Process> findProcessByPid(int pid) throws Exception {
		return findProcess(process -> process.getPid() == pid);
	}

	public static Optional<Process> findParentProcessByChildPid(int pid) throws Exception {
		final Optional<Process> child = findProcessByPid(pid);
		if (child.isPresent()) {
			return findProcessByPid(child.get().getParentPid());
		}
		return Optional.empty();
	}

	public static Optional<Process> findProcessByName(String name) throws Exception {
		return findProcessByName(name, SystemFactory.getInstance().getProcessApi().getDefaultNameComparator());
	}

	public static Optional<Process> findProcessByName(String name, Comparator<String> comparator) throws Exception {
		return findProcess(process -> comparator.compare(process.getName(), name) == 0);
	}

}
