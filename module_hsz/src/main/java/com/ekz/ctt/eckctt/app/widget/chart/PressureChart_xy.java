package com.ekz.ctt.eckctt.app.widget.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.view.View;

import com.ekz.ctt.eckctt.mvp.model.entity.TUserBloodPressure;

import org.achartengine.ChartFactory;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * 血压
 */
@SuppressLint("ResourceAsColor")
public class PressureChart_xy {
	private static PressureChart_xy graph;
	private static List<TUserBloodPressure> pressureList = new ArrayList<TUserBloodPressure>();
	public static PressureChart_xy getInstance() {
		if (graph == null) {
			graph = new PressureChart_xy();
		}
		return graph;
	}
	
	/**
	 * @xy
	 * 双折线图，setStandarValue(120) 设置警戒值
	 * 	setyListAxisValuses(getYMaxValuse(pressureList)); 传入所有点的值		
	 */
	public static View getWeekChartView(Context context,
                                        List<TUserBloodPressure> temptureGradeList,
                                        String tag){
		
		pressureList = temptureGradeList;
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setBackgroundColor(Color.parseColor("#ffffff"));
		renderer.setApplyBackgroundColor(true);
		renderer.setMarginsColor(Color.parseColor("#ffffff"));
		renderer.setAxesColor(Color.parseColor("#d9d9d9"));
		renderer.setPanEnabled(true, false); //设置水平可以滑动，垂直无法滑动
		renderer.setLabelsTextSize(30);	//设置标签的文字大小
		renderer.setMargins(new int[] {20, 28, 20,25});
		//renderer.setPanLimits(new double[]{0, pressureList.size()+0.5, 0, 0});
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
		if(pressureList.size()>0) {
			renderer.setYAxisMin(getMinValue(pressureList));    //设置y轴最小值是35
			renderer.setYAxisMax(getMaxValue(pressureList) * 1.2);    //设置y轴最大值是260
		}else{
			renderer.setYAxisMin(0);    //设置y轴最小值是0
			renderer.setYAxisMax(0);    //设置y轴最大值是0
		}
		renderer.setXAxisMin(0.5);
		renderer.setXAxisMax(7.5);
		//#22ad38
		renderer.setXLabelsColor(Color.parseColor("#d9d9d9"));
		renderer.setAxesColor(Color.parseColor("#d9d9d9"));
		renderer.setYLabelsAlign(Align.CENTER);
		renderer.setYLabelsPadding(15);//设置label距离Y轴的水平距离
		renderer.setYLabelsVerticalPadding(-10);
		renderer.setFitLegend(true);
		renderer.setShowLegend(false);
		renderer.setLegendTextSize(12);	//设置图例文本大小	
		renderer.setLabelsTextSize(16); // 设置轴标签文本大小
		
		renderer.setZoomEnabled(false, false);
		for (int i = 0; i < pressureList.size();i++) {
			renderer.addXTextLabel(i+1,pressureList.get(i).getCreateTime2() );
		}
		
		renderer.setPanLimits(new double[]{0, pressureList.size()+0.5, 0, 0});//设置拖动的最小值最大值
		if(pressureList.size() > 7){
			renderer.setRange(new double[]{pressureList.size()-7.5, pressureList.size()+0.5, -10d, 240d}); //设置chart的视图范围
		}
		XYMultipleSeriesDataset dataset    = getXYMultipleSeriesDataset(tag);
		XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
		xyRenderer.setColor(Color.parseColor("#008aff"));
		xyRenderer.setLineWidth(2f);
		xyRenderer.setDisplayChartValues(true);	//设置显示折线的点对应的值
		xyRenderer.setChartValuesTextSize(22f);
		xyRenderer.setDisplayChartValuesDistance(30);
		xyRenderer.setStandarValue0(90f);
		xyRenderer.setStandarValue1(139f);
		xyRenderer.setHighColor("#ea4620");
		xyRenderer.setLowColor("#ea4620");
		xyRenderer.setNormalColor("#8ec21f");
		xyRenderer.setyListAxisValuses(getYMaxValuse(pressureList));
		xyRenderer.setPointStyle(PointStyle.CIRCLE);	//折线点的样式
		xyRenderer.setFillBelowLine(false);		//设置折线下方是否填充
		xyRenderer.setFillPoints(true);
		renderer.addSeriesRenderer(0,xyRenderer);
		/////////////////////////////////////////////////////////////////
		XYSeriesRenderer xyRenderer1 = new XYSeriesRenderer();
		xyRenderer1.setColor(Color.parseColor("#22ad38"));
		xyRenderer1.setLineWidth(2f);
		xyRenderer1.setDisplayChartValues(true);	//设置显示折线的点对应的值
		xyRenderer1.setChartValuesTextSize(22f);
		xyRenderer1.setDisplayChartValuesDistance(30);
		xyRenderer1.setStandarValue0(60f);
		xyRenderer1.setStandarValue1(89f);
		xyRenderer1.setHighColor("#ea4620");
		xyRenderer1.setLowColor("#ea4620");
		xyRenderer1.setNormalColor("#8ec21f");
		xyRenderer1.setyListAxisValuses(getYMinValuse(pressureList));
		xyRenderer1.setPointStyle(PointStyle.CIRCLE);	//折线点的样式
		xyRenderer1.setFillBelowLine(false);	//设置折线下方是否填充
		xyRenderer1.setFillPoints(true);
		renderer.addSeriesRenderer(1,xyRenderer1);
		
		//return ChartFactory.getCubeLineChartView(context, dataset, renderer,0); 
		String[] types = new String[] {LineChart.TYPE,
                                       LineChart.TYPE};
		return ChartFactory.getCombinedXYChartView(context, dataset, renderer, types);
		
	}

	public static XYMultipleSeriesDataset getXYMultipleSeriesDataset(String tag) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYSeries series  = new XYSeries("Temperture");
		XYSeries series2 = new XYSeries("Tempture2");
		for (int i = 1; i < pressureList.size() + 1; i++) {
			String tempobj =	pressureList.get(i - 1).getSystolicPressure()+"";
			Double temp    = Double.parseDouble(tempobj);
			series.add(i,temp);
			//////////////////////////////////////////////////////////
			String tempobj2 =pressureList.get(i - 1).getDiastolicPressure()+"";
			Double temp2    = Double.parseDouble(tempobj2);
			series2.add(i,temp2);
		}
		dataset.addSeries(0,series);
		dataset.addSeries(1,series2);
		return dataset;
	}
	
	private static List<Float> getYMaxValuse(List<TUserBloodPressure> valuse)
	{
		List<Float> temp = new ArrayList<Float>();
		for(int i=0;i<valuse.size();i++)
		{
			temp.add(Float.valueOf(valuse.get(i).getSystolicPressure()));
		}
		return temp;
	}
	
	private static List<Float> getYMinValuse(List<TUserBloodPressure> valuse)
	{
		List<Float> temp = new ArrayList<Float>();
		for(int i=0;i<valuse.size();i++)
		{
			temp.add(Float.valueOf(valuse.get(i).getDiastolicPressure()));
		}
		return temp;
	}
	
	private static double getMaxValue(List<TUserBloodPressure> valuse){
		double max = 0.0;
		for(int i=0;i<valuse.size();i++){
			TUserBloodPressure ps = valuse.get(i);
			double ssy = ps.getSystolicPressure();
			double szy = ps.getDiastolicPressure();
			if(ssy > max){
				max = ssy;
			}
			if(szy > max){
				max = szy;
			}
		}
		return max;
	}
	
	private static double getMinValue(List<TUserBloodPressure> valuse){
		double min = 0.0;
		
		for(int i=0;i<valuse.size();i++){
			TUserBloodPressure ps = valuse.get(i);
			double ssy = ps.getSystolicPressure();
			double szy = ps.getDiastolicPressure();
			if(ssy < min){
				min = ssy;
			}
			if(szy < min){
				min = szy;
			}
		}
		if(min == 0){
			min = -50;
		}
		return min;
	}
}
