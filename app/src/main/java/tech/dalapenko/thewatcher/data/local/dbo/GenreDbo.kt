package tech.dalapenko.thewatcher.data.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreDbo(@PrimaryKey val id: Int)