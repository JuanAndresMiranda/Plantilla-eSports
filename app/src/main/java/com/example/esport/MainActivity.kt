package com.example.esport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esport.ui.theme.Equipo
import com.example.esport.ui.theme.Orbitron
import com.example.esport.ui.theme.Roboto

val Fondo = Color(0xFF0B0B0F)
val Equipo = Color(0xFF7C3AED)
val Separador = Color(0xFFA855F7)
val Nombre = Color(0xFFE8F7FF)
val Descripcion = Color(0xFFB8C7D1)
val Ficha = Color(0xCCEC4899)

data class Jugador(
    val id: Int,
    val nombre: String,
    val nickname: String,
    val rol: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { Plantilla() }
    }
}

@Composable
fun Plantilla() {
    val jugadores = remember {
        mutableStateListOf(
            Jugador(1, "Alex", "AlexPro", "Atacante"),
            Jugador(2, "Sergio", "ShadowX", "Defensor"),
            Jugador(3, "Marta", "MartaGG", "Soporte"),
            Jugador(4, "David", "Destroyer", "Atacante"),
            Jugador(5, "Laura", "Nova", "Estratega")
        )
    }
    var siguienteId by rememberSaveable { mutableIntStateOf(6) }
    var aEliminar by remember { mutableStateOf<Jugador?>(null) }
    var aEditar by remember { mutableStateOf<Jugador?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding().background(Fondo).padding(20.dp)
    ) {
        Text(
            text = "TEAM TITANS",
            color = com.example.esport.Equipo,
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = Equipo,
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
        )
        Divisor()
        Formulario { n, nick, r ->
            jugadores.add(Jugador(siguienteId++, n, nick, r))
        }
        Divisor()

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(jugadores, key = { it.id }) { j ->
                FichaJugador(j, onEditar = { aEditar = j }, onEliminar = { aEliminar = j })
            }
        }
    }

    aEliminar?.let { j ->
        AlertDialog(
            onDismissRequest = { aEliminar = null },
            containerColor = Fondo,
            titleContentColor = Nombre,
            textContentColor = Descripcion,
            shape = RoundedCornerShape(15.dp),
            title = { Text("Eliminar jugador", fontFamily = Orbitron) },
            text = {
                Text("¿Estás seguro de que deseas eliminar a ${j.nickname}?", fontFamily = Roboto)
            },
            confirmButton = {
                TextButton(onClick = { jugadores.remove(j); aEliminar = null }) {
                    Text("Eliminar", color = Separador)
                }
            },
            dismissButton = {
                TextButton(onClick = { aEliminar = null }) {
                    Text("Cancelar", color = Descripcion)
                }
            }
        )
    }

    aEditar?.let { j ->
        EditarDialog(
            jugador = j,
            onGuardar = { editado ->
                val i = jugadores.indexOfFirst { it.id == j.id }
                if (i >= 0) jugadores[i] = editado
                aEditar = null
            },
            onCancelar = { aEditar = null }
        )
    }
}

@Composable
fun Formulario(onAnadir: (String, String, String) -> Unit) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var rol by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Campo("Nombre", nombre) { nombre = it }
        Campo("Nickname", nickname) { nickname = it }
        Campo("Rol", rol) { rol = it }
        if (error) Text("Todos los campos son obligatorios", color = Color(0xFFFF6B6B))
        Button(
            onClick = {
                if (nombre.isBlank() || nickname.isBlank() || rol.isBlank()) {
                    error = true
                } else {
                    onAnadir(nombre.trim(), nickname.trim(), rol.trim())
                    nombre = ""; nickname = ""; rol = ""; error = false
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = com.example.esport.Equipo)
        ) { Text("Añadir jugador") }
    }
}

@Composable
fun EditarDialog(jugador: Jugador, onGuardar: (Jugador) -> Unit, onCancelar: () -> Unit) {
    var nombre by rememberSaveable { mutableStateOf(jugador.nombre) }
    var nickname by rememberSaveable { mutableStateOf(jugador.nickname) }
    var rol by rememberSaveable { mutableStateOf(jugador.rol) }
    var error by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancelar,
        containerColor = Fondo,
        titleContentColor = Nombre,
        shape = RoundedCornerShape(15.dp),
        title = { Text("Editar jugador", fontFamily = Orbitron) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Campo("Nombre", nombre) { nombre = it }
                Campo("Nickname", nickname) { nickname = it }
                Campo("Rol", rol) { rol = it }
                if (error) Text("Todos los campos son obligatorios", color = Color(0xFFFF6B6B))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (nombre.isBlank() || nickname.isBlank() || rol.isBlank()) error = true
                else onGuardar(jugador.copy(nombre = nombre.trim(), nickname = nickname.trim(), rol = rol.trim()))
            }) { Text("Guardar", color = Separador) }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) { Text("Cancelar", color = Descripcion) }
        }
    )
}
@Composable
fun Campo(
    etiqueta: String,
    valor: String,
    colorTexto: Color = Nombre,
    onCambio: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = colorTexto,
            unfocusedTextColor = colorTexto,
            focusedBorderColor = Separador,
            unfocusedBorderColor = Descripcion,
            focusedLabelColor = Separador,
            unfocusedLabelColor = Descripcion
        )
    )
}

@Composable
fun FichaJugador(jugador: Jugador, onEditar: () -> Unit, onEliminar: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(Ficha, RoundedCornerShape(15.dp))
            .padding(16.dp)
    ) {
        Text(jugador.nickname.uppercase(), fontFamily = Orbitron, color = Nombre)
        Text("${jugador.nombre} · ${jugador.rol}", fontFamily = Roboto, color = Descripcion)
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onEditar) { Text("Editar", color = Nombre) }
            TextButton(onClick = onEliminar) { Text("Eliminar", color = Nombre) }
        }
    }
}

@Composable
fun Divisor() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 12.dp),
        thickness = 5.dp,
        color = Separador
    )
}

@Preview(showBackground = true)
@Composable
fun PlantillaPreview() {
    Plantilla()
}
