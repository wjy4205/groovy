package com.bunny.groovy.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bunny.groovy.BuildConfig;
import com.bunny.groovy.R;
import com.bunny.groovy.base.BaseActivity;
import com.bunny.groovy.listener.PermissionListener;
import com.bunny.groovy.model.PerformerUserModel;
import com.bunny.groovy.presenter.SingUpPresenter;
import com.bunny.groovy.utils.AppCacheData;
import com.bunny.groovy.utils.AppConstants;
import com.bunny.groovy.utils.UIUtils;
import com.bunny.groovy.view.ISingUpView;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.xw.repo.XEditText;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mysty on 2018/2/26.
 */

public class VenueFile1Activity extends BaseActivity<SingUpPresenter> implements ISingUpView {

    @Bind(R.id.venue_info_website)
    XEditText mWebsite;
    @Bind(R.id.venue_info_head_pic)
    CircleImageView mHeadView;
    @Bind(R.id.venue_info_service)
    XEditText mVenueService;
    @Bind(R.id.venue_info_phone)
    XEditText mVenuePhone;
    @Bind(R.id.venue_info_website_address)
    XEditText mVenueAddress;
    @Bind(R.id.venue_info_booking_email)
    XEditText mVenueEmail;
    @Bind(R.id.venue_info_booking_phone)
    XEditText mVenueTelPhone;
    private String mHeadUrl;
    private PopupWindow popupWindow;
    private RadioGroup mRadioGroup;
    private final static int PLACE_PICKER_REQUEST = 1;
    private String mLongitude, mLatitude, mPlaceId, mPlaceName;

    @Override
    protected SingUpPresenter createPresenter() {
        return new SingUpPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_venue_info_fill_first;
    }

    @OnClick(R.id.venue_info_head_pic)
    void selectPic() {
        requestRuntimePermission(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //选择图片
                choosePic(VenueFile1Activity.this);
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                //拒绝
                UIUtils.showBaseToast("Select picture denied.");
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        PerformerUserModel model = AppCacheData.getPerformerUserModel();
        if (model != null) {
            if (!TextUtils.isEmpty(model.getTelephone())) {
                mVenueTelPhone.setText(model.getTelephone());
                mVenueTelPhone.setEnabled(false);
            } else if (!TextUtils.isEmpty(model.getUserEmail())) {
                mVenueEmail.setText(model.getUserEmail());
                mVenueEmail.setEnabled(false);
            }
        }
    }

    @OnClick(R.id.venue_info_service)
    void chooseServiceType() {
        UIUtils.hideSoftInput(mVenueService);
        showPopWindow(this, mVenueService);
    }

    @OnClick(R.id.venue_info_website_address)
    void chooseAddress() {
        UIUtils.hideSoftInput(mVenueAddress);
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
            UIUtils.showBaseToast("GooglePlay services not available!");
        }
        if (BuildConfig.DEBUG) {
            mVenueAddress.setText("中国上海市浦东新区");
            mLongitude = "121.6000";
            mLatitude = "31.2200";
            mPlaceId = "ChIJG9rV8LJ3sjURgCSirgwJAGM";
            mPlaceName = mVenueAddress.getText().toString().trim();
        }
    }

    @Override
    public void showCheckResult(boolean invalid, int accountType, String msg) {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public Activity get() {
        return getCurrentActivity();
    }

    @Override
    public void registerSuccess() {

    }


    @OnClick(R.id.perfect_info_tv_next)
    void next() {
        //拦截
        if (TextUtils.isEmpty(mHeadUrl)) {
            UIUtils.showBaseToast("Please select head image.");
        } else if (TextUtils.isEmpty(mVenueTelPhone.getTrimmedString())) {
            UIUtils.showBaseToast("Please input venue telephone.");
        } else if (TextUtils.isEmpty(mVenueEmail.getTrimmedString())) {
            UIUtils.showBaseToast("Please input venue email.");
//        } else if (TextUtils.isEmpty(mVenueService.getTrimmedString())) {
//            UIUtils.showBaseToast("Please select venue service.");
        } else if (TextUtils.isEmpty(mVenuePhone.getTrimmedString())) {
            UIUtils.showBaseToast("Please input venue phone.");
        } else if (TextUtils.isEmpty(mVenueAddress.getTrimmedString())) {
            UIUtils.showBaseToast("Please input address.");
        } else {
            //保存数据
            AppCacheData.getFileMap().put("telephone", mVenueTelPhone.getTrimmedString());
            AppCacheData.getFileMap().put("userEmail", mVenueEmail.getTrimmedString());
            AppCacheData.getFileMap().put("phoneNumber", mVenuePhone.getTrimmedString());
            String service1 = mVenueService.getTrimmedString().replace("Shows are not 21+ only","Exclude 21+");
            AppCacheData.getFileMap().put("venueTypeName", service1);
            AppCacheData.getFileMap().put("longitude", mLongitude);
            AppCacheData.getFileMap().put("latitude", mLatitude);
            AppCacheData.getFileMap().put("placeID", mPlaceId);
            AppCacheData.getFileMap().put("venueAddress", mVenueAddress.getTrimmedString());
            AppCacheData.getFileMap().put("userName", mPlaceName);
            AppCacheData.getFileMap().put("userID", AppCacheData.getPerformerUserModel().getUserID());
            if (!TextUtils.isEmpty(mWebsite.getTrimmedString())) {
                AppCacheData.getFileMap().put("webSiteAddress", mWebsite.getTrimmedString());
            }
            AppCacheData.getFileMap().put("imgfile", mHeadUrl);
            startActivityForResult(new Intent(this, VenueFile2Activity.class), 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.REQUESTCODE_SELECT_PIC && resultCode == RESULT_OK) {
            List<String> mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
            assert mResults != null;
            mHeadUrl = mResults.get(0);
            Bitmap bitmap = BitmapFactory.decodeFile(mHeadUrl);
            mHeadView.setImageBitmap(bitmap);
        } else if (requestCode == 2 && resultCode == AppConstants.ACTIVITY_FINISH) {
            setResult(AppConstants.ACTIVITY_FINISH);
            finish();
        } else if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                mPlaceName = place.getName().toString();
                mVenueAddress.setText(place.getAddress());
                mLongitude = String.valueOf(place.getLatLng().longitude);
                mLatitude = String.valueOf(place.getLatLng().latitude);
                mPlaceId = String.valueOf(place.getId());
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initPopWindow(Context context, String value) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(context);
            View inflate = LayoutInflater.from(context).inflate(R.layout.pop_venue_service_choose_layout, null, false);
            final CheckBox checkBox1 = inflate.findViewById(R.id.service_choose_1);
            final CheckBox checkBox2 = inflate.findViewById(R.id.service_choose_2);
            final CheckBox checkBox3 = inflate.findViewById(R.id.service_choose_3);
            if (!TextUtils.isEmpty(value)) {
                String data[] = value.split(",");
                for (String v : data) {
                    if (TextUtils.equals("Exclude 21+", v)) {
                        checkBox1.setChecked(true);
                    } else if (TextUtils.equals(checkBox2.getText().toString(), v)) {
                        checkBox2.setChecked(true);
                    } else if (TextUtils.equals(checkBox3.getText().toString(), v)) {
                        checkBox3.setChecked(true);
                    }
                }
            }
            popupWindow.setContentView(inflate);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setWidth(mVenueService.getWidth());
            popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (checkBox1.isChecked()) {
                        stringBuilder.append(checkBox1.getText().toString());
                    }
                    if (checkBox2.isChecked()) {
                        if (stringBuilder.length() > 0) stringBuilder.append(",");
                        stringBuilder.append(checkBox2.getText().toString().trim());
                    }
                    if (checkBox3.isChecked()) {
                        if (stringBuilder.length() > 0) stringBuilder.append(",");
                        stringBuilder.append(checkBox3.getText().toString().trim());
                    }
                    mVenueService.setText(stringBuilder.toString());
                    mVenueService.setCheckStatus(XEditText.CheckStatus.NONE);
                }
            });
        }
    }

    public void showPopWindow(Context context, View view) {
        UIUtils.hideSoftInput(mVenueService);
        initPopWindow(context, mVenueService.getTrimmedString());
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            mVenueService.setCheckStatus(XEditText.CheckStatus.NONE);
        } else {
            popupWindow.showAsDropDown(mVenueService, 0, 0);
            mVenueService.setCheckStatus(XEditText.CheckStatus.INVALID);
        }
    }
}
