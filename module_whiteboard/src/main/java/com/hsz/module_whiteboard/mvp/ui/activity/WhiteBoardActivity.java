package com.hsz.module_whiteboard.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hsz.module_whiteboard.R;
import com.hsz.module_whiteboard.R2;
import com.hsz.module_whiteboard.app.widget.CellItemView;
import com.hsz.module_whiteboard.di.component.DaggerWhiteBoardComponent;
import com.hsz.module_whiteboard.di.module.WhiteBoardModule;
import com.hsz.module_whiteboard.mvp.contract.WhiteBoardContract;
import com.hsz.module_whiteboard.mvp.presenter.WhiteBoardPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.config.Constants;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.entity.WhiteBoardBean;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.FileUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


@Route(path = "/whiteboard/whiteboardhome")
public class WhiteBoardActivity extends BaseActivity<WhiteBoardPresenter> implements WhiteBoardContract.View {

    @BindView(R2.id.board_civ_pastPeo)
    CellItemView boardCivPastPeo;
    @BindView(R2.id.board_civ_nowPeo)
    CellItemView boardCivNowPeo;
    @BindView(R2.id.board_civ_firstLevel)
    CellItemView boardCivFirstLevel;
    @BindView(R2.id.board_civ_secondLevel)
    CellItemView boardCivSecondLevel;
    @BindView(R2.id.board_civ_getinPeo)
    CellItemView boardCivGetinPeo;
    @BindView(R2.id.board_civ_outPeo)
    CellItemView boardCivOutPeo;
    @BindView(R2.id.board_civ_dayDoc)
    CellItemView boardCivDayDoc;
    @BindView(R2.id.board_civ_nightDoc)
    CellItemView boardCivNightDoc;
    @BindView(R2.id.board_civ_sickCritical)
    CellItemView boardCivSickCritical;
    @BindView(R2.id.board_civ_sickIll)
    CellItemView boardCivSickIll;
    @BindView(R2.id.board_civ_nNurse)
    CellItemView boardCivNNurse;
    @BindView(R2.id.board_civ_pNurse)
    CellItemView boardCivPNurse;
    @BindView(R2.id.board_et_todayInPeo)
    EditText boardEtTodayInPeo;
    @BindView(R2.id.board_et_todayOutPeo)
    EditText boardEtTodayOutPeo;
    @BindView(R2.id.board_et_reservationOut)
    EditText boardEtReservationOut;
    @BindView(R2.id.view_cell_title_tv)
    TextView viewCellTitleTv;
    @BindView(R2.id.board_et_turnBed)
    EditText boardEtTurnBed;
    @BindView(R2.id.board_civ_breathOxy)
    CellItemView boardCivBreathOxy;
    @BindView(R2.id.board_civ_injectPump)
    CellItemView boardCivInjectPump;
    @BindView(R2.id.board_civ_pluseMonitor)
    CellItemView boardCivPluseMonitor;
    @BindView(R2.id.board_civ_glucMonitor)
    CellItemView boardCivGlucMonitor;
    @BindView(R2.id.board_civ_inoutAmout)
    CellItemView boardCivInoutAmout;
    @BindView(R2.id.board_civ_mdr)
    CellItemView boardCivMdr;
    @BindView(R2.id.board_civ_mouthCare)
    CellItemView boardCivMouthCare;
    @BindView(R2.id.board_civ_prenaeumCare)
    CellItemView boardCivPrenaeumCare;
    @BindView(R2.id.board_civ_ulcerCare)
    CellItemView boardCivUlcerCare;
    @BindView(R2.id.board_civ_tracheaCare)
    CellItemView boardCivTracheaCare;
    @BindView(R2.id.board_civ_fallRisk)
    CellItemView boardCivFallRisk;
    @BindView(R2.id.board_civ_ulcerRisk)
    CellItemView boardCivUlcerRisk;
    @BindView(R2.id.board_civ_changeDrag)
    CellItemView boardCivChangeDrag;
    @BindView(R2.id.board_civ_intervalUrine)
    CellItemView boardCivIntervalUrine;
    @BindView(R2.id.board_civ_lienUrine)
    CellItemView boardCivLienUrine;
    @BindView(R2.id.board_civ_lienStomach)
    CellItemView boardCivLienStomach;
    @BindView(R2.id.board_civ_cta)
    CellItemView boardCivCta;
    @BindView(R2.id.board_civ_dsa)
    CellItemView boardCivDsa;
    @BindView(R2.id.board_civ_gastroscope)
    CellItemView boardCivGastroscope;
    @BindView(R2.id.board_civ_gutscope)
    CellItemView boardCivGutscope;
    @BindView(R2.id.board_civ_ivp)
    CellItemView boardCivIvp;
    @BindView(R2.id.board_civ_mri)
    CellItemView boardCivMri;
    @BindView(R2.id.board_civ_gtt)
    CellItemView boardCivGtt;
    @BindView(R2.id.board_civ_urineGather)
    CellItemView boardCivUrineGather;
    @BindView(R2.id.board_et_remark)
    EditText boardEtRemark;

    @BindView(R2.id.act_whiteboard_back_tv)
    TextView actWhiteboardBackTv;
    @BindView(R2.id.act_whiteboard_submit_tv)
    TextView actWhiteboardSubmitTv;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWhiteBoardComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .whiteBoardModule(new WhiteBoardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_white_board; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getLocalCacheData();
    }

    private void getLocalCacheData() {
        WhiteBoardBean whiteBoardBean = (WhiteBoardBean) FileUtils.getSerialzeObj(Constants.CARE_INFO_FILENAME);
        if (whiteBoardBean != null) {
            boardCivPastPeo.setContent(whiteBoardBean.pastPeople);
            boardCivNowPeo.setContent(whiteBoardBean.nowPeople);
            boardCivFirstLevel.setContent(whiteBoardBean.firstLevel);
            boardCivSecondLevel.setContent(whiteBoardBean.secondLevel);
            boardCivGetinPeo.setContent(whiteBoardBean.getInPeople);
            boardCivOutPeo.setContent(whiteBoardBean.outPeople);
            boardCivDayDoc.setContent(whiteBoardBean.dayDoc);
            boardCivNightDoc.setContent(whiteBoardBean.nightDoc);
            boardCivSickCritical.setContent(whiteBoardBean.sickCritical);
            boardCivSickIll.setContent(whiteBoardBean.sickIll);
            boardCivNNurse.setContent(whiteBoardBean.nNurse);
            boardCivPNurse.setContent(whiteBoardBean.pNurse);
            boardEtTodayInPeo.setText(whiteBoardBean.todayInPeo);
            boardEtTodayOutPeo.setText(whiteBoardBean.todayOutPeo);
            boardEtReservationOut.setText(whiteBoardBean.reservationOutPeo);
            boardEtTurnBed.setText(whiteBoardBean.turnBed);
            boardCivBreathOxy.setContent(whiteBoardBean.breathOxy);
            boardCivInjectPump.setContent(whiteBoardBean.injectPump);
            boardCivPluseMonitor.setContent(whiteBoardBean.pluseMonitor);
            boardCivGlucMonitor.setContent(whiteBoardBean.glucMonitor);
            boardCivInoutAmout.setContent(whiteBoardBean.inoutAmout);
            boardCivMdr.setContent(whiteBoardBean.mdr);
            boardCivMouthCare.setContent(whiteBoardBean.mouthCare);
            boardCivPrenaeumCare.setContent(whiteBoardBean.perinaeumCare);
            boardCivUlcerCare.setContent(whiteBoardBean.pressureUlcerCare);
            boardCivTracheaCare.setContent(whiteBoardBean.tracheaCare);
            boardCivFallRisk.setContent(whiteBoardBean.fallRisk);
            boardCivUlcerRisk.setContent(whiteBoardBean.ulcerRisk);
            boardCivChangeDrag.setContent(whiteBoardBean.changeDrag);
            boardCivIntervalUrine.setContent(whiteBoardBean.intervalUrine);
            boardCivLienUrine.setContent(whiteBoardBean.lienUrine);
            boardCivLienStomach.setContent(whiteBoardBean.lienStomach);
            boardCivCta.setContent(whiteBoardBean.cta);
            boardCivDsa.setContent(whiteBoardBean.dsa);
            boardCivGastroscope.setContent(whiteBoardBean.gastroscope);
            boardCivGutscope.setContent(whiteBoardBean.gutscope);
            boardCivIvp.setContent(whiteBoardBean.ivp);
            boardCivMri.setContent(whiteBoardBean.mri);
            boardCivGtt.setContent(whiteBoardBean.gtt);
            boardCivLienUrine.setContent(whiteBoardBean.urineGather);
            boardEtRemark.setText(whiteBoardBean.remark);
        }
    }

    @Override
    protected void onDestroy() {
        saveWhiteboardData();
        super.onDestroy();
    }

    private void saveWhiteboardData() {
        WhiteBoardBean whiteBoardBean = new WhiteBoardBean();
        whiteBoardBean.pastPeople = boardCivPastPeo.getContent();
        whiteBoardBean.nowPeople = boardCivNowPeo.getContent();
        whiteBoardBean.firstLevel = boardCivFirstLevel.getContent();
        whiteBoardBean.secondLevel = boardCivSecondLevel.getContent();
        whiteBoardBean.getInPeople = boardCivGetinPeo.getContent();
        whiteBoardBean.outPeople = boardCivOutPeo.getContent();
        whiteBoardBean.dayDoc = boardCivDayDoc.getContent();
        whiteBoardBean.nightDoc = boardCivNightDoc.getContent();
        whiteBoardBean.sickCritical = boardCivSickCritical.getContent();
        whiteBoardBean.sickIll = boardCivSickIll.getContent();
        whiteBoardBean.nNurse = boardCivNNurse.getContent();
        whiteBoardBean.pNurse = boardCivPNurse.getContent();
        whiteBoardBean.todayInPeo = boardEtTodayInPeo.getText().toString().trim();
        whiteBoardBean.todayOutPeo = boardEtTodayOutPeo.getText().toString().trim();
        whiteBoardBean.reservationOutPeo = boardEtReservationOut.getText().toString().trim();
        whiteBoardBean.turnBed = boardEtTurnBed.getText().toString().trim();
        whiteBoardBean.breathOxy = boardCivBreathOxy.getContent();
        whiteBoardBean.injectPump = boardCivInjectPump.getContent();
        whiteBoardBean.pluseMonitor = boardCivPluseMonitor.getContent();
        whiteBoardBean.glucMonitor = boardCivGlucMonitor.getContent();
        whiteBoardBean.inoutAmout = boardCivInoutAmout.getContent();
        whiteBoardBean.mdr = boardCivMdr.getContent();
        whiteBoardBean.mouthCare = boardCivMouthCare.getContent();
        whiteBoardBean.perinaeumCare = boardCivPrenaeumCare.getContent();
        whiteBoardBean.pressureUlcerCare = boardCivUlcerCare.getContent();
        whiteBoardBean.tracheaCare = boardCivTracheaCare.getContent();
        whiteBoardBean.fallRisk = boardCivFallRisk.getContent();
        whiteBoardBean.ulcerRisk = boardCivUlcerRisk.getContent();
        whiteBoardBean.changeDrag = boardCivChangeDrag.getContent();
        whiteBoardBean.intervalUrine = boardCivIntervalUrine.getContent();
        whiteBoardBean.lienUrine = boardCivLienUrine.getContent();
        whiteBoardBean.lienStomach = boardCivLienStomach.getContent();
        whiteBoardBean.cta = boardCivCta.getContent();
        whiteBoardBean.dsa = boardCivDsa.getContent();
        whiteBoardBean.gastroscope = boardCivGastroscope.getContent();
        whiteBoardBean.gutscope = boardCivGutscope.getContent();
        whiteBoardBean.ivp = boardCivIvp.getContent();
        whiteBoardBean.mri = boardCivMri.getContent();
        whiteBoardBean.gtt = boardCivGtt.getContent();
        whiteBoardBean.urineGather = boardCivLienUrine.getContent();
        whiteBoardBean.remark = boardEtRemark.getText().toString().trim();

        FileUtils.serialzeObj2Local(whiteBoardBean, Constants.CARE_INFO_FILENAME);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @OnClick({R2.id.act_whiteboard_back_tv, R2.id.act_whiteboard_submit_tv})
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.act_whiteboard_back_tv) {
            finish();
        } else if (viewId == R.id.act_whiteboard_submit_tv) {
            finish();
        }
    }
}
