class PostNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class ReasonNotFoundException(message: String) : RuntimeException(message)
class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNoteNotFoundException(message: String) : RuntimeException(message)

data class Note(val id: Int, val title: String, val text: String, val date: String)

data class NotesComment(val noteId: Int, val cId: Int, val message: String)

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
    private var numberNote = 0
    private var numberNoteComment = 0
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reportComments = emptyArray<ReportComments>()
    private var notes: MutableList<Note> = mutableListOf()
    private var notesComment: MutableList<NotesComment> = mutableListOf()
    private var deleteNotesComment: MutableList<NotesComment> = mutableListOf()
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

    fun notesAdd(title: String, text: String, date: String): Int {
        numberNote++
        notes.add(Note(numberNote, title, text, date))
        return numberNote
    }

    fun notesDeleteComment(commentId: Int): Boolean {
        for ((index) in notesComment.withIndex()) {
            if (commentId == notesComment[index].cId) {
                deleteNotesComment += notesComment[index]
                notesComment.removeAt(index)
                return true
            }
        }
        throw CommentNoteNotFoundException("No element with $commentId")
    }

    fun notesEditComment(commentId: Int, message: String): Boolean {
        for ((index) in notesComment.withIndex()) {
            if (commentId == notesComment[index].cId) {
                val idNote = notesComment[index].noteId
                notesComment.removeAt(index)
                notesComment.add(index, NotesComment(idNote, commentId, message))
                return true
            }
        }
        throw CommentNoteNotFoundException("No element with $commentId")
    }

    fun notesDelete(id: Int): Boolean {
        for ((index) in notes.withIndex()) {
            if (id == notes[index].id) {
                notes.removeAt(index)
                notesDeleteComment(id)
                return true
            }
        }
        throw NoteNotFoundException("No element with $id")
    }

    fun notesEdit(id: Int, title: String, text: String, date: String): Boolean {
        for ((index) in notes.withIndex()) {
            if (id == notes[index].id) {
                notes.removeAt(index)
                notes.add(index, Note(id, title, text, date))
                return true
            }
        }
        throw NoteNotFoundException("No element with $id")
    }

    fun notesGet(noteIds: MutableList<Int>): MutableList<Note> {
        val notesGet: MutableList<Note> = mutableListOf()
        for ((index1) in noteIds.withIndex()) {
            for ((index2) in notes.withIndex()) {
                if (noteIds[index1] == notes[index2].id) {
                    notesGet.add(notes[index2])
                }
            }
        }
        return notesGet
    }

    fun notesGetById(noteId: Int): Note {
        for ((index) in notes.withIndex()) {
            if (noteId == notes[index].id) {
                return notes[index]
            }
        }
        throw NoteNotFoundException("No element with $noteId")
    }

    fun notesRestoreComment(commentId: Int): Boolean {
        for ((index) in deleteNotesComment.withIndex()) {
            if (commentId == deleteNotesComment[index].cId) {
                notesComment.add(deleteNotesComment[index])
                deleteNotesComment.removeAt(index)
                return true
            }
        }
        throw CommentNoteNotFoundException("No element with $commentId")
    }

    fun createCommentNotes(noteId: Int, message: String): Int {
        for ((index) in notes.withIndex()) {
            if (noteId == notes[index].id) {
                numberNoteComment++
                val createdNoteComment = NotesComment(noteId, numberNoteComment, message)
                notesComment.add(createdNoteComment)
                return numberNoteComment
            }
        }
        throw NoteNotFoundException("No element with $noteId")
    }

    fun notesGetComment(noteId: Int): MutableList<NotesComment> {
        val notesGetComment: MutableList<NotesComment> = mutableListOf()
        for ((index) in notesComment.withIndex()) {
            if (noteId == notesComment[index].noteId) {
                notesGetComment += notesComment[index]
            }
        }
        return notesGetComment
    }

    fun clear() {
        posts = emptyArray()
        numberPost = 0
        numberPost = 0
        numberNote = 0
        numberNoteComment = 0
        posts = emptyArray<Post>()
        comments = emptyArray<Comment>()
        reportComments = emptyArray<ReportComments>()
        notes = mutableListOf()
        notesComment = mutableListOf()
        deleteNotesComment = mutableListOf()
    }
}

fun main() {
}