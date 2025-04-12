class PostNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class ReasonNotFoundException(message: String) : RuntimeException(message)

data class ReportComments(val ownerId: Int, val commentId: Int, val reason: Int)

data class Comments(
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true,
    val count: Int = 0,
)

data class Donut(val isDon: Boolean, val placeholder: String)

data class Thread(val count: Int, val canPost: Boolean, val showReplyButton: Boolean, val groupsCanPost: Boolean)

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val donut: Donut?,
    val replyToUser: Int,
    val replyToComment: Int,
    val attachment: Array<Attachment>?,
    val parentsStack: Array<Int>?,
    val thread: Thread?
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
    val attachment: Array<Attachment>?,
    val createdBy: Int = 233,
    val date: Int = 2042025,
    val text: String = "TEXT",
    val replyOwnerId: Int = 223,
    val friendsOnly: Boolean = true,
)

object WallService {
    private var numberPost = 0
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportComments = emptyArray<ReportComments>()
    fun add(post: Post): Post {
        numberPost++
        posts += post.copy(id = numberPost)
        return posts.last()
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for ((index) in posts.withIndex()) {
            if (postId == posts[index].id) {
                comments += comment
                return comment
            }
        }
        throw PostNotFoundException("No found post with $postId")
    }

    fun reportComment(reportComment: ReportComments): ReportComments {
        if (reportComment.reason > 8 || reportComment.reason < 0) {
            throw ReasonNotFoundException("No found reason with ${reportComment.reason}")
        }
        for ((index) in comments.withIndex()) {
            if (reportComment.commentId == comments[index].id) {
                reportComments += reportComment
                return reportComment
            }
        }
        throw CommentNotFoundException("No found comment with ${reportComment.commentId}")
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