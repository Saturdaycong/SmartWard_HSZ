package com.yanglao.ctt.eckctt.app.widget.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

import com.yanglao.ctt.eckctt.mvp.model.entity.HealthServerBean;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ResourceAsColor")
public class CHOLChart_xy {
    private static CHOLChart_xy graph;
    private static List<HealthServerBean> cholList = new ArrayList<HealthServerBean>();

    public static CHOLChart_xy getInstance() {
        if (graph == null) {
            graph = new CHOLChart_xy();
        }
        return graph;
    }

    /**
     * @xy 双折线图，setStandarValue(120) 设置警戒值
     * setyListAxisValuses(getYMaxValuse(pressureList)); 传入所有点的值
     */
    public static View getWeekChartView(Context context, List<HealthServerBean> cholGradeList, String tag) {

        cholList = cholGradeList;
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setBackgroundColor(Color.parseColor("#ffffff"));
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.parseColor("#ffffff"));
        renderer.setAxesColor(Color.parseColor("#d9d9d9"));
        renderer.setPanEnabled(true, false); //设置水平可以滑动，垂直无法滑动
        renderer.setLabelsTextSize(20);    //设置标签的文字大小
        renderer.setMargins(new int[]{20, 25, 20, 25});
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
        renderer.setYAxisMin(getMinValue(cholList));    //设置y轴最小值是0
        renderer.setYAxisMax(getMaxValue(cholList) * 1.5);    //设置y轴最大值是10
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(7.5);
        renderer.setXLabelsColor(Color.parseColor("#d9d9d9"));
        renderer.setAxesColor(Color.parseColor("#d9d9d9"));
        renderer.setYLabelsAlign(Align.LEFT);
        renderer.setYLabelsPadding(15);//设置label距离Y轴的水平距离
        renderer.setYLabelsVerticalPadding(-4);
        renderer.setFitLegend(true);
        renderer.setShowLegend(false);
        renderer.setLegendTextSize(12);    //设置图例文本大小
        renderer.setLabelsTextSize(16); // 设置轴标签文本

        renderer.setZoomEnabled(false, false);
        for (int i = 0; i < cholList.size(); i++) {
            renderer.addXTextLabel(i + 1, cholList.get(i).getCreateTime());
        }

        renderer.setPanLimits(new double[]{0, cholList.size() + 0.5, 0, 0});//设置拖动的最小值最大值
        if (cholList.size() > 7) {
            renderer.setRange(new double[]{cholList.size() - 7.5, cholList.size() + 0.5, 0d, 10d}); //设置chart的视图范围
        }

        XYMultipleSeriesDataset dataset    = getXYMultipleSeriesDataset(tag);
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.parseColor("#008aff"));
        xyRenderer.setLineWidth(2f);
        xyRenderer.setDisplayChartValues(true);    //设置显示折线的点对应的值
        xyRenderer.setChartValuesTextSize(18f);
        xyRenderer.setDisplayChartValuesDistance(30);
        xyRenderer.setStandarValue0(3.12f);
        xyRenderer.setStandarValue1(5.17f);
        xyRenderer.setHighColor("#ea4620");
        xyRenderer.setLowColor("#ea4620");
        xyRenderer.setNormalColor("#8ec21f");
        xyRenderer.setyListAxisValuses(getCHOLList(cholList));
        xyRenderer.setPointStyle(PointStyle.CIRCLE);    //折线点的样式
        xyRenderer.setFillBelowLine(false);        //设置折线下方是否填充
        xyRenderer.setFillPoints(true);
        renderer.addSeriesRenderer(xyRenderer);
        return ChartFactory.getCubeLineChartView(context, dataset, renderer, 0);

    }

    public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series  = new XYSeries("TUserChol");
        for (int i = 1; i < cholList.size() + 1; i++) {
            String tempobj = cholList.get(i - 1).cho + "";
            if (tempobj.length() > 4) {
                tempobj = tempobj.substring(0, 4);
            }
            double temp1 = Double.parseDouble(tempobj);
            series.add(i, temp1);
        }
        dataset.addSeries(series);
        return dataset;
    }

    private static List<Float> getCHOLList(List<HealthServerBean> valuse) {
        List<Float> temp = new ArrayList<Float>();
        for (int i = 0; i < valuse.size(); i++) {
            temp.add(Float.valueOf(valuse.get(i).cho));
        }
        return temp;
    }

    private static double getMaxValue(List<HealthServerBean> data) {
        double max = 0.0;
        for (HealthServerBean chol : data) {
            if (max < Double.parseDouble(chol.cho)) {
                max = Double.parseDouble(chol.cho);
            }
        }
        return max;
    }

    private static double getMinValue(List<HealthServerBean> data) {
        double min = 0.0;
        for (HealthServerBean chol : data) {
            if (min > Double.parseDouble(chol.cho)) {
                min = Double.parseDouble(chol.cho);
            }
        }
        return min;
    }

}
