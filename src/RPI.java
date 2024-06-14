import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

public class RPI extends JPanel {
    private static final int PIE_DURCHMESSER = 400;
    private double[] angles;
    private Color[] colors;
    private int sliceCount;

    public RPI() {
        Random r = new Random();
        double totalAngle = 0;
        double maxAngle = 360;
        int maxSlices = 20;

        angles = new double[maxSlices];
        colors = new Color[maxSlices];

        while (totalAngle < maxAngle) {
            double angle = r.nextInt(90) + 10;
            if (totalAngle + angle > maxAngle) {
                angle = maxAngle - totalAngle;
            }
            angles[sliceCount] = angle;
            colors[sliceCount] = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
            totalAngle += angle;

            sliceCount++;

            if (sliceCount >= maxSlices) {
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int x = (getWidth() - PIE_DURCHMESSER) / 2;
        int y = (getHeight() - PIE_DURCHMESSER) / 2;

        double currAngle = 0;

        for (int i = 0; i < sliceCount; i++) {
            g2d.setColor(colors[i]);
            double angle = angles[i];
            g2d.fillArc(x, y, PIE_DURCHMESSER, PIE_DURCHMESSER, (int) currAngle, (int) angle);
            currAngle += angle;
        }

        // Prozent
        currAngle = 0;
        for (int i = 0; i < sliceCount; i++) {
            double angle = angles[i];
            double midAngle = currAngle + angle / 2;
            double percentage = (angle / 360) * 100;
            g2d.setColor(Color.BLACK);
            double rad = Math.toRadians(midAngle);
            int labelX = x + (int) (PIE_DURCHMESSER / 2 + (PIE_DURCHMESSER / 2.5) * Math.cos(rad));
            int labelY = y + (int) (PIE_DURCHMESSER / 2 - (PIE_DURCHMESSER / 2.5) * Math.sin(rad));
            g2d.drawString(String.format("%.1f%%", percentage), labelX, labelY);
            currAngle += angle;
        }
    }

    private static void GUI() {
        JFrame frame = new JFrame("Random Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RPI());
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        String[] options = {"Wiederholen", "nou"};
        int ans = showOptionDialog(null,
                "Wiederholen oder nou?","Frage",
                YES_NO_CANCEL_OPTION,QUESTION_MESSAGE,null, options, options[0]);
        if(ans == 0){
            frame.dispose();
            main(new String[]{});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RPI::GUI);
    }
}
