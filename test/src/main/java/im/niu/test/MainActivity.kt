package im.niu.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import im.niu.corelib.App
import im.niu.corelib.net.MessageWraper
import im.niu.corelib.net.WebSocketManager
import im.niu.data.Userinfo
import im.niu.test.ui.theme.NiuprotectTheme

class MainActivity : ComponentActivity() {
    private lateinit var webSocketManager: WebSocketManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NiuprotectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        App.init(this)
        webSocketManager = WebSocketManager("192.168.31.31", 9090);
        test()
    }

    override fun onResume() {
        super.onResume()
        webSocketManager.reconnect()
        test()
    }

    private fun test() {
        while (!webSocketManager.isEnable()) {
            Thread.sleep(100)
        }
        var message = MessageWraper()
        message.data = Userinfo.UserInfo.newBuilder()
            .setId("uu0101")
            .setDevice("xm")
            .setOs("os")
            .setVersion(15)
            .build()
        webSocketManager.sendMessage(message)
        message.data = Userinfo.AppInfo.newBuilder()
            .setName("testaaaaaaaaaaaa").setPackage(BuildConfig.APPLICATION_ID)
            .build()
        webSocketManager.sendMessage(message)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NiuprotectTheme {
        Greeting("Android")
    }
}