package im.niu.corelib.data.json

data class BaseResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)
