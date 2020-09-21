package ru.netology

import org.junit.Test

import org.junit.Assert.*
import ru.netology.Data.Comment
import ru.netology.Data.Note

class NoteServiceTest {

    @Test
    fun add() {
        // arrange
        val note = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        // act
        val result = NoteService.add(note)

        // assert
        assert(result.id != 0)
    }

    @Test
    fun createComment_True() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val comment = Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment")

        //act
        val result = NoteService.createComment(comment)

        //assert
        assertTrue(result)
    }

    @Test
    fun createComment_False() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val comment = Comment(id = 1, noteId = 0, date = 2020_09_02, text = "First comment")

        //act
        val result = NoteService.createComment(comment)

        //assert
        assertFalse(result)
    }


    @Test
    fun deleteNote_True() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        // act
        val result = NoteService.deleteNote(2)

        // assert
        assertTrue(result)

    }

    @Test
    fun deleteNote_InputError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        // act
        val result = NoteService.deleteNote(5)

        // assert
        assertFalse(result)
    }

    @Test
    fun deleteNote_ReDeletion() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))


        // act
        NoteService.deleteNote(2)
        val result = NoteService.deleteNote(2)

        // assert
        assertFalse(result)
    }


    @Test
    fun deleteComment_True() {
        // arrange
        val firstNote = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val secondNote = Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        )
        val thirdNote = Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val firstComment = Comment(id = 1, noteId = 3, date = 2020_09_01, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 3, date = 2020_09_01, text = "Second comment")
        val thirdComment = Comment(id = 3, noteId = 3, date = 2020_09_01, text = "Third comment")
        val fourthComment = Comment(id = 4, noteId = 3, date = 2020_09_01, text = "Fourth comment")

        // act
        NoteService.add(firstNote)
        NoteService.add(secondNote)
        NoteService.add(thirdNote)
        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.createComment(thirdComment)
        NoteService.createComment(fourthComment)
        val result = NoteService.deleteComment(commentId = 4)

        //assert
        assertTrue(result)
    }

    @Test
    fun deleteComment_ReDeletion() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val firstComment = Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 1, date = 2020_09_01, text = "Second comment")

        // act
        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.deleteComment(commentId = 2)
        val result = NoteService.deleteComment(commentId = 2)

        //assert
        assertFalse(result)
    }


    @Test
    fun editNote_True() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val editedNote = Note(
            id = 1,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2020_09_15,
            emptyList(),
            emptyList()
        )

        // act
        val result = NoteService.editNote(editedNote)

        // assert
        assertTrue(result)
    }

    @Test
    fun editNote_False() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val editedNote = Note(
            id = 2,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2020_09_15,
            emptyList(),
            emptyList()
        )

        // act
        NoteService.deleteNote(noteId = 2)
        val result = NoteService.editNote(editedNote)

        // assert
        assertFalse(result)
    }

    @Test
    fun editNote_IdError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        val editedNote = Note(
            id = 5,
            ownerId = 555,
            title = "Edit",
            text = "That's edited text", date = 2020_09_15,
            emptyList(),
            emptyList()
        )

        // act
        val result = NoteService.editNote(editedNote)

        // assert
        assertFalse(result)
    }


    @Test
    fun editComment_True() {
        // arrange
        val firstNote = Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val secondNote = Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val thirdNote = Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2020_09_13,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val fourthNote = Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2020_09_13,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val fifthNote = Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2020_09_21,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val sixthNote = Note(
            ownerId = 66,
            title = "Sixth note",
            text = "Sixth Hello",
            date = 2020_09_21,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val seventhNote = Note(
            ownerId = 77,
            title = "Seventh note",
            text = "Seventh Hello",
            date = 2020_09_21,
            comments = emptyList(),
            deletedComments = emptyList()
        )

        val eighthNote = Note(
            ownerId = 88,
            title = "Eighth note",
            text = "Eighth Hello",
            date = 2020_09_21,
            comments = emptyList(),
            deletedComments = emptyList()
        )
        NoteService.add(firstNote)
        NoteService.add(secondNote)
        NoteService.add(thirdNote)
        NoteService.add(fourthNote)
        NoteService.add(fifthNote)
        NoteService.add(sixthNote)
        NoteService.add(seventhNote)
        NoteService.add(eighthNote)


        val firstComment  = Comment(id = 1, noteId = 8, date = 2020_09_01, text = "First comment")
        val secondComment = Comment(id = 2, noteId = 8, date = 2020_09_01, text = "Second comment")
        val thirdComment = Comment(id = 3, noteId = 8, date = 2020_09_01, text = "Third comment")
        val fourthComment = Comment(id = 4, noteId = 8, date = 2020_09_01, text = "Fourth comment")
        val fifthComment = Comment(id = 5, noteId = 8, date = 2020_09_01, text = "Fifth comment")
        val sixthComment = Comment(id = 6, noteId = 8, date = 2020_09_01, text = "Sixth comment")
        val seventhComment = Comment(id = 7, noteId = 8, date = 2020_09_01, text = "Sixth comment")
        val eighthComment = Comment(id = 8, noteId = 8, date = 2020_09_01, text = "Sixth comment")

        NoteService.createComment(firstComment)
        NoteService.createComment(secondComment)
        NoteService.createComment(thirdComment)
        NoteService.createComment(fourthComment)
        NoteService.createComment(fifthComment)
        NoteService.createComment(sixthComment)
        NoteService.createComment(seventhComment)
        NoteService.createComment(eighthComment)


        val editedComment = Comment(id = 8, noteId = 8, date = 2020_09_01, text = "Edited comment!!!")

        // act
        val result = NoteService.editComment(editedComment)

        // assert
        assertTrue(result)
    }

    @Test
    fun editComment_DeletedCommentError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2020_09_13,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.createComment(Comment(id = 1, noteId = 3, date = 2020_09_01, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 3, date = 2020_09_01, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 3, date = 2020_09_01, text = "Third comment"))

        NoteService.deleteComment(commentId = 3)
        val editedComment = Comment(id = 3, noteId = 1, date = 2020_09_01, text = "Edited comment!!!")


        // act
        val result = NoteService.editComment(editedComment)

        // assert
        assertFalse(result)
    }

    @Test
    fun editComment_DeletedNoteError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment"))
        NoteService.deleteNote(noteId = 1)
        val editedComment = Comment(id = 1, noteId = 1, date = 2020_09_01, text = "Edited comment!!!")

        // act
        val result = NoteService.editComment(editedComment)

        // assert
        assertFalse(result)
    }

    @Test
    fun editComment_IdError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 2, date = 2020_09_01, text = "Second comment"))
        val editedComment = Comment(id = 5, noteId = 1, date = 2020_09_01, text = "Edited comment!!!")


        // act
        val result = NoteService.editComment(editedComment)

        // assert
        assertFalse(result)
    }

    @Test
    fun restoreComment_True() {

        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2020_09_13,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2020_09_20,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2020_09_20,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 66,
            title = "Sixth note",
            text = "Sixth Hello",
            date = 2020_09_20,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.add(Note(
            ownerId = 77,
            title = "Seventh note",
            text = "Seventh Hello",
            date = 2020_09_20,
            comments = emptyList(),
            deletedComments = emptyList()
        ))


        NoteService.createComment(Comment(id = 1, noteId = 4, date = 2020_09_01, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 4, date = 2020_09_01, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 4, date = 2020_09_01, text = "Third comment"))
        NoteService.createComment(Comment(id = 4, noteId = 4, date = 2020_09_01, text = "Fourth comment"))
        NoteService.createComment(Comment(id = 5, noteId = 4, date = 2020_09_01, text = "Fifth comment"))
        NoteService.createComment(Comment(id = 6, noteId = 4, date = 2020_09_01, text = "Sixth comment"))
        NoteService.createComment(Comment(id = 7, noteId = 4, date = 2020_09_01, text = "Seventh comment"))


        NoteService.deleteComment(7)

        // act
        val result = NoteService.restoreComment(commentId = 7)

        // assert
        assertTrue(result)
    }

    @Test
    fun restoreComment_NotDeletedComment() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))

        NoteService.createComment(Comment(id = 1, noteId = 1, date = 2020_09_01, text = "First comment"))

        // act
        val result = NoteService.restoreComment(commentId = 1)

        //assert
        assertFalse(result)
    }

    @Test
    fun restoreComment_DeletedNoteError() {
        // arrange
        NoteService.add(Note(
            ownerId = 12,
            title = "First note",
            text = "Hello everyone",
            date = 2020_09_11,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 21,
            title = "Second note",
            text = "Second Hello",
            date = 2020_09_12,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 33,
            title = "Third note",
            text = "Third Hello",
            date = 2020_09_13,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 44,
            title = "Fourth note",
            text = "Fourth Hello",
            date = 2020_09_20,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.add(Note(
            ownerId = 55,
            title = "Fifth note",
            text = "Fifth Hello",
            date = 2020_09_21,
            comments = emptyList(),
            deletedComments = emptyList()
        ))
        NoteService.createComment(Comment(id = 1, noteId = 5, date = 2020_09_01, text = "First comment"))
        NoteService.createComment(Comment(id = 2, noteId = 5, date = 2020_09_01, text = "Second comment"))
        NoteService.createComment(Comment(id = 3, noteId = 5, date = 2020_09_01, text = "Third comment"))
        NoteService.createComment(Comment(id = 4, noteId = 5, date = 2020_09_01, text = "Fourth comment"))
        NoteService.createComment(Comment(id = 5, noteId = 5, date = 2020_09_01, text = "Fourth comment"))
        NoteService.deleteNote(5)

        // act
        val result = NoteService.restoreComment(commentId = 5)

        // assert
        assertFalse(result)
    }
}
