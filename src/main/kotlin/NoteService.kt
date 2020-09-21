package ru.netology

import ru.netology.Data.Comment
import ru.netology.Data.Note

object NoteService {
    private var notes = mutableListOf<Note>()
    private var deletedNotes = mutableListOf<Note>()
    private var lastId = 1


    fun add(note: Note): Note {
        notes.plusAssign(note.copy(id = lastId++))
        return notes.last()
    }

    fun createComment(comment: Comment): Boolean {
        for (n: Note in notes) {

            if (n.id == comment.noteId) {
                val tempListOfComments = (n.comments).toMutableList()
                tempListOfComments.add(comment)
                val updatedNote = n.copy(comments = tempListOfComments)
                notes[notes.indexOf(n)] = updatedNote
                return true
            }
        }
        return false
    }

    fun deleteNote(noteId: Int): Boolean {
        if (noteId < 0 || noteId > notes.size) {
            println("Неверный Id, повторите ввод!")
            return false
        }

        for (note: Note in deletedNotes) {
            if (noteId == note.id) {
                println("Заметка с Id = $noteId уже была удалена ранее, повторное удаление невозможно!")
                return false
            }
        }

        for (note: Note in notes) {
            if (noteId == note.id) {
                deletedNotes.add(note)
                notes.remove(note)
                return true
            }
        }
        return false
    }

    fun deleteComment(commentId: Int): Boolean {
        for (note: Note in notes) {
            for (delComm: Comment in note.deletedComments) {
                if (commentId == delComm.id) {
                    println("Комментарий с Id= $commentId удален ранее, повторное удаление невозможно!")
                    return false
                }
            }

            for (comm: Comment in note.comments) {
                if (commentId == comm.id) {
                    val tempListOfComments = note.comments.toMutableList()
                    tempListOfComments.remove(comm)
                    val tempListOfDeletedComments = note.deletedComments.toMutableList()
                    tempListOfDeletedComments.plusAssign(comm)
                    val updatedNote = note.copy(
                        comments = tempListOfComments,
                        deletedComments = tempListOfDeletedComments
                    )
                    notes[notes.indexOf(note)] = updatedNote
                    return true
                }
            }
        }
        return false
    }

    fun editNote(note: Note): Boolean {
        for (n: Note in deletedNotes) {
            if (n.id == note.id) {
                println("Данная заметка удалена и не может быть отредактирована!")
                return false
            }
        }

        for (n: Note in notes) {
            val noteIndexInList = notes.indexOf(n)

            if (n.id != note.id) {
                println("Заметки с Id = ${note.id} не существует!!")
                return false
            }

            if (n.id == note.id) {
                notes[noteIndexInList] = note.copy(
                    id = n.id,
                    comments = n.comments,
                    deletedComments = n.deletedComments
                )
                return true
            }
        }
        return false
    }

    fun editComment(comment: Comment): Boolean {
        for (note: Note in notes) {
            for (comm: Comment in note.deletedComments) {
                if (comm.id == comment.id) {
                    println("Данный комментарий удален и не может быть отредактирован!")
                    return false
                }
            }
        }

        for (note: Note in deletedNotes) {
            for (comm: Comment in note.comments) {
                if (comment.id == comm.id) {
                    println("Заметка с комментарием (Id = ${comment.id}) была удалена!!")
                    return false
                }
            }
        }

        for (note: Note in notes) {
            val tempListOfComments = note.comments.toMutableList()

            for (comm: Comment in note.comments) {

                if (comm.id == comment.id) {
                    tempListOfComments[note.comments.indexOf(comm)] = comment.copy(
                        id = comm.id,
                        noteId = comm.noteId,
                    )
                    notes[notes.indexOf(note)] = note.copy(comments = tempListOfComments)
                    return true
                }
            }
        }
        return false
    }

    fun restoreComment(commentId: Int): Boolean {
        for (note: Note in deletedNotes) {
            for (comm: Comment in note.comments) {
                if (commentId == comm.id) {
                    println("Комментарий с Id = $commentId удален вместе с заметкой, восстановление невозможно!")
                    return false
                }
            }
        }
        for (note: Note in notes) {
            for (comm: Comment in note.comments) {
                if(comm.id == commentId) {
                    println("Данный комментарий не был удален, восстановление невозможно!!")
                }
            }
        }

            for (note: Note in notes) {

                for (comm: Comment in note.deletedComments) {

                    if (commentId == comm.id) {
                        val tempListOfComments = note.comments.toMutableList()
                        val tempListOfDeletedComments = note.deletedComments.toMutableList()
                        tempListOfDeletedComments.remove(comm)
                        tempListOfComments.plusAssign(comm)
                        val updatedNote = note.copy(
                            comments = tempListOfComments,
                            deletedComments = tempListOfDeletedComments
                        )
                        notes[notes.indexOf(note)] = updatedNote
                        return true
                    }
                }
        }
        return false
    }

    fun getNotes(sortMethod: Int) {

        when(sortMethod) {
            1 -> {
                println("Список активных заметок отсортированный по возрастанию:")
                val sortedAscending = notes.sortedBy { it.date }
                for ((index, value) in sortedAscending.withIndex()) {
                    println("\t${index + 1}. $value")
                }
            }
            0 -> {
                println("Список активных заметок отсортированный по убыванию:")
                val sortedDescending = notes.sortedBy { it.date.inv() }
                for ((index, value) in sortedDescending.withIndex()) {
                    println("\t${index + 1}. $value")
                }
            }
        }
    }

    fun getNoteById(noteId: Int) {
        for (note: Note in deletedNotes) {
            if (note.id == noteId) {
                println("Заметка с Id = $noteId была удалена!!")
            }
        }
        for (note: Note in notes) {
            if (note.id == noteId) {
                println(note.toString())
            }
        }
    }

    fun getComments(noteId: Int, sortMethod: Int) {

        for (note: Note in deletedNotes) {
            if (note.id == noteId) {
                println("Заметка с Id = $noteId была удалена!!")
            }
        }

        for (note: Note in notes) {
            if (noteId == note.id) {

                when(sortMethod) {
                    0 -> {
                        val sortedAscending = note.comments.sortedBy { it.date }
                        println("Список комментариев к данной заметке отсортированный по возрастанию:")
                        for ((index, value) in sortedAscending.withIndex()) {
                            println("\t${index + 1}. $value")
                        }
                    }

                    1 -> {
                        val sortedDescending = note.comments.sortedBy { it.date.inv() }
                        println("Список комментариев к данной заметке отсортированный по убыванию:")
                        for ((index, value) in sortedDescending.withIndex()) {
                            println("\t${index + 1}. $value")
                        }
                    }
                }
            }
        }
    }

    fun printDeletedNotes() {
        println("\nСписок удаленных заметок:")
        for ((index, value) in deletedNotes.withIndex()) {
            println("\t${index + 1}. $value")
        }
    }

}
