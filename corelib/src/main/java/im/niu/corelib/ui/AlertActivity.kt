package im.niu.corelib.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import im.niu.corelib.ui.ui.theme.NiuprotectTheme
import im.niu.corelib.utils.ILog

class AlertActivity : ComponentActivity(),IUICallbackAble {
    private val tag = AlertActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NiuprotectTheme {
                Greeting(this)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        ILog.d(tag, "key down $keyCode")
//        return super.onKeyDown(keyCode, event)
        return false
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        ILog.d(tag, "key up $keyCode")
//        return super.onKeyUp(keyCode, event)
        return false
    }

    override fun onClick() {
        finish()
    }
}
@Composable
fun Greeting(callback:IUICallbackAble){
    Button(onClick = { callback.onClick()}) {
        Text("关闭")
    }
}