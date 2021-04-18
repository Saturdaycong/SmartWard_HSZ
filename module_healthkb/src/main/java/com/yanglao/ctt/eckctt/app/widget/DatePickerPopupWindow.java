package com.yanglao.ctt.eckctt.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.DateTimeUtil;
import com.yanglao.ctt.eckctt.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class DatePickerPopupWindow extends PopupWindow {
    private static String date = null;
    private OnSelectListener mListener;
    private int defalut;

    public DatePickerPopupWindow(Context context, View parent, OnSelectListener listener) {
        mListener = listener;
        Log.i("infoweek", "DatePickerPopupWindow");
        View view = View.inflate(context, R.layout.popupwindow_calendar, null);
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
        LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        RelativeLayout coverRl = (RelativeLayout) view.findViewById(R.id.coverRl);
        ll_popup.startAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_bottom_in_1));

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        update();
        coverRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        final TextView popupwindow_calendar_month = (TextView) view.findViewById(R.id.popupwindow_calendar_month);
        final KCalendar calendar =  view.findViewById(R.id.popupwindow_calendar);
        Button popupwindow_calendar_bt_enter = (Button) view.findViewById(R.id.popupwindow_calendar_bt_enter);

        popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年" + calendar.getCalendarMonth() + "月");

        if (null != date) {

            int years = Integer.parseInt(date.substring(0, date.indexOf("-")));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
                    date.lastIndexOf("-")));
            popupwindow_calendar_month.setText(years + "年" + month + "月");

            calendar.showCalendar(years, month);
            calendar.setCalendarDayBgColor(date, R.drawable.calendar_date_focused);
        }

        List<String> list = new ArrayList<String>(); //设置标记列表,即日起右下角三角标记
            /*list.add("2015-10-01");
            list.add("2015-10-02");*/
        calendar.addMarks(list, 0);

        //监听所选中的日期
        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {

            public void onCalendarClick(int row, int col, String dateFormat) {
                int month = Integer.parseInt(dateFormat.substring(dateFormat.indexOf("-") + 1,
                        dateFormat.lastIndexOf("-")));

                if (calendar.getCalendarMonth() - month == 1//跨年跳转
                        || calendar.getCalendarMonth() - month == -11) {
                    calendar.lastMonth();

                } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
                        || month - calendar.getCalendarMonth() == -11) {
                    calendar.nextMonth();

                } else {
                    calendar.removeAllBgColor();
                    calendar.setCalendarDayBgColor(dateFormat,
                            R.drawable.calendar_date_focused);
                    date = dateFormat;//最后返回给全局 date
                    Log.i("infodate", date);

                }
            }
        });

        //监听当前月份
        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
            public void onCalendarDateChanged(int year, int month) {
                popupwindow_calendar_month.setText(year + "年" + month + "月");
            }
        });

        //上月监听按钮
        RelativeLayout popupwindow_calendar_last_month = (RelativeLayout) view.findViewById(R.id.popupwindow_calendar_last_month);
        popupwindow_calendar_last_month.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                calendar.lastMonth();
            }

        });

        //下月监听按钮
        RelativeLayout popupwindow_calendar_next_month = (RelativeLayout) view.findViewById(R.id.popupwindow_calendar_next_month);
        popupwindow_calendar_next_month.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                calendar.nextMonth();
            }
        });

        //关闭窗口
        popupwindow_calendar_bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_date = DateTimeUtil.GetNowDate();
                if (date != null) {
                    //获取当前显示的时间与日历中时间之间  周数的差
                    try {
                        int day_duration = DateTimeUtil.daysBetween(current_date, date);
                        if (DateTimeUtil.GetNowDate() == date) {
                            mListener.onSelect(0,date);
                            defalut = 0;
                            dismiss();
                        }
                        if (day_duration > 0) {
                            Toast.makeText(context,
                                    "所选日期需小于或等于当前日期(" + DateTimeUtil.GetNowDate() + ")",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            //当天日期为今年第几周
                            int current_week = DateTimeUtil.getWeekFromYear(current_date);
                            //选择的日期为今年第几周
                            int select_week = DateTimeUtil.getWeekFromYear(date);
                            //当天的日期与选择的日期的相差的周数
                            int currentWeekDiff = select_week - current_week;
                            //上一次选择的相差的周数 与 当前选择的相差的周数
                            int dateDiff = Math.abs(currentWeekDiff) - Math.abs(defalut);

                            if (currentWeekDiff == 0) {
                                mListener.onSelect(currentWeekDiff,date);
                            } else if (currentWeekDiff > defalut) {
                                mListener.onSelect(defalut + dateDiff,date);
                            } else {
                                mListener.onSelect(defalut - dateDiff,date);
                            }
                            //将当前的周数差赋值
                            defalut = select_week - current_week;
                            dismiss();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    dismiss();
                }
            }
        });
    }
    
   public interface OnSelectListener{
        void onSelect(int num, String dateStr);
    }
}