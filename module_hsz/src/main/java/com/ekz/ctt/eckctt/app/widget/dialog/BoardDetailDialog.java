package com.ekz.ctt.eckctt.app.widget.dialog;

import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.style.LineStyle;
import com.bin.david.form.listener.OnColumnClickListener;
import com.ekz.ctt.eckctt.R;
import com.ekz.ctt.eckctt.R2;
import com.ekz.ctt.eckctt.app.widget.MySmartTable;
import com.ekz.ctt.eckctt.mvp.model.entity.HuliTable;
import com.jess.arms.base.BaseDialogFragment;
import com.jess.arms.utils.UIUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/*
 *  @项目名：  EkzCT_qinghai
 *  @包名：    com.ekz.ctt.eckctt.app.widget.dialog
 *  @文件名:   DiagnoseDialog
 *  @创建者:   Administrator
 *  @创建时间:  2018/10/12 14:42
 *  @描述：    TODO
 */
public class BoardDetailDialog extends BaseDialogFragment {
    @BindView(R2.id.dialog_board_detail_smarttable)
    MySmartTable  dialogBoardDetailSmarttable;
    @BindView(R2.id.dialog_board_detail_close_btn)
    AutoRelativeLayout dialogBoardDetailCloseBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_board_detail;
    }


    /**
     *  list.add(new HuliTable("病人总数", "52", "心电监测", "05"));
     *         list.add(new HuliTable("入院", "01,02,03,04,05,06", "吸氧", "14,15,18,19,24,26,30,42"));
     *         list.add(new HuliTable("出院", "07", "雾化吸入", "02,04"));
     *         list.add(new HuliTable("转科", "08,09", "血糖监测", "02,04"));
     *         list.add(new HuliTable("转床", "11,22", "胰岛素", "01,03"));
     *         list.add(new HuliTable("今日手术", "12,13", "口腔护理", "05,06"));
     *         list.add(new HuliTable("明日手术", "01,04", "吸痰护理", "01,04"));
     *         list.add(new HuliTable("病重", "01,04", "灌肠", "01,04"));
     *         list.add(new HuliTable("病危", "01,04", "绝对卧床", "01,04"));
     *         list.add(new HuliTable("24小时尿量", "03,04", "翻身", "03,04"));
     *         list.add(new HuliTable("24小时测入量", "06,04", "气垫床", "07,04"));
     *         list.add(new HuliTable("一级护理", "07,04", "隔离", "07,04"));
     *         list.add(new HuliTable("二级护理", "08,04", "胃管", "08,04"));
     *         list.add(new HuliTable("生命体征检测q1h", "08,04", "导尿管", "08,04"));
     *         list.add(new HuliTable("生命体征检测q2h", "02,04", "引流管", "02,04"));
     *         list.add(new HuliTable("生命体征检测q4h", "09,04", "药物留置", "09,04"));
     *         list.add(new HuliTable("生命体征检测q8h", "09,04", "其他", "09,04"));
     *         list.add(new HuliTable("生命体征检测q12h", "02,04", "其他", "02,04"));
     *         list.add(new HuliTable("特殊", "", "其他", ""));
     *         list.add(new HuliTable("特殊", "", "其他", ""));
     *         list.add(new HuliTable("特殊", "", "其他", ""));
     *         list.add(new HuliTable("特殊", "", "其他", ""));
     */
    @Override
    protected void initData() {
        super.initData();
        dialogBoardDetailSmarttable.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {

            }
        });
        dialogBoardDetailSmarttable.setZoom(true);
//        dialogBoardDetailSmarttable.set
        List<HuliTable> list = new ArrayList<>();
        list.add(new HuliTable("病人总数", "52", "心电监测", "05"));
        list.add(new HuliTable("入院", "01,02,03,04,05,06", "吸氧", "14,15,18,19,24,26,30,42"));
        list.add(new HuliTable("出院", "", "雾化吸入", ""));
        list.add(new HuliTable("转科", "", "血糖监测", ""));
        list.add(new HuliTable("转床", "", "胰岛素", ""));
        list.add(new HuliTable("今日手术", "", "口腔护理", ""));
        list.add(new HuliTable("明日手术", "", "吸痰护理", ""));
        list.add(new HuliTable("病重", "", "灌肠", ""));
        list.add(new HuliTable("病危", "", "绝对卧床", ""));
        list.add(new HuliTable("24小时尿量", "", "翻身", ""));
        list.add(new HuliTable("24小时测入量", "", "气垫床", ""));
        list.add(new HuliTable("一级护理", "", "隔离", ""));
        list.add(new HuliTable("二级护理", "", "胃管", ""));
        list.add(new HuliTable("生命体征检测q1h", "", "导尿管", ""));
        list.add(new HuliTable("生命体征检测q2h", "", "引流管", ""));
        list.add(new HuliTable("生命体征检测q4h", "", "药物留置", ""));
        list.add(new HuliTable("生命体征检测q8h", "", "其他", ""));
        list.add(new HuliTable("生命体征检测q12h", "", "其他", ""));
        list.add(new HuliTable("特殊", "", "其他", ""));
        list.add(new HuliTable("特殊", "", "其他", ""));
        list.add(new HuliTable("特殊", "", "其他", ""));
        list.add(new HuliTable("特殊", "", "其他", ""));


        dialogBoardDetailSmarttable.setData(list);
        TableConfig config = dialogBoardDetailSmarttable.getConfig();
//        config.setMinTableWidth(1900);
        config.setShowColumnTitle(false);
        config.setShowTableTitle(false);
        config.setShowXSequence(false);
        config.setShowYSequence(false);
//        config.setZoom(1.5f);
        config.setContentGridStyle(new LineStyle(1, UIUtils.getColor(R.color.theme_red)));
        config.setContentStyle(new FontStyle(20, UIUtils.getColor(R.color.public_text_60)));
    }

    @OnClick(R2.id.dialog_board_detail_close_btn)
    public void onClick() {
        dismiss();
    }
}
