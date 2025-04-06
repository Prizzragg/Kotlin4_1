sealed class Attachment(val type: String)

data class VideoAttachment(val video: Video?) : Attachment("video")
data class AudioAttachment(val audio: Audio?) : Attachment("audio")
data class FileAttachment(val file: File?) : Attachment("file")
data class PhotoAttachment(val photo: Photo?) : Attachment("photo")
data class StickerAttachment(val sticker: Sticker?) : Attachment("sticker")

data class Audio(val id: Int, val ownerId: Int, val artist: String, val title: String, val duration: Int)
data class Video(val id: Int, val ownerId: Int, val title: String, val description: String, val duration: Int)
data class File(val id: Int, val ownerId: Int, val title: String, val size: Int, val ext: Int)
data class Photo(val id: Int, val albumId: Int, val ownerId: Int, val userId: Int, val text: String)
data class Sticker(
    val stickerId: Int,
    val productId: Int,
    val isAllowed: Boolean,
    val innerType: String = "base_sticker_new"
)