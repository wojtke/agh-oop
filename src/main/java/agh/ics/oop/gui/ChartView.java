package agh.ics.oop.gui;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class ChartView {
    private final LineChart<Number, Number> lineChart;
    private final NumberAxis xAxis, yAxis;

    private final int range=100;

    private final ArrayList<ArrayList<Number>> statsArrays = new ArrayList<>();
    private final ArrayList<String> statsNames = new ArrayList<>();

    public ChartView(ArrayList<Number> statsArray, String name) {
        this.statsArrays.add(statsArray);
        this.statsNames.add(name);

        this.xAxis = new NumberAxis(0,0,0);
        this.xAxis.setLabel("Epoch");
        this.yAxis = new NumberAxis(0,0,0);
        this.yAxis.setLabel("Value");
        this.yAxis.setAutoRanging(true);

        this.lineChart = new LineChart<>(xAxis, yAxis);
        this.lineChart.setCreateSymbols(false);
        this.lineChart.setLegendVisible(false);
        this.lineChart.setAnimated(false);
        this.lineChart.setLegendVisible(true);

        this.lineChart.setLayoutX(0);
        this.lineChart.setLayoutY(0);
        this.lineChart.setPrefSize(500, 250);
        this.lineChart.paddingProperty().set(new javafx.geometry.Insets(20, 20, 20, 20));


        this.update(0);
    }

    public ChartView(ArrayList<Number> statsArray1, ArrayList<Number> statsArray2, String name1, String name2) {
        this(statsArray1, name1);
        this.statsArrays.add(statsArray2);
        this.statsNames.add(name2);
    }
    public ChartView(ArrayList<Number>[] statsArrays, ArrayList<String> statsNames) {
        this(statsArrays[0], statsNames.get(0));
        for (int i = 1; i < statsArrays.length; i++) {
            this.statsArrays.add(statsArrays[i]);
            this.statsNames.add(statsNames.get(i));
        }
    }


    public LineChart<Number, Number> getView() {
        return this.lineChart;
    }

    public void update(int current_epoch) {

        if (this.lineChart.getData() != null) {
            this.lineChart.getData().clear();
        }

        for (ArrayList<Number> statsArray : this.statsArrays) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            int start = Math.max(0, (statsArray.size() - range));
            List<Number> lastStats = new ArrayList<Number>(statsArray.subList(start, statsArray.size()));
            for (int i = 0; i < lastStats.size(); i++) {
                series.getData().add(new XYChart.Data<>(start + i, lastStats.get(i)));
            }

            series.setName(this.statsNames.get(this.statsArrays.indexOf(statsArray)));
            this.lineChart.getData().add(series);
        }

        this.updateAxis(current_epoch);
    }

    private void updateAxis(int current_epoch) {
        this.xAxis.setLowerBound(current_epoch - (int) (0.9 * range)-1);
        this.xAxis.setUpperBound(current_epoch + (int) (0.1 * range)-1);
        this.xAxis.setTickUnit( (int)(range*0.1));
    }

}
