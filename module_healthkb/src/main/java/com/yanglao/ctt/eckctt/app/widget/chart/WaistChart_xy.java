package com.yanglao.ctt.eckctt.app.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.jess.arms.database.serverdb.TUserWaistline;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bubblefish on 2016/7/25.
 */
public class WaistChart_xy {
    private static WeightChart_xy graph;
    private static List<TUserWaistline> waistList = new ArrayList<TUserWaistline>();

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
    public static View getWeekChartView(Context context,
                                        List<TUserWaistline> temptureGradeList,
                                        String tag) {

        waistList = temptureGradeList;
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
        Paint.Align align = renderer.getYAxisAlign(0);
        renderer.setYLabelsAlign(align);
        renderer.setYLabelsColor(0, Color.parseColor("#d9d9d9"));
        renderer.setYLabels(8);
        renderer.setYAxisMin(getMinValue(waistList));    //设置y轴最小值是5
        renderer.setYAxisMax(getMaxValue(waistList) * 1.2);    //设置y轴最大值是120
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(7.5);
        renderer.setXLabelsColor(Color.parseColor("#d9d9d9"));
        renderer.setAxesColor(Color.parseColor("#d9d9d9"));
        renderer.setYLabelsAlign(Paint.Align.CENTER);
        renderer.setYLabelsPadding(15);//设置label距离Y轴的水平距离
        renderer.setYLabelsVerticalPadding(-4);
        renderer.setFitLegend(true);
        renderer.setShowLegend(false);
        renderer.setLegendTextSize(12);    //设置图例文本大小
        renderer.setLabelsTextSize(16); // 设置轴标签文本大小

        renderer.setZoomEnabled(false, false);
        for (int i = 0; i < waistList.size(); i++) {
            renderer.addXTextLabel(i + 1, waistList.get(i).getCreateTime2());
        }

        renderer.setPanLimits(new double[]{0, waistList.size() + 0.5, 0, 0});//设置拖动的最小值最大值
        if (waistList.size() > 7) {
            renderer.setRange(new double[]{waistList.size() - 7.5, waistList.size() + 0.5, 5d, 250d}); //设置chart的视图范围
        }

        XYMultipleSeriesDataset dataset = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        //TODO
        xyRenderer.setColor(Color.parseColor("#008aff"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);    //设置显示折线的点对应的值
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);

        //设置区间值
//        float[] waistRange = AnalyzeDataHelper.getInstance().getWaistRange();
//        xyRenderer.setStandarValue0(waistRange[0]>0?waistRange[0]:30);
//        xyRenderer.setStandarValue1(waistRange[1]>0?waistRange[1]:120);

        xyRenderer.setHighColor("#ea4620");
        xyRenderer.setLowColor("#ea4620");
        xyRenderer.setNormalColor("#8ec21f");
        xyRenderer.setyListAxisValuses(getWaistList(waistList));
        xyRenderer.setPointStyle(PointStyle.CIRCLE);    //折线点的样式
        xyRenderer.setFillBelowLine(false);        //设置折线下方是否填充
        xyRenderer.setFillPoints(true);
        renderer.addSeriesRenderer(xyRenderer);
        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);

    }

    public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("Temperture");
        for (int i = 1; i < waistList.size() + 1; i++) {
            String tempobj = waistList.get(i - 1).getWaistline() + "";
            double temp1 = Double.parseDouble(tempobj.substring(0, tempobj.indexOf(".") + 2));
            series.add(i, temp1);
        }
        dataset.addSeries(series);
        return dataset;
    }

    private static List<Float> getWaistList(List<TUserWaistline> valuse) {
        List<Float> temp = new ArrayList<Float>();
        for (int i = 0; i < valuse.size(); i++) {
            temp.add(Float.parseFloat(valuse.get(i).getWaistline() + ""));
        }
        return temp;
    }

    private static double getMaxValue(List<TUserWaistline> valuse) {
        double max = 0.0;
        for (int i = 0; i < valuse.size(); i++) {
            TUserWaistline wt = valuse.get(i);
            double waist = wt.getWaistline();
            if (waist > max) {
                max = waist;
            }
        }
        return max;
    }

    private static double getMinValue(List<TUserWaistline> valuse) {
        double min = 0.0;
        for (int i = 0; i < valuse.size(); i++) {
            TUserWaistline wt = valuse.get(i);
            double waist = wt.getWaistline();
            if (waist < min) {
                min = waist;
            }
        }
        if (min == 0) {
            min = 0;
        }
        return min;
    }
}
