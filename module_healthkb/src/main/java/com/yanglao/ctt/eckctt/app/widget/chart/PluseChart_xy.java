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
public class PluseChart_xy {
	private static PluseChart_xy graph;
	private static List<HealthServerBean> oxygenList = new ArrayList();
	public static PluseChart_xy getInstance() {
		if (graph == null) {
			graph = new PluseChart_xy();
		}
		return graph;
	}
	
	/**
	 * @xy
	 * 双折线图，setStandarValue(120) 设置警戒值
	 * 	setyListAxisValuses(getYMaxValuse(pressureList)); 传入所有点的值		
	 */
	public static View getWeekChartView(Context context,
                                        List<HealthServerBean> oxyServerBeanList,
                                        String tag) {
		
		oxygenList = oxyServerBeanList;
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setBackgroundColor(Color.parseColor("#ffffff"));
		renderer.setApplyBackgroundColor(true);
		renderer.setMarginsColor(Color.parseColor("#ffffff"));
		renderer.setAxesColor(Color.parseColor("#d9d9d9"));
		renderer.setPanEnabled(true, false); //设置水平可以滑动，垂直无法滑动
		renderer.setLabelsTextSize(20);	//设置标签的文字大小
		renderer.setMargins(new int[] {20, 25, 20,25});
		renderer.setXLabels(0);
		renderer.setShowGrid(true);
	    renderer.setGridColor(Color.parseColor("#eeeeee"));
		renderer.setPointSize(5f);
		renderer.setClickEnabled(true); //是否可以点击
		renderer.setSelectableBuffer(30); //点击区域的大小
		Align align = renderer.getYAxisAlign(0);
		renderer.setYLabelsAlign(align);
		renderer.setYLabelsColor(0, Color.parseColor("#666666"));
		renderer.setYLabels(8);
		if(oxygenList.size()>0) {
			renderer.setYAxisMin(getMinValue(oxygenList));    //设置y轴最小值是35
			renderer.setYAxisMax(getMaxValue(oxygenList) * 1.2);    //设置y轴最大值是260
		}else{
			renderer.setYAxisMin(0);    //设置y轴最小值是0
			renderer.setYAxisMax(0);    //设置y轴最大值是0
		}
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(7.5);
		renderer.setXLabelsColor(Color.parseColor("#666666"));
		renderer.setAxesColor(Color.parseColor("#666666"));
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setYLabelsPadding(15);//设置label距离Y轴的水平距离
		renderer.setYLabelsVerticalPadding(-5);
		renderer.setFitLegend(true);
		renderer.setShowLegend(false);
		renderer.setLegendTextSize(15);	//设置图例文本大小
		renderer.setLabelsTextSize(15); // 设置轴标签文本大小
		
		renderer.setZoomEnabled(false, false);
		for (int i = 0; i < oxygenList.size();i++) {
			renderer.addXTextLabel(i+1,oxygenList.get(i).getCreateTime());
		}
		
		renderer.setPanLimits(new double[]{0, oxygenList.size()+0.5, 0, 0});//设置拖动的最小值最大值
		if(oxygenList.size() > 7){
			renderer.setRange(new double[]{oxygenList.size()-7.5, oxygenList.size()+0.5, -10d, 150d}); //设置chart的视图范围
		}

		XYMultipleSeriesDataset dataset    = getXYMultipleSeriesDataset(tag);
//		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
//		xyRenderer.setColor(Color.parseColor("#008aff"));
//		xyRenderer.setLineWidth(2f);
//		xyRenderer.setDisplayChartValues(true);	//设置显示折线的点对应的值
//		xyRenderer.setChartValuesTextSize(24f);
//		xyRenderer.setDisplayChartValuesDistance(30);
//		xyRenderer.setStandarValue0(94);
//		xyRenderer.setStandarValue1(100);
//		xyRenderer.setHighColor("#ea4620");
//		xyRenderer.setLowColor("#ea4620");
//		xyRenderer.setNormalColor("#8ec21f");
//		xyRenderer.setyListAxisValuses(getOxygenList(oxygenList));
//		xyRenderer.setPointStyle(PointStyle.CIRCLE);	//折线点的样式
//		xyRenderer.setFillBelowLine(false);		//设置折线下方是否填充
//		xyRenderer.setFillPoints(true);
//		renderer.addSeriesRenderer(0,xyRenderer);
		/////////////////////////////////////////////////////////////////
		XYSeriesRenderer xyRenderer1 = new XYSeriesRenderer();
		xyRenderer1.setColor(Color.parseColor("#008aff"));
		xyRenderer1.setLineWidth(2f);
		xyRenderer1.setDisplayChartValues(true);	//设置显示折线的点对应的值
		xyRenderer1.setChartValuesTextSize(24f);
		xyRenderer1.setDisplayChartValuesDistance(30);
		xyRenderer1.setStandarValue0(60f);
		xyRenderer1.setStandarValue1(100f);
		xyRenderer1.setHighColor("#ea4620");
		xyRenderer1.setLowColor("#ea4620");
		xyRenderer1.setNormalColor("#8ec21f");
		xyRenderer1.setyListAxisValuses(getPluserateList(oxygenList));
		xyRenderer1.setPointStyle(PointStyle.CIRCLE);	//折线点的样式
		xyRenderer1.setFillBelowLine(false);	//设置折线下方是否填充
		xyRenderer1.setFillPoints(true);
		renderer.addSeriesRenderer(xyRenderer1);
		
		return ChartFactory.getCubeLineChartView(context, dataset, renderer,0);
//		String[] types = new String[] {LineChart.TYPE, LineChart.TYPE};
//		return ChartFactory.getCombinedXYChartView(context, dataset, renderer, types);
	}

	public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//		XYSeries series  = new XYSeries("Oxygen");
		XYSeries series2 = new XYSeries("Pluserate");
		for (int i = 1; i < oxygenList.size() + 1; i++) {
//			String tempobj =	oxygenList.get(i - 1).oxy+"";
//			double temp1   = Double.parseDouble(tempobj.substring(0, tempobj.indexOf(".")+2));
//			double temp1   = Double.parseDouble(tempobj);
//			series.add(i,temp1);
			//////////////////////////////////////////////////////////
			String tempobj2 =oxygenList.get(i - 1).pulse+"";
//			double temp2    = Double.parseDouble(tempobj2.substring(0, tempobj2.indexOf(".")+2));
			double temp2    = Double.parseDouble(tempobj2);
			series2.add(i,temp2);
		}
//		dataset.addSeries(0,series);
		dataset.addSeries(series2);
		return dataset;
	}
	
	private static List<Float> getOxygenList(List<HealthServerBean> valuse)
	{
		List<Float> temp = new ArrayList<Float>();
		for(int i=0;i<valuse.size();i++)
		{
			temp.add((float)valuse.get(i).oxy);
		}
		return temp;
	}
	
	private static List<Float> getPluserateList(List<HealthServerBean> valuse)
	{
		List<Float> temp = new ArrayList<Float>();
		for(int i=0;i<valuse.size();i++)
		{
			temp.add((float)valuse.get(i).pulse);
		}
		return temp;
	}
	
	private static double getMaxValue(List<HealthServerBean> valuse){
		double max = 0.0;
		for(int i=0;i<valuse.size();i++){
			HealthServerBean oxygen = valuse.get(i);
			double oxy = oxygen.oxy;
			double pulse = oxygen.pulse;
			if(oxy > max){
				max = oxy;
			}
			if(pulse > max){
				max = pulse;
			}
		}
		return max;
	}
	
	private static double getMinValue(List<HealthServerBean> valuse){
		double min = 0.0;
		for(int i=0;i<valuse.size();i++){
			HealthServerBean oxygen = valuse.get(i);
			double oxy = oxygen.oxy;
			double pulse = oxygen.pulse;
			if(oxy < min){
				min = oxy;
			}
			if(pulse < min){
				min = pulse;
			}
		}
		if(min == 0){
			min = -50;
		}
		return min;
	}

}
