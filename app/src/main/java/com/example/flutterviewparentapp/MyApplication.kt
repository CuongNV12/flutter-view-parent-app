package com.example.flutterviewparentapp

import android.app.Application
import com.example.flutterviewparentapp.MainActivity.Companion.TAG_FLUTTER_ENGINE
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor.DartEntrypoint


class MyApplication : Application() {
    private lateinit var flutterEngine: FlutterEngine
    override fun onCreate() {
        super.onCreate()
        // Instantiate a FlutterEngine.
        flutterEngine = FlutterEngine(this)
        // Start executing Dart code to pre-warm the FlutterEngine.
        DartEntrypoint.createDefault()
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartEntrypoint("", "mainKVS"),
            listOf(
                "dev",
                "vi",
                "sdk_onboarding_to_retail",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6InNCeCJ9.eyJpc3MiOiJrdnNzand0Iiwic3ViIjoyNzE4LCJpYXQiOjE2OTgyMjA5MzgsImV4cCI6MTY5OTQzMDUzOCwicHJlZmVycmVkX3VzZXJuYW1lIjoiYWRtaW4iLCJyb2xlcyI6WyJVc2VyIl0sImt2c291cmNlIjoiUmV0YWlsIiwia3Z1c2V0ZmEiOjAsImt2d2FpdG90cCI6MCwia3ZzZXMiOiJmYjk1MWQ3MDRkMDE0MGM0OGRmMmQ5NDcwYTU1ZDYyZCIsImt2dWlkIjoyNzE4LCJrdnV0eXBlIjowLCJrdnVsaW1pdCI6IkZhbHNlIiwia3Z1YWRtaW4iOiJUcnVlIiwia3Z1YWN0IjoiVHJ1ZSIsImt2dWxpbWl0dHJhbnMiOiJGYWxzZSIsImt2dXNob3dzdW0iOiJUcnVlIiwia3ZiaSI6IlRydWUiLCJrdmN0eXBlIjozLCJ1c2VCSSI6eyJDdXN0b21lckJJUmVwb3J0X1JlYWQiOltdLCJTYWxlQklSZXBvcnRfUmVhZCI6W10sIlByb2R1Y3RCSVJlcG9ydF9SZWFkIjpbXSwiRmluYW5jZUJJUmVwb3J0X1JlYWQiOltdfSwia3ZiaWQiOjI3MjIsImt2cmluZGlkIjozLCJrdnJjb2RlIjoiaG9hMjEwMCIsImt2cmlkIjo0MjMyLCJrdnVyaWQiOjQyMzIsImt2cmdpZCI6MSwicGVybXMiOiIifQ.mBcfjyYOY87jWaGQRiLcMLYNswSUcwDInKzaYuUgLcXT2Or6ZUVtjOb3CFNMAstNWEFFrs9G11YKhEJNYltv9zje8lRPGU6srWtepkKBLfGMUnkexMFjZkkayIj_NorunRU8uyzpslZvt0ALKp_kRCHWNl98gQrBL5sfGbPJJjyEj-Llmacjm5LVJZnGYA7-5Z8iPl0QK9lbiwCgVS4F-oyS4_IYPkY805INTs74dTRc_DkBIQNcAjqjxizvQtc5XGBh7tkvE8cPIo_jsh2T0T9joBPkMtNaaCjRT7YxwWBvw_ZdCguhoqZCANu2fiLAcOQjXsoE9x5U8eWEXP9bPw",
                "tooltip",
            ),
        )
        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
        FlutterEngineCache
            .getInstance()
            .put(TAG_FLUTTER_ENGINE, flutterEngine)
    }
}