package domain.checks;

import domain.LintType;
import domain.Message;
import domain.MyClassNode;
import domain.MyClassNodeCreator;
import domain.myasm.MyASMClassNodeCreator;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramToInterfaceTest {
    MyClassNodeCreator classNodeCreator = new MyASMClassNodeCreator(
            Path.of(
                    "src/test/resources"
            ).toAbsolutePath()
    );

    @Test
    public void validateProgramToInterface_withClassThatViolates_expectViolations(){
        String className = "domain/checks/ProgramToInterfaceNotImplementationTestClasses/BadInterfaceUse";
        validate(className, new ArrayList<>(Arrays.asList(
                "Where you need to Programming to interface instead of implementation: violatesPattern",
                "Where you need to Programming to interface instead of implementation: violator",
                "Where you need to Programming to interface instead of implementation: violatesDifferently",
                "Where you need to Programming to interface instead of implementation: badInterfaceUse",
                "Where you need to Programming to interface instead of implementation: badAbstractUse"
        )));
    }

    @Test
    public void validateProgramToInterface_withClassThatDoesNotViolate_expectEmptyMessage(){
        String className = "domain/checks/ProgramToInterfaceNotImplementationTestClasses/GoodInterfaceUse";
        MyClassNode classNode = classNodeCreator.createMyClassNodeFromName(className);
        ProgramInterfaceNotImplementation programInterfaceNotImplementation = new ProgramInterfaceNotImplementation(classNodeCreator);
        List<Message> badImplementations = programInterfaceNotImplementation.run(classNode);
        assertEquals(0, badImplementations.size());
    }

    private void validate(String classPath, List<String> expectedMessages){
        MyClassNode classNode = classNodeCreator.createMyClassNodeFromName(classPath);
        ProgramInterfaceNotImplementation programInterfaceNotImplementation = new ProgramInterfaceNotImplementation(classNodeCreator);
        List<Message> badImplementations = programInterfaceNotImplementation.run(classNode);
        for(int i = 0; i < badImplementations.size(); i ++){
            Message badImplementation = badImplementations.get(i);
            assertEquals(badImplementation.getCheckType(), LintType.INTERFACE_OVER_IMPLEMENTATION);
            assertEquals(badImplementation.getClassesOfInterest(), classNode.name);
            assertEquals(badImplementation.getMessage(), expectedMessages.get(i));
        }

    }


}
