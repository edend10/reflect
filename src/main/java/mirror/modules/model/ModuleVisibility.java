package mirror.modules.model;

public class ModuleVisibility {
    private boolean hidden;

    public ModuleVisibility() {
        this.hidden = false;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
