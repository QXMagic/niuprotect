package im.niu.corelib.data

/**
 * 统计数据---记录每个应用的包名，使用时长和使用次数
 *
 *
 */
class PackageInfo(
    private var mUsedCount: Int,
    private var mUsedTime: Long,
    private var mPackageName: String,
    private var mAppName: String
) {
    fun addCount() {
        mUsedCount++
    }

    fun getmUsedCount(): Int {
        return mUsedCount
    }

    fun setmUsedCount(mUsedCount: Int) {
        this.mUsedCount = mUsedCount
    }

    fun getmUsedTime(): Long {
        return mUsedTime
    }

    fun setmUsedTime(mUsedTime: Long) {
        this.mUsedTime = mUsedTime
    }

    fun getmPackageName(): String {
        return mPackageName
    }

    fun setmPackageName(mPackageName: String) {
        this.mPackageName = mPackageName
    }

    fun getmAppName(): String {
        return mAppName
    }

    fun setmAppName(mAppName: String) {
        this.mAppName = mAppName
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        val standardDetail = other as PackageInfo
        return standardDetail.getmPackageName() == mPackageName
    }

    override fun hashCode(): Int {
        return (mPackageName + mUsedTime).hashCode()
    }

    override fun toString(): String {
        return "PackageInfo{" +
                "mUsedCount=" + mUsedCount +
                ", mUsedTime=" + mUsedTime +
                ", mPackageName='" + mPackageName + '\'' +
                ", mAppName='" + mAppName + '\'' +
                '}'
    }
}