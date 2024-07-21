package im.niu.corelib.data

import android.content.Intent
import android.graphics.drawable.Drawable
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class AppInfo:LitePalSupport (){
    var appName: String? = null
    @Column(ignore = true)
    var icon: Drawable? = null
    @Column(ignore = true)
    var intent: Intent? = null
    var name: String? = null
    @Column(index = true, unique = true)
    var packageName: String? = null
    var type = 0
}