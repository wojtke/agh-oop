package agh.ics.oop.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ChartView {
    private LineChart<Number, Number> lineChart;
    private final NumberAxis xAxis, yAxis;

    private int update_counter=0;

    public ChartView(XYChart.Series<Number, Number> series) {
        this.xAxis = new NumberAxis(0, 100, 10);

        this.yAxis = new NumberAxis(0, 100, 10);
        this.yAxis.setAutoRanging(true);

        this.lineChart = new LineChart<>(xAxis, yAxis);

        this.lineChart.getData().add(series);
        this.lineChart.setCreateSymbols(false);
    }

    public ChartView(XYChart.Series<Number, Number> series1, XYChart.Series<Number, Number> series2) {
        this(series1);
        this.lineChart.getData().add(series2);
    }

    public LineChart<Number, Number> getView() {
        return this.lineChart;
    }

    public void updateAxis(int n) {
        if (update_counter==0) {
            int last_epoch = lineChart.getData().get(0).getData().size();
            this.xAxis.setLowerBound(last_epoch - (int) (0.75 * n)-1);
            this.xAxis.setUpperBound(last_epoch + (int) (0.25 * n)-1);
            this.xAxis.setTickUnit( (int)(n*0.25));

            this.update_counter = (int) (0.2 * n) -1;
        } else {
            update_counter--;
        }
    }
}
