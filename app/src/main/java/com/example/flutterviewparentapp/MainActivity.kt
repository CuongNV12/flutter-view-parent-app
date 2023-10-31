package com.example.flutterviewparentapp

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        const val TAG_FLUTTER_ENGINE = "kvc_onboarding_cached_engine"
        private const val TAG_FLUTTER_FRAGMENT = "onboarding_sdk_fragment"
        private const val ONBOARDING_CHANNEL = "onboarding_sdk_flutter_channel"
        private const val ONBOARDING_EVENT_CHANNEL = "onboarding_sdk_flutter_channel/event"
    }

    private var flutterFragment: FlutterFragment? = null
    private var onboardingFrame: FrameLayout? = null
    private var channel: MethodChannel? = null
    private var eventChannel: EventChannel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFlutterFragment()
        initMethodChanel()
        initEventChanel()
    }

    private fun createFlutterFragment() {
        onboardingFrame = findViewById(R.id.onboarding_sdk)
        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager: FragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment, in case this is not the
        // first time that onCreate() was run.
        flutterFragment =
            fragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment == null) {
            val newFlutterFragment =
//                FlutterFragment.withNewEngine().dartEntrypoint("mainKVS").dartEntrypointArgs(
//                    listOf(
//                        "dev",
//                        "vi",
//                        "sdk_onboarding_to_retail",
//                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6InNCeCJ9.eyJpc3MiOiJrdnNzand0Iiwic3ViIjoyNzE4LCJpYXQiOjE2OTgyMjA5MzgsImV4cCI6MTY5OTQzMDUzOCwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJyb2xlcyI6WyJVc2VyIl0sImt2c291cmNlIjoiUmV0YWlsIiwia3Z1c2V0ZmEiOjAsImt2d2FpdG90cCI6MCwia3ZzZXMiOiJmYjk1MWQ3MDRkMDE0MGM0OGRmMmQ5NDcwYTU1ZDYyZCIsImt2dWlkIjoyNzE4LCJrdnV0eXBlIjowLCJrdnVsaW1pdCI6IkZhbHNlIiwia3Z1YWRtaW4iOiJUcnVlIiwia3Z1YWN0IjoiVHJ1ZSIsImt2dWxpbWl0dHJhbnMiOiJGYWxzZSIsImt2dXNob3dzdW0iOiJUcnVlIiwia3ZiaSI6IlRydWUiLCJrdmN0eXBlIjozLCJ1c2VCSSI6eyJDdXN0b21lckJJUmVwb3J0X1JlYWQiOltdLCJTYWxlQklSZXBvcnRfUmVhZCI6W10sIlByb2R1Y3RCSVJlcG9ydF9SZWFkIjpbXSwiRmluYW5jZUJJUmVwb3J0X1JlYWQiOltdfSwia3ZiaWQiOjI3MjIsImt2cmluZGlkIjozLCJrdnJjb2RlIjoiaG9hMjEwMCIsImt2cmlkIjo0MjMyLCJrdnVyaWQiOjQyMzIsImt2cmdpZCI6MSwicGVybXMiOiIifQ.mBcfjyYOY87jWaGQRiLcMLYNswSUcwDInKzaYuUgLcXT2Or6ZUVtjOb3CFNMAstNWEFFrs9G11YKhEJNYltv9zje8lRPGU6srWtepkKBLfGMUnkexMFjZkkayIj_NorunRU8uyzpslZvt0ALKp_kRCHWNl98gQrBL5sfGbPJJjyEj-Llmacjm5LVJZnGYA7-5Z8iPl0QK9lbiwCgVS4F-oyS4_IYPkY805INTs74dTRc_DkBIQNcAjqjxizvQtc5XGBh7tkvE8cPIo_jsh2T0T9joBPkMtNaaCjRT7YxwWBvw_ZdCguhoqZCANu2fiLAcOQjXsoE9x5U8eWEXP9bPw",
//                        "tooltip"
//                    )
//                ).build<FlutterFragment>()
                FlutterFragment.withCachedEngine(TAG_FLUTTER_ENGINE)
                    .build<FlutterFragment>()
            flutterFragment = newFlutterFragment
            fragmentManager.beginTransaction().add(
                R.id.onboarding_sdk, newFlutterFragment, TAG_FLUTTER_FRAGMENT
            ).commit()
        }
    }

    private fun initMethodChanel() {
        channel = FlutterEngineCache.getInstance()
            .get(TAG_FLUTTER_ENGINE)?.dartExecutor?.binaryMessenger?.let {
                MethodChannel(
                    it, ONBOARDING_CHANNEL
                )
            }

        channel?.setMethodCallHandler { call, result ->
            if (call.method == "sdk_onboarding_kvc_flutter_set_tooltip_height") {
                val params = onboardingFrame?.layoutParams
                val heightPxValue = (call.arguments as Double)
                params?.height = convertFlutterPxToAndroidPx(heightPxValue)
                onboardingFrame?.layoutParams = params
//                        onboardingFrame?.visibility = View.GONE
                result.success("hahaha")
            } else {
                result.notImplemented()
            }
        }
//        channel?.invokeMethod("ABCD","xxx")
    }

    private fun convertFlutterPxToAndroidPx(logicalPxValue: Double): Int {
        // Lấy mật độ điểm ảnh (dpi) của thiết bị Android
        val dpi: Float = resources.displayMetrics.densityDpi.toFloat()

        // Chuyển đổi giá trị từ logical pixels (px) trong Flutter sang pixels (px) trong Android
        val androidPxValue: Double = logicalPxValue * (dpi / 160.0)

        // Chuyển đổi thành kiểu int và trả về
        return ceil(androidPxValue).toInt()
    }

    private fun initEventChanel() {
        eventChannel = EventChannel(
            FlutterEngineCache.getInstance()
                .get(TAG_FLUTTER_ENGINE)?.dartExecutor?.binaryMessenger,
            ONBOARDING_EVENT_CHANNEL
        )
        eventChannel?.setStreamHandler(OnboardingStreamHandler())
    }
}

class OnboardingStreamHandler : EventChannel.StreamHandler {
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Log.d("cuong.nv2", "onListen: ")
        events?.success("hahahaha")
    }

    override fun onCancel(arguments: Any?) {
        Log.d("cuong.nv2", "onCancel: ")
    }

}