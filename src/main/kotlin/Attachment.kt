interface Attachment {
    val type: String
}

class Audio(val id: Int, val ownerId: Int, val artist: String, val title: String, val duration: Int)
class Video(val id: Int, val ownerId: Int, val title: String, val description: String, val duration: Int)
class File(val id: Int, val ownerId: Int, val title: String, val size: Int, val ext: Int)
class Photo(val id: Int, val albumId: Int, val ownerId: Int, val userId: Int, val text: String)
class Sticker(
    val stickerId: Int,
    val productId: Int,
    val isAllowed: Boolean,
    val innerType: String = "base_sticker_new"
)

open class AudioAttachment(val audio: Audio?, override val type: String = "Audio") : Attachment

open class VideoAttachment(val video: Video?, override val type: String = "Video") : Attachment

open class FileAttachment(val file: File?, override val type: String = "File") : Attachment

open class PhotoAttachment(val photo: Photo?, override val type: String = "Photo") : Attachment

open class StickerAttachment(val sticker: Sticker?, override val type: String = "Sticker") : Attachment
