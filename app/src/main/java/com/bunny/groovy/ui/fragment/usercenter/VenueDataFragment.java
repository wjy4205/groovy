package com.bunny.groovy.ui.fragment.usercenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseFragment;
import com.bunny.groovy.base.FragmentContainerActivity;
import com.bunny.groovy.listener.PermissionListener;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.model.StyleModel;
import com.bunny.groovy.presenter.VenueMePresenter;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.IMeView;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.bunny.groovy.utils.AppConstants.REQUESTCODE_SELECT_PIC;

/**
 * 演播厅编辑设置页面
 * <p>
 * Created by Administrator on 2017/12/24.
 */

public class VenueDataFragment extends BaseFragment<VenueMePresenter> implements IMeView {

    @Bind(R.id.personal_headview)
    CircleImageView mHeadView;

    @Bind(R.id.personal_tv_name)
    TextView mTvName;

    @Bind(R.id.personal_tv_address)
    TextView mTvAddress;

    @Bind(R.id.personal_tv_phone)
    TextView mTvPhone;

    @Bind(R.id.personal_et_phone)
    EditText mEtPhone;

    @Bind(R.id.personal_et_website)
    EditText mEtWebsite;

    @Bind(R.id.personal_et_facebook)
    EditText mEtFaceBook;

    @Bind(R.id.personal_et_twitter)
    EditText mEtTwitter;

    @Bind(R.id.service_choose_1)
    CheckBox mCb1;
    @Bind(R.id.service_choose_2)
    CheckBox mCb2;
    @Bind(R.id.service_choose_3)
    CheckBox mCb3;

    private String headImagePath, mServiceName;

    @OnClick(R.id.personal_iv_select_pic)
    public void selectHeaderView() {
        mActivity.requestRuntimePermission(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //选择图片
                Intent intent = new Intent(mActivity, ImagesSelectorActivity.class);
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 1);
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                startActivityForResult(intent, REQUESTCODE_SELECT_PIC);
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                //拒绝
                UIUtils.showBaseToast("Select picture denied.");
            }
        });
    }

    public static void launch(Activity from) {
        Bundle bundle = new Bundle();
        bundle.putString(FragmentContainerActivity.FRAGMENT_TITLE, "PERSONAL DATA");
        FragmentContainerActivity.launch(from, VenueDataFragment.class, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.personal_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.personal_item_save) {
            //判断空，拦截
            //获取演播厅类型
            StringBuilder stringBuilder = new StringBuilder();
            if (mCb1.isChecked()) {
                stringBuilder.append(mCb1.getText().toString().trim());
            }
            if (mCb2.isChecked()) {
                if (stringBuilder.length() > 0) stringBuilder.append(",");
                stringBuilder.append(mCb2.getText().toString().trim());
            }
            if (mCb3.isChecked()) {
                if (stringBuilder.length() > 0) stringBuilder.append(",");
                stringBuilder.append(mCb3.getText().toString().trim());
            }
            mServiceName = stringBuilder.toString();
            if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                UIUtils.showBaseToast("Please input phone.");
//            } else if (TextUtils.isEmpty(mServiceName)) {
//                UIUtils.showBaseToast("Please select venue service.");
            } else if (TextUtils.isEmpty(mEtWebsite.getText().toString())) {
                UIUtils.showBaseToast("Please input website.");
            } else if (TextUtils.isEmpty(mEtFaceBook.getText().toString())) {
                UIUtils.showBaseToast("Please input facebook.");
            } else if (TextUtils.isEmpty(mEtTwitter.getText().toString())) {
                UIUtils.showBaseToast("Please input twitter.");
            } else {
                HashMap<String, String> ma = new HashMap<>();
                ma.put("phoneNumber", mEtPhone.getText().toString());
                ma.put("venueTypeName", mServiceName);
                ma.put("webSiteAddress", mEtWebsite.getText().toString());
                ma.put("twitterAccount", mEtTwitter.getText().toString());
                ma.put("facebookAccount", mEtFaceBook.getText().toString());
                //头像
                if (!TextUtils.isEmpty(headImagePath))
                    ma.put("imgfile", headImagePath);
                mPresenter.updateVenueData(ma);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Activity get() {
        return mActivity;
    }

    @Override
    public void setUserView(PerformerUserModel model) {
        mTvName.setText(model.getUserName());
        mTvAddress.setText(model.getVenueAddress());
        mTvPhone.setText(model.getTelephone());
        Glide.with(getActivity()).load(model.getHeadImg())
                .placeholder(R.drawable.venue_default_photo).error(R.drawable.venue_default_photo).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                mHeadView.setImageDrawable(resource.getCurrent());
            }
        });
        mServiceName = model.getVenueTypeName();
        if (!TextUtils.isEmpty(mServiceName)) {
            String data[] = mServiceName.split(",");
            for (String v : data) {
                if (TextUtils.equals(mCb1.getText().toString(), v)) {
                    mCb1.setChecked(true);
                } else if (TextUtils.equals(mCb2.getText().toString(), v)) {
                    mCb2.setChecked(true);
                } else if (TextUtils.equals(mCb3.getText().toString(), v)) {
                    mCb3.setChecked(true);
                }
            }
        }
        mEtPhone.setText(model.getPhoneNumber());
        mEtWebsite.setText(model.getWebSiteAddress());
        mEtFaceBook.setText(model.getFacebookAccount());
        mEtTwitter.setText(model.getTwitterAccount());
    }

    @Override
    public void showStylePop(List<StyleModel> modelList) {

    }

    @Override
    protected VenueMePresenter createPresenter() {
        return new VenueMePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_venue_personal_data_layout;
    }

    @Override
    protected void loadData() {
        mPresenter.requestUserData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUESTCODE_SELECT_PIC && resultCode == RESULT_OK) {
            List<String> mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;
            headImagePath = mResults.get(0);
            Bitmap bitmap = BitmapFactory.decodeFile(headImagePath);
            mHeadView.setImageBitmap(bitmap);
        }
    }
}
