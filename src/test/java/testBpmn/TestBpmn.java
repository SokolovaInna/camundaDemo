package testBpmn;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.externalTask;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.community.mockito.CamundaMockito.registerJavaDelegateMock;
import static org.camunda.community.mockito.CamundaMockito.verifyJavaDelegateMock;
import static org.mockito.Mockito.times;
public class TestBpmn {
    @Rule
    public ProcessEngineRule processEngineRule = new ProcessEngineRule();
    @Test
    @Deployment(resources = {"diagram_10.bpmn"})
    public void givenError_whenRunningLesson13Bpmn_thenProcessError() throws Exception {
        final RuntimeService runtimeService = processEngineRule.getRuntimeService();
        registerJavaDelegateMock("SomeInternalServiceTask");
        registerJavaDelegateMock("ProcessError");
        final ProcessInstance pi = runtimeService.startProcessInstanceByKey("Lesson13_Process");
        assertThat(pi).isStarted();
        execute(job());
        verifyJavaDelegateMock("SomeInternalServiceTask").executed(times(1));
        assertThat(pi).isWaitingAt("call_external_service_task");
        execute(job());
        complete(externalTask("call_external_service_task"));
        assertThat(pi).isWaitingAt("some_message_received_from_external_system");
        execute(job());
        processEngineRule.getRuntimeService()
                .createMessageCorrelation("some_message")
                .processInstanceId(pi.getProcessInstanceId())
                .setVariable("SUCCESS", false)
                .correlate();
        assertThat(pi).isWaitingAt("gateway_open");
        execute(job());
        assertThat(pi).isWaitingAt("process_error");
        execute(job());
        verifyJavaDelegateMock("ProcessError").executed(times(1));
        assertThat(pi).isWaitingAt("gateway_close");
        execute(job());
        assertThat(pi).isEnded();
    }
    @Test
    @Deployment(resources = {"lesson13.bpmn"})
    public void givenSuccess_whenRunningLesson13Bpmn_thenDontProcessError(){
        final RuntimeService runtimeService = processEngineRule.getRuntimeService();
        registerJavaDelegateMock("SomeInternalServiceTask");
        final ProcessInstance pi = runtimeService.startProcessInstanceByKey("Lesson13_Process");
        assertThat(pi).isStarted();
        assertThat(pi).isWaitingAt("call_internal_service_task");
        execute(job());
        verifyJavaDelegateMock("SomeInternalServiceTask").executed(times(1));
        assertThat(pi).isWaitingAt("call_external_service_task");
        execute(job());
        complete(externalTask("call_external_service_task"));
        assertThat(pi).isWaitingAt("some_message_received_from_external_system");
        execute(job());
        processEngineRule.getRuntimeService()
                .createMessageCorrelation("some_message")
                .processInstanceId(pi.getProcessInstanceId())
                .setVariable("SUCCESS", true)
                .correlate();
        assertThat(pi).isWaitingAt("gateway_open");
        execute(job());
        assertThat(pi).isWaitingAt("gateway_close");
        execute(job());
        assertThat(pi).isEnded();
    }

}
