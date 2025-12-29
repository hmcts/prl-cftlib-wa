package uk.gov.hmcts.reform.cftlib.client.taskmonitor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "task-monitor-client", url = "${task-monitor.url}")
public interface TaskMonitorClient {

    @PostMapping(value = "/monitor/tasks/jobs")
    void taskMonitorJob(@RequestHeader("ServiceAuthorization") String serviceAuthorization,
                        @RequestBody TaskMonitorJobRequest request);
}
