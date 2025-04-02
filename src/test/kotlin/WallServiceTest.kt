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
        val likes = Likes()
        val post1 = Post(0, comments, likes)
        val result = service.add(post1)
        assertEquals(1, result.id)
    }

    @Test
    fun updateTrue() {
        val service = WallService
        val comments = Comments()
        val likes = Likes()
        val post1 = Post(22, comments, likes)
        service.add(post1)
        val post2 = Post(1, comments, likes)
        val result = service.update(post2)
        assertEquals(true, result)
    }

    @Test
    fun updateFalse() {
        val service = WallService
        val comments = Comments()
        val likes = Likes()
        val post1 = Post(0, comments, likes)
        service.add(post1)
        val post2 = Post(222, comments, likes)
        val result = service.update(post2)
        assertFalse(result)
    }
}