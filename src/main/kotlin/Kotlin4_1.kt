data class Comments(
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean,
    val count: Int = 0,
)

data class Likes(val userLikes: Boolean, val canLike: Boolean, val canPublish: Boolean, val count: Int = 0)

data class Post(
    var id: Int,
    var ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int,
    val friendsOnly: Boolean,
    val comments: Comments,
    val likes: Likes
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
        for (postsInArray in posts) {
            if (post.id == postsInArray.id) {
                posts[postsInArray.id - 1] = post.copy(id = postsInArray.id)
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