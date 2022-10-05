package main.delegate;


import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("Делегат3")
public class DelegateMes implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution)  {
        System.out.println("Делегат3");

       delegateExecution.getProcessEngine().getRuntimeService().createMessageCorrelation("Message_start")
                .processInstanceBusinessKey("AB-123")
                .correlate();
    }
}
