package wang.tinycoder.easylinkerapp.module.home.fragment.sys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import wang.tinycoder.easylinkerapp.R;
import wang.tinycoder.easylinkerapp.app.Constants;
import wang.tinycoder.easylinkerapp.base.BaseFragment;
import wang.tinycoder.easylinkerapp.bean.SystemState;
import wang.tinycoder.easylinkerapp.util.SpannableStringUtils;

/**
 * Progect：EasyLinkerAppNew
 * Package：wang.tinycoder.easylinkerapp.module.home.fragment.sys
 * Desc：系统状态
 * Author：TinycoderWang
 * CreateTime：2018/5/6 16:15
 */
public class SysStateFragment extends BaseFragment<SysStatePresenter> implements SysStateContract.View, LocationSource {


    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.tv_state)
    TextView mTvState;


    private AMap mAMap;
    //定义一个UiSettings对象
    private UiSettings mUiSettings;
    //定位需要的数据
    LocationSource.OnLocationChangedListener mListener;
    //声明AMapLocationClient类对象
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    //定位蓝点
    MyLocationStyle myLocationStyle;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @SuppressLint("CommitPrefEdits")
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
//                    //解析定位结果
//                    Logger.i("%s location : ", TAG, aMapLocation.toStr());
//                    //定位成功回调信息，设置相关消息
//                    int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    double latitude = aMapLocation.getLatitude();//获取纬度
//                    double longitude = aMapLocation.getLongitude();//获取经度
//                    float accuracy = aMapLocation.getAccuracy();//获取精度信息
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = new Date(aMapLocation.getTime());
//                    String dateStr = df.format(date);//定位时间
//                    String address = aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    String country = aMapLocation.getCountry();//国家信息
//                    String province = aMapLocation.getProvince();//省信息
//                    String city = aMapLocation.getCity();//城市信息
//                    String district = aMapLocation.getDistrict();//城区信息
//                    String street = aMapLocation.getStreet();//街道信息
//                    String streetNum = aMapLocation.getStreetNum();//街道门牌号信息
//                    String cityCode = aMapLocation.getCityCode();//城市编码
//                    String adCode = aMapLocation.getAdCode();//地区编码
//                    String aoiName = aMapLocation.getAoiName();//获取当前定位点的AOI信息

                    // 保存位置信息
                    String city = aMapLocation.getCity();//城市信息
                    double latitude = aMapLocation.getLatitude();//获取纬度
                    double longitude = aMapLocation.getLongitude();//获取经度
                    Logger.i("location : %s %f %f", city, latitude, longitude);
                    SharedPreferences sp = mActivity.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString(Constants.EXTRA_CITY_NAME, city);
                    edit.putString(Constants.EXTRA_CITY_LONGITUDE, numExchangeToStr("E", longitude));
                    edit.putString(Constants.EXTRA_CITY_LATITUDE, numExchangeToStr("N", latitude));
                    edit.commit();

                    // 地图显示当前位置
                    mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                    // 停止定位
                    mlocationClient.stopAssistantLocation();
                    mlocationClient.stopLocation();

                } else {
                    String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                    Log.e("AmapErr", errText);
                }
            }
        }

    };

    private String numExchangeToStr(String pre, double num) {

        StringBuilder sb = new StringBuilder(pre);
        // E116°23′29.52″
        int du = (int) num;
        sb.append(du).append("°");
        float fenFloat = ((float) num - du) * 60;
        int fen = (int) fenFloat;
        sb.append(fen).append("′");
        float miao = (fenFloat - fen) * 60;
        sb.append(miao).append("″");
        return sb.toString();
    }


    @Override
    protected int getlayoutId() {
        return R.layout.fragment_sys_state;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new SysStatePresenter(this, new SysStateModel());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        mAMap = mMapView.getMap();
        // 设置定位监听
        mAMap.setLocationSource(this);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置蓝点的样式
        setupLocationStyle();
        //设置地图的放缩级别
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(5));
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setCompassEnabled(false);
        mUiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示


//        MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.
//                fromResource(R.drawable.dev_location_icon))
//                .title("设备")
//                .snippet("总：10个\n在线：6个")
//                .position(new LatLng(39.9018, 116.3915))
//                .draggable(true);
//        mAMap.addMarker(markerOption);
//
//
//        MarkerOptions markerOption1 = new MarkerOptions().icon(BitmapDescriptorFactory.
//                fromResource(R.drawable.dev_location_icon))
//                .title("设备")
//                .snippet("总：10个\n在线：6个")
//                .position(new LatLng(37.9018, 106.3915))
//                .draggable(true);
//        mAMap.addMarker(markerOption1);

    }

    /**
     * 设置定位小蓝点样式
     */
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.location_point_icon));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(ContextCompat.getColor(mActivity, R.color.tab_blue));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(2);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(ContextCompat.getColor(mActivity, R.color.soft_blue));
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
//        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        // 将自定义的 myLocationStyle 对象添加到地图上
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        //mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        mTvState.setText(SpannableStringUtils.getBuilder("")
                .append("在线: ")
                .append("-").setForegroundColor(getResources().getColor(R.color.online)).setProportion(1.2f)
                .append(" 离线: ")
                .append("-").setForegroundColor(getResources().getColor(R.color.offline)).setProportion(1.2f)
                .append(" 总共: ")
                .append("-").setForegroundColor(getResources().getColor(R.color.text_color)).setProportion(1.2f)
                .create());

        // 获取当前的系统状态
        mPresenter.requestCurrentState();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
        if (null != mlocationClient) {
            mlocationClient.stopAssistantLocation();
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            //初始化定位
            mlocationClient = new AMapLocationClient(mActivity.getApplicationContext());
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(mLocationListener);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            mLocationOption.setOnceLocation(false);
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            mLocationOption.setInterval(1000*20);
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        if (null != mlocationClient) {
            mlocationClient.stopAssistantLocation();
            mlocationClient.onDestroy();
        }
    }

    @Override
    public void showState(SystemState state) {
        mTvState.setText(SpannableStringUtils.getBuilder("")
                .append("在线: ")
                .append(String.valueOf(state.getOnLine())).setForegroundColor(getResources().getColor(R.color.online)).setProportion(1.2f)
                .append("    离线: ")
                .append(String.valueOf(state.getOffLine())).setForegroundColor(getResources().getColor(R.color.offline)).setProportion(1.2f)
                .append("    总共: ")
                .append(String.valueOf(state.getTotal())).setForegroundColor(getResources().getColor(R.color.text_color)).setProportion(1.2f)
                .create());
    }

}
