package net.petrvlasak.jopenscan.task;

import net.petrvlasak.jopenscan.domain.JobSettings;

public interface ScanningTaskFactory {

    ScanningTask createTask(JobSettings jobSettings);

}
