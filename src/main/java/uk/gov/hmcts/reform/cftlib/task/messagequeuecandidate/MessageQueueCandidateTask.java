package uk.gov.hmcts.reform.cftlib.task.messagequeuecandidate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.cftlib.repository.MessageQueueCandidateRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageQueueCandidateTask {

    private final MessageQueueCandidateRepository messageQueueCandidateRepository;
    private final MessageQueueCandidateHandler messageQueueCandidateHandler;

    @Scheduled(initialDelay = 10000, fixedRateString = "${message-queue-candidate-task.interval:5000}")
    public void runTask() {
        log.debug("Checking for new message queue candidates");

        messageQueueCandidateRepository.findByPublishedIsNullOrderByTimeStampAsc()
            .forEach(messageQueueCandidateHandler::handle);
    }
}
