package domain;

import domain.myasm.MyASMClassNode;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

public class MyClassNodeCreator {

    public MyClassNode crateMyClassNodeFromName(String path) {

        ClassReader reader = null;
        ClassNode classNode = null;
        try {
            reader = new ClassReader(path);
            classNode = new ClassNode();
            reader.accept(classNode, ClassReader.EXPAND_FRAMES);
        } catch (IOException e) {
            throw new RuntimeException("This is not a valid class name: " + path);
        }

        return new MyASMClassNode(classNode);
    }


}
