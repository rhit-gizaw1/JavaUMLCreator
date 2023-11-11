package domain.checks;

import domain.LintType;
import domain.Message;
import domain.MyClassNode;
import domain.MyClassNodeCreator;
import domain.myasm.MyASMClassNodeCreator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Strategy Pattern looks for the following:
 * - The provided class contains another user defined class as a field
 * Test: User defined class as field, default Java class as field
 * - The user defined class stored in the field is an abstract type
 * Test: Field of an abstract type (interface and Abstract class), field of concrete type
 * - There is a setter in the class provided to set the strategy Field to a strategy
 * Test: setter provided, no setter, field initialized in the constructor
 */

public class StrategyPatternTest {
    private final MyClassNodeCreator creator = new MyASMClassNodeCreator(
            Path.of("src/test/resources").toAbsolutePath()
    );

    private void testValidStrategyPattern(String className, String strategyClassName, String fieldName, String setterName) {
        MyClassNode classNode = creator.createMyClassNodeFromName(className);
        Check strategyPattern = new StrategyPattern(creator);
        List<Message> messageList = strategyPattern.run(classNode);

        String expectedMessage = String.format("STRATEGY PATTERN: %s stores an instance of L%s; in the field %s. The setter is %s.\n", className, strategyClassName, fieldName, setterName);

        assertEquals(className, messageList.get(0).getClassesOfInterest());
        assertEquals(LintType.STRATEGY_PATTERN, messageList.get(0).getCheckType());
        assertEquals(expectedMessage, messageList.get(0).getMessage());
    }

    @Test
    public void runStrategyPatternValidAbstractClassWSetter() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/ValidStrategyPatternAbstractClassWSetter";
        String strategyClassName = "domain/checks/StrategyPatternMockTestClasses/StrategyAbstractClass";
        String fieldName = "strategy";
        String setterName = "setStrategy";
        testValidStrategyPattern(className, strategyClassName, fieldName, setterName);
    }

    @Test
    public void runStrategyPatternValidInterfaceWSetter() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/ValidStrategyPatternInterfaceWSetter";
        String strategyClassName = "domain/checks/StrategyPatternMockTestClasses/StrategyInterface";
        String fieldName = "strategyField";
        String setterName = "setStrategyField";
        testValidStrategyPattern(className, strategyClassName, fieldName, setterName);
    }

    @Test
    public void runStrategyPatternValidInterfaceWConstructor() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/ValidStrategyPatternAbstractClassWConstructor";
        String strategyClassName = "domain/checks/StrategyPatternMockTestClasses/StrategyAbstractClass";
        String fieldName = "strategy";
        String setterName = "<init>";
        testValidStrategyPattern(className, strategyClassName, fieldName, setterName);
    }

    @Test
    public void runStrategyPatternInValidWConstructor() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/InvalidStrategyPatternWConstructor";
        MyClassNode classNode = creator.createMyClassNodeFromName(className);
        StrategyPattern strategyPattern = new StrategyPattern(creator);
        List<Message> messageList = strategyPattern.run(classNode);
        assertEquals(0, messageList.size());
    }

    @Test
    public void runStrategyPatternInValidWSetter() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/InvalidStrategyPatternWSetter";
        MyClassNode classNode = creator.createMyClassNodeFromName(className);
        StrategyPattern strategyPattern = new StrategyPattern(creator);
        List<Message> messageList = strategyPattern.run(classNode);
        assertEquals(0, messageList.size());
    }

    @Test
    public void runStrategyPatternInValidWNoSetterNoConstructor() throws IOException {
        String className = "domain/checks/StrategyPatternMockTestClasses/InvalidStrategyPatternWNoSetterNoConstructor";
        MyClassNode classNode = creator.createMyClassNodeFromName(className);
        StrategyPattern strategyPattern = new StrategyPattern(creator);
        List<Message> messageList = strategyPattern.run(classNode);
        assertEquals(0, messageList.size());
    }

}
