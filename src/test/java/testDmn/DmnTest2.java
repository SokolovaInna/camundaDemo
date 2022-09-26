package testDmn;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DmnTest2 {
    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                { "Австрия", true },
                { "Бельгия", true },
                { "Болгария", true },
                { "Хорватия", true },
                { "Кипр", true },
                { "Чехия", true },
                { "Дания", true },
                { "Эстония", true },
                { "Финляндия", true },
                { "Франция", true },
                { "Германия", true },
                { "Греция", true },
                { "Венгрия", true },
                { "Ирландия", true },
                { "Италия", true },
                { "Латвия", true },
                { "Литва", true },
                { "Люксембург", true },
                { "Мальта", true },
                { "Нидерланды", true },
                { "Польша", true },
                { "Португалия", true },
                { "Румыния", true },
                { "Словакия", true },
                { "Словения", true },
                { "Испания", true },
                { "Швеция", true },
                { "Страна 404", false },
                { "Блефуску", false },
                { null, false },
        });
    }
    private final String country;
    private final boolean expectedResult;
    @Rule
    public ProcessEngineRule processEngineRule = new
            ProcessEngineRule();
    public DmnTest2(final String country, final boolean
            expectedResult) {
        this.country = country;
        this.expectedResult = expectedResult;
    }
    @Test
    @Deployment(resources = {"dmn_1.dmn"})
    public void
    givenCountry_whenEvaluatingDmnTable_thenReturnCorrectResult() {
        final DecisionService decisionService =
                processEngineRule.getDecisionService();
        final DmnDecisionTableResult actRes =
                decisionService.evaluateDecisionTableByKey("EU_Membership",
                        Variables.createVariables()
                                .putValue("country", country));
        assertEquals(1, actRes.size());
        assertEquals(expectedResult,
                actRes.getSingleResult().getEntry("eu_member"));
    }
}
