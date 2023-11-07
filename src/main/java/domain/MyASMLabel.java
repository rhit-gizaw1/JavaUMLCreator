package domain;

import org.objectweb.asm.Label;

public class MyASMLabel extends MyLabel {
    private Label label;

    public MyASMLabel(Label label) {
        this.label = label;
    }

    @Override
    public boolean equals(MyLabel otherLabel) {
        if (!(otherLabel instanceof MyASMLabel)) {
            return false;
        }
        return this.label.equals(((MyASMLabel) otherLabel).getLabel());
    }

    public Label getLabel() {
        return label;
    }
}
