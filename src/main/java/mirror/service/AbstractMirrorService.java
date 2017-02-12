package mirror.service;

public abstract class AbstractMirrorService {
    private boolean visible;

    public AbstractMirrorService() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public void show() {
        visible = true;
    }
}
