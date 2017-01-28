package cc.ethon.jprocess.process;

import java.util.Optional;
import java.util.function.Consumer;

import cc.ethon.jprocess.factory.SystemFactory;

public final class ProcessList {

	public static void forEach(Consumer<Process> consumer) throws Exception {
		try (ProcessSnapshot snapshot = SystemFactory.getInstance().createProcessSnapshot()) {
			snapshot.createSnapshot();
			Optional<Process> current = snapshot.getFirstProcess();
			while (current.isPresent()) {
				consumer.accept(current.get());
				current = snapshot.getNextProcess();
			}
		}
	}

}
