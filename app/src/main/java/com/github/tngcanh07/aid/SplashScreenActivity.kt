package com.github.tngcanh07.aid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.github.tngcanh07.aid.layout.LayoutAnalysisActivity

/**
 * Created by toannguyen
 * Jun 05, 2019 at 17:48
 */
class SplashScreenActivity : AppCompatActivity() {

  override fun onStart() {
    super.onStart()
    System.out.println(R.layout::class.java.name)
    Intent(this, LayoutAnalysisActivity::class.java)
        .putExtra(LayoutAnalysisActivity.EXTRA_RES_PACKAGE_NAME, BuildConfig.APPLICATION_ID)
        .also(this::startActivity)
    finish()
  }

}
