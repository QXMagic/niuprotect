package im.niu.corelib.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.niu.corelib.R
import im.niu.corelib.accessibility.AccessibilitySettingHelper
import im.niu.corelib.ui.ui.theme.NiuprotectTheme


class AccessSettingActivity : ComponentActivity(),IUICallbackAble {
    private val TAG=AccessSettingActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NiuprotectTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Guideline(
                        this,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        if(AccessibilitySettingHelper.isAccessibilitySettingsOnByService(this)){
            finish()
        }
    }

    override fun onClick(){
//        Builder(this)
//            .setTitle("开启")
//            .setMessage("是否开启")
//            .setPositiveButton("确定") { _, _ ->
//                jumpToSetting()
//            }.show()
        jumpToSetting()
    }

    private fun jumpToSetting() {
        AccessibilitySettingHelper.openAccessibility(this)
    }
}


@Composable
fun Guideline(callback:IUICallbackAble, modifier: Modifier = Modifier) {
    Column(modifier= modifier.verticalScroll(rememberScrollState())) {
        Text(stringResource( R.string.setting_accessibility_title), modifier= Modifier.padding(16.dp))
        Image(painterResource(R.drawable.guide),"guide1")
        Row(modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                callback.onClick()
            }) {
                Text(text = stringResource( R.string.setting_accessibility_button))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NiuprotectTheme {
        Surface(modifier = Modifier.fillMaxSize()){
        Guideline(object:IUICallbackAble{
            override fun onClick() {

            }
        })
    }}
}
