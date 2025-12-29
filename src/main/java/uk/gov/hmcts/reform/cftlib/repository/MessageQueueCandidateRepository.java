package uk.gov.hmcts.reform.cftlib.repository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.cftlib.entity.MessageQueueCandidate;

import java.util.List;

public interface MessageQueueCandidateRepository extends CrudRepository<MessageQueueCandidate, Long> {

    List<MessageQueueCandidate> findByPublishedIsNullOrderByTimeStampAsc();
}
