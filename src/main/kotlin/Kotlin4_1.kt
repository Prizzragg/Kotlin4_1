data class Comments(
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true,
    val count: Int = 0,
)

data class Likes(
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true,
    val count: Int = 0
)

data class Post(
    var id: Int,
    val comments: Comments,
    val likes: Likes?,
    val ownerId: Int?,
    val fromId: Int?,
    val attachment: Array<Attachment>,
    val createdBy: Int = 233,
    val date: Int = 2042025,
    val text: String = "TEXT",
    val replyOwnerId: Int = 223,
    val friendsOnly: Boolean = true,
)

object WallService {
    private var numberPost = 0
    private var posts = emptyArray<Post>()
    fun add(post: Post): Post {
        numberPost++
        posts += post.copy(id = numberPost)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index) in posts.withIndex()) {
            if (post.id == posts[index].id) {
                posts[index] = post.copy(id = posts[index].id)
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        numberPost = 0
    }
}

fun main() {

}