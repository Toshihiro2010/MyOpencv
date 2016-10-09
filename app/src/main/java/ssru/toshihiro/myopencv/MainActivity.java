package ssru.toshihiro.myopencv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.opencv.android.CameraBridgeViewBase.*;

public class MainActivity extends AppCompatActivity implements CvCameraViewListener2 {

    private CameraBridgeViewBase myCamera;
    private Mat mRgba;


    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS :
                    myCamera.enableView(); //เปิดใช้กล้อง

                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        bindView();



    }

    private void bindView() {
        myCamera = (CameraBridgeViewBase) findViewById(R.id.my_camera);
        myCamera.setCvCameraViewListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, loaderCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (myCamera != null) {
            myCamera.disableView();
        }

    }



    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();


        return mRgba;
    }
}
