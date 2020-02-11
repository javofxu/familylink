package com.ilop.sthome.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.common.base.BaseActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.siterwell.familywellplus.R;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author skygge
 * @date 2019-11-27.
 * GitHub：javofxu@github.com
 * email：skygge@yeah.net
 * description：
 */
public class QRCodeActivity extends BaseActivity {
    private DecoratedBarcodeView barcodeView;
    private boolean isLight = false;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initialize() {
        super.initialize();
        immersionStatusBar(false);
    }

    @Override
    protected void initView() {
        super.initView();
        initViews();
    }

    private void initViews() {
        barcodeView = findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.decodeContinuous(callback);
        /*barcodeView.setTorchListener(new DecoratedBarcodeView.TorchListener() {
            @Override
            public void onTorchOn() {
                Toast.makeText(QRCodeActivity.this, "onTorchOn", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTorchOff() {
                Toast.makeText(QRCodeActivity.this, "onTorchOff", Toast.LENGTH_SHORT).show();
            }
        });*/
        ImageView light = findViewById(R.id.iv_open_light);
        light.setOnClickListener(view -> {
            if (!isLight) {
                barcodeView.setTorchOn();
                light.setImageResource(R.mipmap.icon_open);
                isLight = true;
            } else {
                isLight = false;
                barcodeView.setTorchOff();
                light.setImageResource(R.mipmap.icon_close);
            }
        });
        findViewById(R.id.iv_scan_back).setOnClickListener(view -> finish());
    }


    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            // Prevent duplicate scans
            if (result.getText() == null) return;
            barcodeView.setStatusText(result.getText());
            parseQRCode(result.getText(), new Bundle());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    //解析二维码中的字符串
    private void parseQRCode(String result, Bundle bundle) {

        if (TextUtils.isEmpty(result)) {
            Toast.makeText(this, "照片中未识别到二维码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (result != null) {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            Intent resIntent = new Intent();
            resIntent.putExtra("SN", result);
            setResult(RESULT_OK, resIntent);
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}
