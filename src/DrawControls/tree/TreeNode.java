package DrawControls.tree;

import DrawControls.icons.Icon;

public abstract class TreeNode {
    public static final byte GROUP = 0;
    public static final byte CONTACT = 1;

    public boolean isGroup() {
        return getType() == GROUP;
    }

    public boolean isContact() {
        return getType() == CONTACT;
    }

    protected abstract byte getType();
    public abstract String getText();
    public abstract int getNodeWeight();

    public Icon getLeftIcon() {
       return null;
    }
}