package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true) //autogenerate para o id
    val id: String,
    val nome: String,
    val senha: String
)
