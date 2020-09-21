package ru.netology.Data

data class Note(
    val id: Int = 1,
    val ownerId: Int,
    val title: String,
    val text: String,
    val date: Int,
    val comments: List<Comment>,
    val deletedComments: List<Comment>,
) {
    override fun toString(): String {
        return "id= $id, ownerId= $ownerId, title= $title, text= $text, date= $date, comments= $comments," +
                "deleted comments = $deletedComments"
    }
}