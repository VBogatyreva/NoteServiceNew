import org.junit.Test

import org.junit.Assert.*

class NoteServiceTest {


    @Test
    fun add() {

        val note1 = NoteService.add(Note (1))
        val note2 = NoteService.add(Note (2))
        val result1 = NoteService.add(note1).idNote
        val result2=NoteService.add(note2).idNote

        assertNotEquals(result1, result2)
    }

    @Test
    fun createComment () {
        val note1 = Note(1,  comment = Comment(1))
        NoteService.add(note1)
        val comment1 = Comment(2)
        NoteService.createComment(1, comment1)
        val result = NoteService.update(1, note1)
        val expected = true
        assertEquals(expected, result)
    }



//    @Test(expected = NoteNotFoundException::class)
//    fun createCommentInNoteNotExist() {
//        val note = Note(1)
//        NoteService.add(note)
//        val commentTest = Comment(1)
//        NoteService.createComment(2, commentTest)
//        NoteService.update(2, note)
//    }



    @Test
    fun editNote () {
        val noteCorrected = Note(1)
        NoteService.add(noteCorrected)

        val expected = true
        val result = NoteService.edit(1, noteCorrected)

        assertEquals(expected, result)
    }

//    @Test(expected = NoteNotFoundException::class)
//    fun editNoteNotExist () {
////        val note = Note(1)
////        NoteService.add(note)
//        val noteCorrected = Note(1, textNote = "Текст скорректированный")
//        NoteService.add(noteCorrected)
//        NoteService.edit(2, noteCorrected)
//
//    }

    @Test
    fun editComment () {
        val noteCorrected = Note(1)
       NoteService.add(noteCorrected)

        val expected = true
        val result = NoteService.editComment(1, noteCorrected)

        assertEquals(expected, result)
    }



//    @Test(expected = CommentNotFoundException::class)
//    fun editCommentNotExist () {
//
//        val note = Note(1, comment = Comment(1))
//        NoteService.add(note)
//        NoteService.editComment(1, newNote = Note(comment = Comment(2)))
//    }

}