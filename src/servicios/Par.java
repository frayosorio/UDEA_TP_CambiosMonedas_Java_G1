package servicios;

public class Par<ClaseX, ClaseY> {

    private ClaseX x;
    private ClaseY y;
    
    public Par(ClaseX x, ClaseY y) {
        this.x = x;
        this.y = y;
    }

    public ClaseX getX() {
        return x;
    }

    public void setX(ClaseX x) {
        this.x = x;
    }

    public ClaseY getY() {
        return y;
    }

    public void setY(ClaseY y) {
        this.y = y;
    }

    
}
