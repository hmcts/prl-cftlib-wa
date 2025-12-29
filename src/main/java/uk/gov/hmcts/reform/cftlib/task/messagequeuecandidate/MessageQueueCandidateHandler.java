package uk.gov.hmcts.reform.cftlib.task.messagequeuecandidate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.cftlib.entity.MessageQueueCandidate;
import uk.gov.hmcts.reform.cftlib.entity.WaCaseEventMessage;
import uk.gov.hmcts.reform.cftlib.entity.WaCaseEventMessageState;
import uk.gov.hmcts.reform.cftlib.repository.MessageQueueCandidateRepository;
import uk.gov.hmcts.reform.cftlib.repository.WaCaseEventMessageRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class MessageQueueCandidateHandler {
    private final MessageQueueCandidateRepository messageQueueCandidateRepository;
    private final WaCaseEventMessageRepository waCaseEventMessageRepository;
    private final ObjectMapper objectMapper;

    public void handle(MessageQueueCandidate candidate) {
        log.info("Processing message queue candidate with ID: {}", candidate.getId());

        try {
            WaCaseEventMessage waCaseEventMessage = getWaCaseEventMessage(candidate);
            waCaseEventMessageRepository.save(waCaseEventMessage);

            candidate.setPublished(LocalDateTime.now());
            messageQueueCandidateRepository.save(candidate);

            log.info("Processed and published message queue candidate with ID: {}", candidate.getId());
        } catch (JsonProcessingException e) {
            log.error("Error while processing the message queue candidate with ID: {}", candidate.getId(), e);
        }
    }

    private WaCaseEventMessage getWaCaseEventMessage(MessageQueueCandidate messageQueueCandidate) throws JsonProcessingException {
        String messageContent = objectMapper.writeValueAsString(messageQueueCandidate.getMessageInformation());

        return WaCaseEventMessage.builder()
                .messageId(String.valueOf(messageQueueCandidate.getId()))
                .caseId(messageQueueCandidate.getMessageInformation().getCaseId())
                .eventTimestamp(messageQueueCandidate.getTimeStamp())
                .fromDlq(false)
                .state(WaCaseEventMessageState.READY)
                .messageContent(messageContent)
                .received(LocalDateTime.now())
                .deliveryCount(0)
                .retryCount(0)
                .build();
    }
}
