import java.awt.*;

class Pixel {
    private int x, y;
    private Color color;

    public Pixel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void draw(Graphics g, int cellSize) {
        g.setColor(color);
        g.fillRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
        g.setColor(Color.BLACK);
        g.drawRect(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
    }
}