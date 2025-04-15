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

    @Test
    fun notesAdd() {
        val service = WallService
        val result = service.notesAdd("title", "text", "15042025")
        assertEquals(1, result)
    }

    @Test
    fun notesDelete() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        val result = service.notesDelete(2)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesDeleteWithException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.notesDelete(0)
    }

    @Test
    fun notesEdit() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        val result = service.notesEdit(2, "title3", "text3", "17042025")
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesEditWithException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.notesEdit(0, "title3", "text3", "17042025")
    }

    @Test
    fun notesGet() {
        val note1 = Note(1, "title", "text", "15042025")
        val note2 = Note(2, "title2", "text2", "16042025")
        val getExpectedNotes = mutableListOf(note1, note2)
        val getNotes = mutableListOf(1, 2)
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.notesAdd("title3", "text3", "17042025")
        val result = service.notesGet(getNotes)
        assertEquals(getExpectedNotes, result)
    }

    @Test
    fun notesGetVoid() {
        val getExpectedNotes = mutableListOf<Note>()
        val getNotes = mutableListOf(4, 5)
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.notesAdd("title3", "text3", "17042025")
        val result = service.notesGet(getNotes)
        assertEquals(getExpectedNotes, result)
    }

    @Test
    fun notesGetById() {
        val getById = Note(2, "title2", "text2", "16042025")
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        val result = service.notesGetById(2)
        assertEquals(getById, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetByIdWithException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.notesGetById(5)
    }

    @Test
    fun createCommentNotes() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        val result = service.createCommentNotes(1, "Message")
        assertEquals(1, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createCommentNotesWithException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(2, "Message")
    }

    @Test
    fun notesDeleteComment() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(1, "Message")
        val result = service.notesDeleteComment(1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesDeleteCommentWithNoteException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(2, "Message")
        service.notesDeleteComment(1)
    }

    @Test(expected = CommentNoteNotFoundException::class)
    fun notesDeleteCommentWithCommentException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(1, "Message")
        service.notesDeleteComment(2)
    }

    @Test
    fun notesEditComment() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(1, "Message")
        val result = service.notesEditComment(1, "Message2")
        assertEquals(true, result)
    }

    @Test(expected = CommentNoteNotFoundException::class)
    fun notesEditCommentWithCommentException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(1, "Message")
        service.notesEditComment(2, "Message2")
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesEditCommentWithNoteException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.createCommentNotes(2, "Message")
        service.notesEditComment(1, "Message2")
    }

    @Test
    fun notesGetComment() {
        val service = WallService
        val comment1 = NotesComment(1, 1, "Message1")
        val comment2 = NotesComment(1, 2, "Message2")
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        val getExpectedNotesComment = mutableListOf(comment1, comment2)
        service.createCommentNotes(1, "Message1")
        service.createCommentNotes(1, "Message2")
        service.createCommentNotes(2, "Message3")
        val result = service.notesGetComment(1)
        assertEquals(getExpectedNotesComment, result)
    }

    @Test
    fun notesGetCommentVoid() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        val getExpectedNotesComment = mutableListOf<NotesComment>()
        service.createCommentNotes(2, "Message1")
        service.createCommentNotes(2, "Message2")
        service.createCommentNotes(2, "Message3")
        val result = service.notesGetComment(1)
        assertEquals(getExpectedNotesComment, result)
    }

    @Test
    fun notesRestoreComment() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.createCommentNotes(1, "Message1")
        service.createCommentNotes(1, "Message2")
        service.createCommentNotes(2, "Message2")
        service.notesDeleteComment(2)
        val result = service.notesRestoreComment(2)
        assertEquals(true, result)
    }

    @Test(expected = CommentNoteNotFoundException::class)
    fun notesRestoreCommentWithException() {
        val service = WallService
        service.notesAdd("title", "text", "15042025")
        service.notesAdd("title2", "text2", "16042025")
        service.createCommentNotes(1, "Message1")
        service.createCommentNotes(1, "Message2")
        service.createCommentNotes(2, "Message2")
        service.notesDeleteComment(2)
        service.notesRestoreComment(4)
    }
}