package cc.ethon.jprocess.process;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;

import cc.ethon.jprocess.common.SystemPredicate;
import cc.ethon.jprocess.factory.SystemFactory;

public final class ProcessList {

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

	public static Optional<Process> findProcess(SystemPredicate<Process> predicate) throws Exception {
		try (ProcessSnapshot snapshot = SystemFactory.getInstance().getProcessApi().createProcessSnapshot()) {
			snapshot.createSnapshot();
			final Optional<Process> current = snapshot.getFirstProcess();
			while (current.isPresent()) {
				if (predicate.test(current.get())) {
					return Optional.of(current.get());
				}
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
