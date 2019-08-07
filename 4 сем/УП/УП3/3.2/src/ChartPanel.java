import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;

class ChartPanel extends JPanel {
    private PieChart chart;

    public ChartPanel(double[] values, String[] names, String title) {
        chart = new PieChartBuilder().width(700).height(600).title(title).theme(Styler.ChartTheme.GGPlot2).build();
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndValue);
        chart.getStyler().setDrawAllAnnotations(true);
        chart.getStyler().setAnnotationDistance(1.1);
        chart.getStyler().setPlotContentSize(0.7);
        chart.getStyler().setStartAngleInDegrees(270);
        chart.getStyler().setChartBackgroundColor(new Color(0xA0A0A0));
        chart.getStyler().setPlotBackgroundColor(new Color(0xA0A0A0));
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0xA0A0A0));
        chart.getStyler().setChartTitleBoxBorderColor(new Color(0xA0A0A0));
        chart.getStyler().setPlotBorderColor(new Color(0xA0A0A0));
        chart.getStyler().setAnnotationsFont(new Font("Verdana", Font.PLAIN, 14));
        chart.getStyler().setChartTitleFont(new Font("Verdana", Font.BOLD, 30));
        for (int i = 0; i < names.length; i++) {
            chart.addSeries(names[i], values[i]);
        }
        add(new XChartPanel<>(chart));
    }
}