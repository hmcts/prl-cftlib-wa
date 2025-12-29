package uk.gov.hmcts.reform.cftlib.repository;

import org.springframework.data.repository.CrudRepository;
import uk.gov.hmcts.reform.cftlib.entity.WaCaseEventMessage;

public interface WaCaseEventMessageRepository extends CrudRepository<WaCaseEventMessage, Long> {
}
