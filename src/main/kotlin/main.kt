data class Comment(
    val idComment: Int? = null,
    val idOwner: Int? = null,
    val messageComment: String? = null,
    var deletedComment: Boolean = false,
    var readComment: Boolean = false
)

data class Note(
    val idNote: Int? = null,
    val idOwner: Int? = null,
    val titleNote: String? = null,
    val textNote: String? = null,
    var deletedNote: Boolean = false,
    var readNote: Boolean = false,
    val comment: Comment = Comment()
)

interface NoteAndCommentService<N, C> {
    fun add (entity: N) : N
    fun createComment(id: Int, entity:C): C
    fun update (id: Int, entity: N): Boolean
    fun delete (id: Int)
    fun deleteComment(id: Int)
    fun edit (id: Int, entity: N): Boolean
    fun editComment(idNote: Int, entity: N): Boolean
    fun read (id: Int): List<N>
    fun readComment(id: Int): List<C>
    fun getById (id: Int): N
    fun restore (id: Int)
    fun restoreComment(id: Int)

}

object NoteService : NoteAndCommentService<Note, Comment> {

    var notes = mutableListOf(Note())
    var comments = mutableListOf(Comment())
    var lastIdNote: Int = 0
    var lastIdComment: Int = 0

    override fun add(note: Note): Note {
        val newNote = note.copy(
            idNote = ++lastIdNote,
            comment = note.comment
        )
        notes.add(newNote)
        return notes.last()
    }

    override fun createComment(idNote: Int, comment: Comment): Comment {
        val newComment = comment.copy(idComment = ++lastIdComment, messageComment = comment.messageComment)
        comments.add(newComment)
        for (note in notes) {
            if (note.idNote == idNote) {
                comments += comment
                return comments.last()
            }
        }
        throw NoteNotFoundException("Заметка $idNote не найдена!")
    }

    override fun update(idNote: Int, newNote: Note): Boolean {
        for ((index, note) in notes.withIndex()){
            if (notes[index].idNote == newNote.idNote) {
                notes[index] = note.copy(

                    idNote = note.idNote,
                    idOwner = note.idOwner,
                    titleNote = note.titleNote,
                    textNote = note.textNote,
                    readNote = note.readNote,
                    deletedNote = note.deletedNote,
                    comment = createComment(idNote = lastIdNote, comments.last())
                )
                return true
            }
        }
        return false
    }


    override fun delete(idNote: Int) {
        notes.forEach { note ->
            if (idNote == note.idNote) note.deletedNote = true

        }

    }

    override fun deleteComment(idComment: Int) {
        comments.forEach { comment ->
            if (idComment == comment.idComment) comment.deletedComment = true
        }

    }

    override fun read(idOwner: Int): List<Note> {
        notes.forEach { note ->
            if (note.idOwner == idOwner) note.readNote = true

        }
        return notes
    }

    override fun readComment(idOwner: Int): List<Comment> {
        comments.forEach { comment ->
            if (comment.idOwner == idOwner) comment.readComment = true

        }
        return comments
    }

    override fun getById(idNote: Int): Note {
        notes.forEach { note ->
            if (idNote == note.idNote) return note
        }
        throw NoteNotFoundException("Заметка не найдена!")
    }

    override fun restore(idNote: Int) {
        notes.forEach { note ->
            if (idNote == note.idNote && note.deletedNote == true) note.deletedNote = false
        }

    }

    override fun restoreComment(idComment: Int) {
        comments.forEach { comment ->
            if (idComment == comment.idComment && comment.deletedComment == true) comment.deletedComment = false
        }
    }

    override fun edit(idNote: Int, newNote: Note): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (idNote == note.idNote && note.comment.deletedComment != true && note.deletedNote != true) {

                notes[index] = note.copy(
                    idNote = note.idNote,
                    idOwner = note.idOwner,
                    titleNote = newNote.titleNote,
                    textNote = newNote.textNote,
                    comment = note.comment
                )
                return true
            }
        }
        throw NoteNotFoundException("Заметка не найдена!")
    }

    override fun editComment(idNote: Int, newNote: Note): Boolean {
        for ((index, note) in notes.withIndex()) {
            if (idNote == note.idNote && note.comment.idComment == newNote.comment.idComment && note.comment.deletedComment != true && note.deletedNote != true) {

                notes[index] = note.copy(
                    idNote = note.idNote,
                    idOwner = note.idOwner,
                    titleNote = note.titleNote,
                    textNote = note.textNote,
                    comment = newNote.comment
                )
                return true
            }
        }
        throw CommentNotFoundException("Комментарий не найден!")
    }

    fun print() {
        for (note in notes) {
            print(note)
            println()
        }
        println()
    }


}
class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
fun main() {

//    val note1 = Note(1, 1, "Заметка 1", "Скоро Весна", false)
//    val note2 = Note(2, 2, "Заметка 2", "Скоро Лето", false)
//    val note3 = Note(3, 1, "Заметка 3", "Скоро Осень", false)
//    NoteService.add(note1)
//    NoteService.add(note2)
//    NoteService.add(note3)
//    NoteService.print()
//
//    NoteService.delete(3)
//    NoteService.read(1)
//    NoteService.edit(2, newNote = Note(2, 2, "Заметка 2 ИСПРАВЛЕНА", "СКОРО ЛЕТО"))
//    NoteService.print()
//
//
//    val comment1 = Comment(1, 1, "Точно весна?")
//    NoteService.createComment(1, comment1)
//    NoteService.update(1, note1)
//    val comment2 = Comment(2, 2, "Уверен?")
//    NoteService.createComment(2, comment2)
//    NoteService.update(2, note2)
//    val comment3 = Comment(3, 3, "Я ЗНАЮ")
//    NoteService.createComment(3, comment3)
//    NoteService.update(3, note3)
//    NoteService.print()
//
//    NoteService.editComment(2, newNote = Note(comment = Comment(2, 2, "Я точно ЗНАЮ")))
//    NoteService.update(2, note3)
//    NoteService.restore(3)
//    NoteService.print()

}