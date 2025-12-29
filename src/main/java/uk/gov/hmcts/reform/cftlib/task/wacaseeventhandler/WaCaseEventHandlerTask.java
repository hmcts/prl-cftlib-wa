package uk.gov.hmcts.reform.cftlib.task.wacaseeventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.cftlib.client.wacaseeventhandler.JobName;
import uk.gov.hmcts.reform.cftlib.client.wacaseeventhandler.WaCaseEventHandlerClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class WaCaseEventHandlerTask {

    private final WaCaseEventHandlerClient waCaseEventHandlerClient;
    private final AuthTokenGenerator authTokenGenerator;

    @Scheduled(initialDelay = 10000, fixedRateString = "${wa-case-event-handler.find-problem-messages-task.interval:60000}")
    public void runFindProblemMessagesTask() {
        log.debug("Executing WA Case Event Handler Find Problem Messages request");

        String s2sToken = authTokenGenerator.generate();
        waCaseEventHandlerClient.messagesJob(s2sToken, JobName.FIND_PROBLEM_MESSAGES);
    }
}
