import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlotGenerator {

    public static void main(String[] args) {
        // results/plots папкасын тексеру немесе құру
        File plotsDir = new File("results/plots");
        if (!plotsDir.exists()) {
            plotsDir.mkdirs();
        }

        generateExecutionTimePlot();
        generateOperationsPlot();

        System.out.println("✔ Барлық графиктер Java арқылы results/plots/ папкасына сәтті сақталды!");
    }

    // 1-ГРАФИК: Execution Time vs N
    private static void generateExecutionTimePlot() {
        int width = 800;
        int height = 500;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Сглаживание (Anti-aliasing)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Фонды ақ түске бояу
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Тақырыбы мен осьтері
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Plot 1: Execution Time vs. N", 260, 40);

        // Остер сызу
        int padding = 70;
        int graphWidth = width - 2 * padding;
        int graphHeight = height - 2 * padding;

        g2d.drawLine(padding, height - padding, width - padding, height - padding); // X осі
        g2d.drawLine(padding, padding, padding, height - padding);               // Y осі

        // Деректер (N = 100, 1000, 10000, 100000)
        double[] daTime = {0.02, 0.03, 0.05, 0.08};
        double[] llTime = {0.45, 5.12, 68.40, 890.15};
        double[] heapTime = {0.05, 0.42, 5.10, 62.30};

        // Тек тор сызықтары (Grid)
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 1; i <= 4; i++) {
            int y = height - padding - (i * graphHeight / 4);
            g2d.drawLine(padding, y, width - padding, y);
        }

        // Сызықтарды сызу
        drawLineGraph(g2d, daTime, Color.BLUE, "Dynamic Array", padding, graphWidth, graphHeight, height, 0);
        drawLineGraph(g2d, llTime, Color.RED, "Linked List", padding, graphWidth, graphHeight, height, 1);
        drawLineGraph(g2d, heapTime, Color.GREEN.darker(), "Min-Heap", padding, graphWidth, graphHeight, height, 2);

        // Тақырыпшалар
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 12));
        g2d.drawString("Input Size (N)", width / 2 - 30, height - 20);
        g2d.drawString("Time (ms)", 10, height / 2);

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("results/plots/execution_time_vs_n.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 2-ГРАФИК: Operations vs N
    private static void generateOperationsPlot() {
        int width = 800;
        int height = 500;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString("Plot 2: Operations / Accesses vs. N", 230, 40);

        int padding = 70;
        int graphWidth = width - 2 * padding;
        int graphHeight = height - 2 * padding;

        g2d.drawLine(padding, height - padding, width - padding, height - padding);
        g2d.drawLine(padding, padding, padding, height - padding);

        double[] daOps = {10000, 10000, 10000, 10000};
        double[] llOps = {50000, 500000, 5000000, 50000000};
        double[] heapOps = {1329, 13290, 132900, 1329000};

        drawLineGraph(g2d, daOps, Color.BLUE, "DA Accesses", padding, graphWidth, graphHeight, height, 0);
        drawLineGraph(g2d, llOps, Color.RED, "LL Accesses", padding, graphWidth, graphHeight, height, 1);
        drawLineGraph(g2d, heapOps, Color.GREEN.darker(), "Heap Comparisons", padding, graphWidth, graphHeight, height, 2);

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("results/plots/operations_vs_n.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void drawLineGraph(Graphics2D g2d, double[] data, Color color, String legend,
                                      int padding, int graphWidth, int graphHeight, int height, int legendIndex) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2.5f));

        int points = data.length;
        int[] xPoints = new int[points];
        int[] yPoints = new int[points];

        // Масштабтау үшін максималды мәнді табу
        double max = data[points - 1] == 0 ? 1 : data[points - 1];

        for (int i = 0; i < points; i++) {
            xPoints[i] = padding + (i * graphWidth / (points - 1));
            // Нормализация (0 мен graphHeight арасында)
            yPoints[i] = height - padding - (int) ((data[i] / max) * (graphHeight - 40));
        }

        for (int i = 0; i < points - 1; i++) {
            g2d.drawLine(xPoints[i], yPoints[i], xPoints[i + 1], yPoints[i + 1]);
            g2d.fillOval(xPoints[i] - 4, yPoints[i] - 4, 8, 8);
        }
        g2d.fillOval(xPoints[points - 1] - 4, yPoints[points - 1] - 4, 8, 8);

        // Легенда (Белгішесі)
        int legendX = padding + 20;
        int legendY = padding + 20 + (legendIndex * 25);
        g2d.drawString(legend, legendX + 30, legendY + 5);
        g2d.fillRect(legendX, legendY - 5, 20, 10);
    }
}