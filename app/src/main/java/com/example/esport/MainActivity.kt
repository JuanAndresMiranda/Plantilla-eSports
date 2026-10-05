package com.example.esport

import android.media.audiofx.AudioEffect
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.esport.ui.theme.ESportTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Plantilla()
        }
    }
}
@Composable
fun Plantilla(){
    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        Text(
            text = "TEAM TITANS",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
        )
        Jugador("AlexPro", "Atacante", 21, 87, R.drawable.fotoalex)
        Divisor()
        Jugador("ShadowX","Defensor",24,91, R.drawable.fotoshadow)
        Divisor()
        Jugador("MartaGG","Soporte",20,84, R.drawable.fotomarta)
        Divisor()
        Jugador("Destroyer","Atacante",23,89, R.drawable.fotodestroyer)
    }
}

@Preview(showBackground = true)
@Composable
fun PlantillaPreview(){
    Plantilla()
}


@Composable
fun Jugador(
    nombre: String,
    rol: String,
    edad: Int,
    nivel: Int,
    @DrawableRes imagen: Int
) {
    Row( //que todo sea una unica row hace que no se ponga la imagen encima del texto
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Box{
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "Foto de $nombre",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp).clip(CircleShape)
            )
        }
            Column(){
                Descripcion(nombre,rol,edad,nivel)
             }
    }


}

@Composable
fun Descripcion(nombre: String, rol: String, edad: Int, nivel: Int){
    Row(
        modifier = Modifier.fillMaxWidth(),//necesario para centar el texto en el espacio que queda
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center//centra el texto
    ) {
        Text(nombre.uppercase(),
            modifier = Modifier.padding(vertical = 2.dp),
            style = MaterialTheme.typography.titleMedium)
    }
    Row (
        modifier = Modifier.fillMaxWidth(),//necesario para centar el texto en el espacio que queda
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center//centra el texto
    ) {
        Text("Mi rol es $rol, tengo $edad y soy nivel $nivel")
    }
}
@Composable
fun Divisor(){
    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp),
        thickness = 20.dp,
        color =  Color.Gray)
}
@Preview(showBackground = true)
@Composable
fun JugadorPreview(){

    Jugador("AlexPro", "Atacante", 21, 87, R.drawable.fotoalex)

}
