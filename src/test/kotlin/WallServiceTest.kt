import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class WallServiceTest {

    @Before
    fun clearBeforeTests() {
        WallService.clear()
    }

    @Test
    fun add() {
        val service = WallService
        val comments = Comments()
        val audioAttachment = AudioAttachment(null)
        val videoAttachment = VideoAttachment(null)
        val post1 = Post(0, comments, null, 133, 233, arrayOf(audioAttachment, videoAttachment))
        val result = service.add(post1)
        assertEquals(1, result.id)
    }

    @Test
    fun updateTrue() {
        val service = WallService
        val comments = Comments()
        val audioAttachment = AudioAttachment(null)
        val videoAttachment = VideoAttachment(null)
        val post1 = Post(22, comments, null, 234, 256, arrayOf(audioAttachment, videoAttachment))
        service.add(post1)
        val post2 = Post(1, comments, null, 654, 765, arrayOf(audioAttachment, videoAttachment))
        val result = service.update(post2)
        assertEquals(true, result)
    }

    @Test
    fun updateFalse() {
        val service = WallService
        val comments = Comments()
        val audioAttachment = AudioAttachment(null)
        val videoAttachment = VideoAttachment(null)
        val post1 = Post(0, comments, null, 643, 91, arrayOf(audioAttachment, videoAttachment))
        service.add(post1)
        val post2 = Post(222, comments, null, 543, 65, arrayOf(audioAttachment, videoAttachment))
        val result = service.update(post2)
        assertFalse(result)
    }

    @Test
    fun createCommentNoException() {
        val service = WallService
        val commentsPost = Comments()
        val comment = Comment(
            1,
            5,
            12042025,
            "text",
            null,
            1,
            1,
            null,
            null,
            null
        )
        val post = Post(122, commentsPost, null, 643, 912, null)
        service.add(post)
        val result = service.createComment(1, comment)
        assertEquals(comment, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentWithException() {
        val service = WallService
        val commentsPost = Comments()
        val comment = Comment(
            1,
            5,
            12042025,
            "text",
            null,
            1,
            1,
            null,
            null,
            null
        )
        val post = Post(122, commentsPost, null, 643, 912, null)
        service.add(post)
        service.createComment(2, comment)
    }

    @Test
    fun reportCommentNoException() {
        val service = WallService
        val commentsPost = Comments()
        val reportComment = ReportComments(1, 1, 3)
        val comment = Comment(
            1,
            5,
            12042025,
            "text",
            null,
            1,
            1,
            null,
            null,
            null
        )
        val post = Post(122, commentsPost, null, 643, 912, null)
        service.add(post)
        service.createComment(1, comment)
        val result = service.reportComment(reportComment)
        assertEquals(reportComment, result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun reportCommentWithCommentException() {
        val service = WallService
        val commentsPost = Comments()
        val reportComment = ReportComments(1, 3, 3)
        val comment = Comment(
            1,
            5,
            12042025,
            "text",
            null,
            1,
            1,
            null,
            null,
            null
        )
        val post = Post(122, commentsPost, null, 643, 912, null)
        service.add(post)
        service.createComment(1, comment)
        service.reportComment(reportComment)
    }

    @Test(expected = ReasonNotFoundException::class)
    fun reportCommentWithReasonException() {
        val service = WallService
        val commentsPost = Comments()
        val reportComment = ReportComments(1, 1, 9)
        val comment = Comment(
            1,
            5,
            12042025,
            "text",
            null,
            1,
            1,
            null,
            null,
            null
        )
        val post = Post(122, commentsPost, null, 643, 912, null)
        service.add(post)
        service.createComment(1, comment)
        service.reportComment(reportComment)
    }
}