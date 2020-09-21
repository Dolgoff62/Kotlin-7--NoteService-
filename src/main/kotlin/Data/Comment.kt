package ru.netology.Data

data class Comment(
    val id: Int,
    val noteId: Int,
    val date: Int,
    val text: String,
) {
    override fun toString(): String {
        return "id= $id, noteId= $noteId, date= $date ,text= $text"
    }
}