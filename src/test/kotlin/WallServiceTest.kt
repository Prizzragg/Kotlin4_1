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
        val comments = Comments(canPost = true, groupsCanPost = false, canClose = false, canOpen = false)
        val likes = Likes(userLikes = true, canLike = true, canPublish = true)
        val post1 = Post(
            0,
            33,
            345,
            22,
            2042025,
            "TEXT",
            123,
            false,
            comments,
            likes
        )
        val result = service.add(post1)
        assertEquals(1, result.id)
    }

    @Test
    fun updateTrue() {
        val service = WallService
        val comments = Comments(canPost = true, groupsCanPost = false, canClose = false, canOpen = false)
        val likes = Likes(userLikes = true, canLike = true, canPublish = true)
        val post1 = Post(
            22,
            33,
            345,
            22,
            2042025,
            "TEXT",
            123,
            false,
            comments,
            likes
        )
        service.add(post1)
        val post2 = Post(1, 221, 345, 22, 2042025, "TEXT", 123, false, comments, likes)
        val result = service.update(post2)
        assertEquals(true, result)
    }

    @Test
    fun updateFalse() {
        val service = WallService
        val comments = Comments(canPost = true, groupsCanPost = false, canClose = false, canOpen = false)
        val likes = Likes(userLikes = true, canLike = true, canPublish = true)
        val post1 = Post(
            0,
            33,
            345,
            22,
            2042025,
            "TEXT",
            123,
            false,
            comments,
            likes
        )
        service.add(post1)
        val post2 = Post(
            222,
            221,
            345,
            22,
            2042025,
            "TEXT",
            123,
            false,
            comments,
            likes
        )
        val result = service.update(post2)
        assertFalse(result)
    }
}