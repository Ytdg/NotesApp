package com.example.notesappapi.feature.domain.model

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date

object ConstColorNote {
    val defaultColorNote = GetRGBColorInt(242, 156, 152)
    val colorsInt = listOf<Color>(
        GetRGBColorInt(252, 54, 68),
        GetRGBColorInt(169, 144, 221),
        GetRGBColorInt(254, 203, 77),
        defaultColorNote,
        GetRGBColorInt(250, 78, 133),
        GetRGBColorInt(137, 98, 248)
    )
}

private fun GetRGBColorInt(r: Int, g: Int, b: Int): Color {
    return Color(r, g, b)
}
@Entity
data class Note(
    val timeSave:Long=System.currentTimeMillis(),
    val title: String,
    val color: Int,
    val dateCreating: String = Data.currentDate,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
)
@Entity(
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = ["id"],
        childColumns = ["parentId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class NoteContent(
    val text: String?=null,
    val typeContent:TypeContent,
    val parentId: Int,
    val duration:String?=null,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)
enum class TypeContent{
    audio,image,text,none
}
object Data {
    @SuppressLint("SimpleDateFormat")
    private val settingFormat = SimpleDateFormat("dd MMMM yyyy")
    val currentDate: String = settingFormat.format(Date())
}

