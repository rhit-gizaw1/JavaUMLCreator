package domain.checks;

import domain.Message;
import domain.MyClassNode;

import java.util.List;

public class InterfaceOverImplementation implements Check {
    @Override
    public List<Message> run(MyClassNode classNode) {
        return null;
    }
}