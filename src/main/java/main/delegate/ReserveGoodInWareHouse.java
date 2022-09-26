package main.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
@Component("ReserveGoodInWareHouse")
public class ReserveGoodInWareHouse implements JavaDelegate {
    @Override
    public void execute(final DelegateExecution delEx) throws  Exception {
        throw new BpmnError("Товар закончился");
    }
}