package uk.gov.hmcts.reform.cftlib.task.taskmonitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.cftlib.client.taskmonitor.TaskMonitorClient;
import uk.gov.hmcts.reform.cftlib.client.taskmonitor.TaskMonitorJobRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskMonitorTask {

    private final TaskMonitorClient taskMonitorClient;
    private final AuthTokenGenerator authTokenGenerator;

    @Scheduled(initialDelay = 10000, fixedRateString = "${task-monitor.initiation-task.interval:5000}")
    public void runInitiationTask() {
        log.debug("Executing Task Monitor Initiation request");

        String s2sToken = authTokenGenerator.generate();
        taskMonitorClient.taskMonitorJob(s2sToken, TaskMonitorJobRequest.initiation());
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${task-monitor.reconfiguration-task.interval:5000}")
    public void runReconfigurationTask() {
        log.debug("Executing Task Monitor Reconfiguration request");

        String s2sToken = authTokenGenerator.generate();
        taskMonitorClient.taskMonitorJob(s2sToken, TaskMonitorJobRequest.reconfiguration());
    }

    @Scheduled(initialDelay = 10000, fixedRateString = "${task-monitor.termination-task.interval:5000}")
    public void runTerminationTask() {
        log.debug("Executing Task Monitor Termination request");

        String s2sToken = authTokenGenerator.generate();
        taskMonitorClient.taskMonitorJob(s2sToken, TaskMonitorJobRequest.termination());
    }
}
