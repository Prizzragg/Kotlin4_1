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
        val post1 = Post(0, comments, null, 643, 912, arrayOf(audioAttachment, videoAttachment))
        service.add(post1)
        val post2 = Post(222, comments, null, 543, 653, arrayOf(audioAttachment, videoAttachment))
        val result = service.update(post2)
        assertFalse(result)
    }
}