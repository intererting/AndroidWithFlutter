package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Declare a local variable to reference the FlutterFragment so that you
    // can forward calls to it later.
    private var flutterFragmentA: FlutterFragment? = null
    private var flutterFragmentB: FlutterFragment? = null
    private val flutterLists = arrayListOf<FlutterFragment>()

    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT_A = "flutter_fragment_a"
        private const val TAG_FLUTTER_FRAGMENT_B = "flutter_fragment_b"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jumpToFlutter.setOnClickListener {
//            startActivity(FlutterActivity.createDefaultIntent(this))

            //打开指定路由
//            startActivity(
//                FlutterActivity.withNewEngine().initialRoute("/pageB").build(this)
//            )

            //Engine预加载
//            startActivity(FlutterActivity.withCachedEngine("my_engine_id").build(this))

            //添加FlutterFragment
            flutterFragmentA =
                supportFragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT_A) as FlutterFragment?
            flutterFragmentB =
                supportFragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT_B) as FlutterFragment?

            // Create and attach a FlutterFragment if one does not exist.
            if (flutterFragmentA === null) {
                val newFragment =
                    FlutterFragment.withCachedEngine("my_engine_id").build<FlutterFragment>()
                flutterFragmentA = newFragment
                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.fragment_container,
                        newFragment,
                        TAG_FLUTTER_FRAGMENT_A
                    )
                    .commit()
                flutterLists.add(flutterFragmentA as FlutterFragment)
            }

            if (flutterFragmentB === null) {
                val newFragment =
                    FlutterFragment.withNewEngine().dartEntrypoint("main_2")
                        .build<FlutterFragment>()
                flutterFragmentB = newFragment
                supportFragmentManager
                    .beginTransaction()
                    .add(
                        R.id.fragment_container_other,
                        newFragment,
                        TAG_FLUTTER_FRAGMENT_B
                    )
                    .commit()
                flutterLists.add(flutterFragmentB as FlutterFragment)
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        for (flutterFragment in flutterLists) {
            flutterFragment.onPostResume()
        }
    }

    override fun onNewIntent(@NonNull intent: Intent) {
        for (flutterFragment in flutterLists) {
            flutterFragment.onNewIntent(intent)
        }
    }

    override fun onBackPressed() {
        for (flutterFragment in flutterLists) {
            flutterFragment.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        for (flutterFragment in flutterLists) {
            flutterFragment.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        }

    }

    override fun onUserLeaveHint() {
        for (flutterFragment in flutterLists) {
            flutterFragment.onUserLeaveHint()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        for (flutterFragment in flutterLists) {
            flutterFragment.onTrimMemory(level)
        }
    }
}
