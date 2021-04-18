package com.yanglao.ctt.eckctt.app.widget.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;
import com.jess.arms.database.serverdb.TUserWeight;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ResourceAsColor")
public class WeightChart_xy {
    private static WeightChart_xy graph;
    private  List<TUserWeight> weightList = new ArrayList<TUserWeight>();

    public static WeightChart_xy getInstance() {
        if (graph == null) {
            graph = new WeightChart_xy();
        }
        return graph;
    }

    /**
     * @xy 双折线图，setStandarValue(120) 设置警戒值
     * setyListAxisValuses(getYMaxValuse(pressureList)); 传入所有点的值
     */
    public  View getWeekChartView(Context context, List<TUserWeight> temptureGradeList, String tag, float minWeight, float maxWeight) {
        weightList = temptureGradeList;
        minWeight =  minWeight==0?50:minWeight;
        maxWeight =  maxWeight==0?70:maxWeight;

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setBackgroundColor(Color.parseColor("#ffffff"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#ffffff"));
        renderer.setAxesColor(Color.parseColor("#d9d9d9"));
        renderer.setPanEnabled(true, false); //设置水平可以滑动，垂直无法滑动
        renderer.setLabelsTextSize(20);    //设置标签的文字大小
        renderer.setMargins(new int[]{20, 25, 20, 25});
        renderer.setShowAxes(true);
        renderer.setXLabels(0);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.parseColor("#eeeeee"));
        renderer.setPointSize(5f);
        renderer.setClickEnabled(true); //是否可以点击
        renderer.setSelectableBuffer(30); //点击区域的大小
        Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.parseColor("#d9d9d9"));
        renderer.setYLabels(8);
        renderer.setYAxisMin(getMinValue(weightList));    //设置y轴最小值是5
//        renderer.setYAxisMax(getMaxValue(weightList) * 1.2);    //设置y轴最大值是120
        renderer.setYAxisMax(getMaxValue(weightList) * 2.0);    //设置y轴最大值是120
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(7.5);
//        renderer.setXAxisMax(10);
        renderer.setXLabelsColor(Color.parseColor("#d9d9d9"));
        renderer.setAxesColor(Color.parseColor("#d9d9d9"));
        renderer.setYLabelsAlign(Align.CENTER);
        renderer.setYLabelsPadding(15);//设置label距离Y轴的水平距离
        renderer.setYLabelsVerticalPadding(-4);
        renderer.setFitLegend(true);
        renderer.setShowLegend(false);
        renderer.setLegendTextSize(12);    //设置图例文本大小
        renderer.setLabelsTextSize(16); // 设置轴标签文本大小

        renderer.setZoomEnabled(false, false);
        for (int i = 0; i < weightList.size(); i++) {
            renderer.addXTextLabel(i + 1, weightList.get(i).getCreateTime2());
        }

        renderer.setPanLimits(new double[]{0, weightList.size() + 0.5, 0, 0});//设置拖动的最小值最大值
        if (weightList.size() > 7) {
            renderer.setRange(new double[]{weightList.size() - 7.5, weightList.size() + 0.5, 5d, 220d}); //设置chart的视图范围
        }

        XYMultipleSeriesDataset dataset    = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        ////TODO
        xyRenderer.setColor(Color.parseColor("#008aff"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);    //设置显示折线的点对应的值
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);
        xyRenderer.setStandarValue0(minWeight);
        xyRenderer.setStandarValue1(maxWeight);
        xyRenderer.setHighColor("#ea4620");
        xyRenderer.setLowColor("#ea4620");
        xyRenderer.setNormalColor("#8ec21f");
        xyRenderer.setyListAxisValuses(getWeightList(weightList));
        xyRenderer.setPointStyle(PointStyle.CIRCLE);    //折线点的样式        xyRenderer.setFillBelowLine(false);        //设置折线下方是否填充
        xyRenderer.setFillPoints(true);
        renderer.addSeriesRenderer(xyRenderer);
        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);

    }

    public XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series  = new XYSeries("Temperture");
        for (int i = 1; i < weightList.size() + 1; i++) {
            String tempobj = weightList.get(i - 1).getWeight() + "";
            double temp1   = Double.parseDouble(tempobj.substring(0, tempobj.indexOf(".") + 2));
            series.add(i, temp1);
        }
        dataset.addSeries(series);
        return dataset;
    }

    private static List<Float> getWeightList(List<TUserWeight> valuse) {
        List<Float> temp = new ArrayList<Float>();
        for (int i = 0; i < valuse.size(); i++) {
            temp.add((float) valuse.get(i).getWeight());
        }
        return temp;
    }

    private static double getMaxValue(List<TUserWeight> valuse) {
        double max = 0.0;
        for (int i = 0; i < valuse.size(); i++) {
            TUserWeight wg = valuse.get(i);
            double weight = wg.getWeight()==null?0.0:wg.getWeight();
            if (weight > max) {
                max = weight;
            }
        }
        return max;
    }

    private static double getMinValue(List<TUserWeight> valuse) {
        double min = 0.0;
        for (int i = 0; i < valuse.size(); i++) {
            TUserWeight wg = valuse.get(i);
            double weight = wg.getWeight()==null?0.0:wg.getWeight();
            if (weight < min) {
                min = weight;
            }
        }
//        if (min == 0) {
//            min = -50;
//        }
        return min;
    }
}
