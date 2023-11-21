package com.example.sensory

import android.app.Application
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sensory.ui.theme.SensoryTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen" ){
        composable("splash_screen"){
            SplashScreen(navController)
        }
        composable("main_screen"){
            MainScreen(navController)
        }
        composable("e_sensor1"){
            Sensor1(navController)
        }
        composable("e_sensor2")
        {
            Sensor2(navController)
        }
        composable("e_sensor3")
        {
            Sensor3(navController)
        }
        composable("e_sensor4")
        {
            Sensor4(navController)
        }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.navigate("main_screen")
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(Modifier.height(200.dp)){
            Image(painter = painterResource(id = R.drawable.baseline_sensor_occupied_24), contentDescription = "Logo", Modifier.size(195.dp))
        }
        Spacer(Modifier.height(65.dp))
        Text(text = "Autor: Weronika Rydz", fontSize = 25.sp, color = Color.White)
        Text(text = "Temat: Sensory", fontSize = 25.sp, color = Color.White)
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedButton(onClick = {
                navController.navigate("e_sensor1")
            }) {
                Image(
                    painterResource(id = R.drawable.baseline_sensor_occupied_24),
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kliknij Mnie1", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                navController.navigate("e_sensor2")
            }) {
                Image(
                    painterResource(id = R.drawable.baseline_sensor_occupied_24),
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kliknij Mnie2", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            OutlinedButton(onClick = {
                navController.navigate("e_sensor3")
            }) {
                Image(
                    painterResource(id = R.drawable.baseline_sensor_occupied_24),
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kliknij Mnie3", color = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = {
                navController.navigate("e_sensor4")
            }) {
                Image(
                    painterResource(id = R.drawable.baseline_sensor_occupied_24),
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kliknij Mnie4", color = Color.Black)
            }
        }
    }
}

class SensorViewModel(application: Context) : AndroidViewModel(application as Application), SensorEventListener {
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // MutableState przechowujący dane z sensora
    private val _sensorData = mutableStateOf("Brak danych z sensora")
    val sensorData: State<String> = _sensorData

    init {
        // Rejestracja odbiornika sensora
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // Obsługa zdarzenia odczytu z sensora
    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        // Aktualizacja MutableState z danymi z sensora
        _sensorData.value = "X: $x, Y: $y, Z: $z"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Obsługa zmiany dokładności sensora (opcjonalne)
    }

    override fun onCleared() {
        // Anulowanie subskrypcji przy zniszczeniu ViewModel
        sensorManager.unregisterListener(this)
    }
}

@Composable
fun Sensor1(navController: NavHostController, viewModel: SensorViewModel) {
    Text(text = "Akcelerometr ${viewModel.sensorData.value}", color = Color.Black)
}

@Composable
fun Sensor2(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun Sensor3(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Composable
fun Sensor4(navController: NavHostController) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SensoryTheme {
        Greeting()
    }
}