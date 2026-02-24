import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class DrawingPanel extends JPanel {
    private List<Pixel> pixels = new ArrayList<>();
    private static final int GRID_SIZE = 35;
    private static final int OFFSET_X = 2;
    private static final int OFFSET_Y = 0;
    private static final int PIXEL_SIZE = 8;
    private String algorithm;

    private int lineX1 = 10, lineY1 = 10;
    private int lineX2 = 30, lineY2 = 30;

    private Random random = new Random();


    public DrawingPanel(String algorithm) {
        this.algorithm = algorithm;
        int panelSize = GRID_SIZE * PIXEL_SIZE;
        setPreferredSize(new Dimension(panelSize + 100, panelSize + 100));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        if (algorithm.equals("Интерактивная")) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    changeLineDirection();
                }
            });
        }

        draw(algorithm);
    }

    private void changeLineDirection() {
        lineX1 = random.nextInt(GRID_SIZE - 20) + 10;
        lineY1 = random.nextInt(GRID_SIZE - 20) + 10;
        lineX2 = random.nextInt(GRID_SIZE - 20) + 10;
        lineY2 = random.nextInt(GRID_SIZE - 20) + 10;
        draw(algorithm);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();


        int cellSize = Math.min((width - 40) / GRID_SIZE, (height - 40) / GRID_SIZE);
        int offsetX = (width - cellSize * GRID_SIZE) / 2;
        int offsetY = (height - cellSize * GRID_SIZE) / 2;


        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= GRID_SIZE; i++) {
            int x = offsetX + i * cellSize;
            int y = offsetY + i * cellSize;
            g.drawLine(x, offsetY, x, offsetY + cellSize * GRID_SIZE);
            g.drawLine(offsetX, y, offsetX + cellSize * GRID_SIZE, y);
        }


        g.setColor(Color.BLACK);
        g.drawLine(offsetX, offsetY, offsetX + cellSize * GRID_SIZE, offsetY);
        g.drawLine(offsetX, offsetY, offsetX, offsetY + cellSize * GRID_SIZE);


        g.setColor(Color.BLACK);
        g.drawString("(0,0)", offsetX + 5, offsetY + 15);
        g.drawString("(" + GRID_SIZE + ",0)", offsetX + cellSize * GRID_SIZE - 60, offsetY + 15);
        g.drawString("(0," + GRID_SIZE + ")", offsetX + 5, offsetY + cellSize * GRID_SIZE - 10);


        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(offsetX, offsetY);

        for (Pixel pixel : pixels) {
            pixel.draw(g2d, cellSize);
        }

        g2d.translate(-offsetX, -offsetY);
    }

    public void draw(String algorithm) {
        clear();

        if (algorithm.equals("ЦДА (DDA)")) {
            drawDDAFigure();
        } else if (algorithm.equals("Брезенхем")) {
            drawBresenhamFigure();
        } else if (algorithm.equals("Интерактивная")) {
            drawInteractiveLine();
        }

        repaint();
    }

    private void drawDDAFigure() {
        for (double[] line :  Сoordinates.LINES) {
            ddaLine(line[0] + OFFSET_X, line[1] + OFFSET_Y,
                    line[2] + OFFSET_X, line[3] + OFFSET_Y);
        }
    }

    private void drawBresenhamFigure() {

        for (double[] line :  Сoordinates.LINES) {
            bresenhamLine(line[0] + OFFSET_X, line[1] + OFFSET_Y,
                    line[2] + OFFSET_X, line[3] + OFFSET_Y);
        }

    }

    private void drawInteractiveLine() {
        bresenhamLine(lineX1, lineY1, lineX2, lineY2);
        addPixel(lineX1, lineY1, Color.GREEN);
        addPixel(lineX2, lineY2, Color.RED);
        System.out.println(lineX1+","+lineX1+" -> "+lineX2+","+lineY2);
    }

    private void ddaLine(double x1, double y1, double x2, double y2) {
        int ix1 = (int)x1;
        int iy1 = (int)y1;
        int ix2 = (int)x2;
        int iy2 = (int)y2;

        int dx = ix2 - ix1;
        int dy = iy2 - iy1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) {
            addPixel(ix1, iy1, Color.RED);
            return;
        }

        double xIncrement = (double) dx / steps;
        double yIncrement = (double) dy / steps;

        double x = x1;
        double y = y1;

        for (int i = 0; i <= steps; i++) {
            int pixelX = (int)Math.round(x);
            int pixelY = (int)Math.round(y);
            addPixel(pixelX, pixelY, Color.RED);
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void bresenhamLine(double x1, double y1, double x2, double y2) {
        int dx = Math.abs((int)x2 - (int)x1);
        int dy = Math.abs((int)y2 - (int)y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;

        int x = (int)x1;
        int y = (int)y1;

        while (true) {
            addPixel(x , y, Color.blue);
            if (x == x2 && y == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x += sx;
            }
            if (e2 < dx) {
                err += dx;
                y += sy;
            }
        }
    }

    private void addPixel(int x, int y, Color color) {
        int screenY = GRID_SIZE - 1 - y;

        if (x >= 0 && x < GRID_SIZE && screenY >= 0 && screenY < GRID_SIZE) {
            pixels.add(new Pixel(x, screenY, color));
        }
    }

    public void clear() {
        pixels.clear();
        repaint();
    }
}