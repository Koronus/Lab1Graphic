import javax.swing.*;
import java.awt.*;

public class LabaDrawing extends JFrame {
    private DrawingPanel cdaPanel;
    private DrawingPanel brezenhamPanel;
    private DrawingPanel lineBrezenhamPanel;

    public LabaDrawing() {
        setTitle("Лабораторная работа: Алгоритмы ЦДА и Брезенхема");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 950);


        JPanel mainPanel = new JPanel(new BorderLayout(10, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));


        cdaPanel = new DrawingPanel("ЦДА (DDA)");
        cdaPanel.setBackground(Color.WHITE);
        cdaPanel.setPreferredSize(new Dimension(400, 350));



        brezenhamPanel = new DrawingPanel("Брезенхем");
        brezenhamPanel.setBackground(Color.WHITE);
        brezenhamPanel.setPreferredSize(new Dimension(400, 350));


        topWrapper.add(cdaPanel);
        topWrapper.add(brezenhamPanel);


        JPanel bottomWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        lineBrezenhamPanel = new DrawingPanel("Интерактивная");
        lineBrezenhamPanel.setBackground(Color.WHITE);
        lineBrezenhamPanel.setPreferredSize(new Dimension(850, 300));



        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        bottomWrapper.add(lineBrezenhamPanel, gbc);


        mainPanel.add(topWrapper, BorderLayout.NORTH);
        mainPanel.add(bottomWrapper, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LabaDrawing().setVisible(true);
        });
    }
}