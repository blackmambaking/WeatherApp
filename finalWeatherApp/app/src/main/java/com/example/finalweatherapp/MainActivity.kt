package com.example.finalweatherapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.finalweatherapp.ui.theme.FinalWeatherAppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
//
//class MainActivity : ComponentActivity() {
//
//    private val CITY: String = "dhaka,bd"
//    private val API: String = "09c02610bf1a5dee667fabf65480d078"
//
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private var latitude by mutableStateOf("")
//    private var longitude by mutableStateOf("")
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//            getLocation()
//            PolishedUI()
//
//            fetchWeatherData()
//
//        }
//    }
//
//
//    private fun getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location != null) {
//                    latitude = location.latitude.toString()
//                    longitude = location.longitude.toString()
//                    CoroutineScope(Dispatchers.Main).launch {
//                    Toast.makeText(this@MainActivity, "Success!!", Toast.LENGTH_SHORT).show()
//                        // Update your UI here with the fetched data
//                    }                } else {
//                    Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    private fun fetchWeatherData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = URL("https://api.openweathermap.org/data/3.0/onecall/day_summary?lat=$latitude&lon=$longitude&date=2020-03-04&appid=$API").readText(
//                    Charsets.UTF_8
//                )
//                val jsonObj = JSONObject(response)
//                val temperature = jsonObj.getJSONObject("temperature")
//                val tempMin = "Min: " + temperature.getString("min") + "°C"
//                val tempMax = "Max: " + temperature.getString("max") + "°C"
//
//                // Update UI on the main thread
//                CoroutineScope(Dispatchers.Main).launch {
////                    Toast.makeText(this@MainActivity, "Success!!", Toast.LENGTH_SHORT).show()
//                    // Update your UI here with the fetched data
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Handle error, show error message or retry mechanism
//            }
//        }
//    }
//    @Composable
//    fun PolishedUI() {
//        BoxWithConstraints(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val maxWidth = constraints.maxWidth.toFloat()
//            val maxHeight = constraints.maxHeight.toFloat()
//
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.back),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    contentScale = ContentScale.FillBounds
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = "Weather",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//
//                    var location by remember { mutableStateOf("Location") }
//                    var maxTemp by remember { mutableStateOf("Max") }
//                    var minTemp by remember { mutableStateOf("Min") }
//                    var selectedDate by remember { mutableStateOf(Date()) }
//
//                    Text(text = "Location: $location", fontSize = 20.sp, color = Color.White)
//
//                    Surface(
//                        color = Color.White,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
//                        shape = MaterialTheme.shapes.medium
//                    ) {
//                        DatePicker(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Surface(
//                            color = Color.Transparent,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f),
//                            shape = MaterialTheme.shapes.medium
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(8.dp),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    text = "Max Temp",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text(
//                                    text = maxTemp,
//                                    fontSize = 32.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
//
//                        Surface(
//                            color = Color.Transparent,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f),
//                            shape = MaterialTheme.shapes.medium
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(8.dp),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    text = "Min Temp",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text(
//                                    text = minTemp,
//                                    fontSize = 32.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun DatePicker(
//        selectedDate: Date,
//        onDateSelected: (Date) -> Unit
//    ) {
//        val calendar = Calendar.getInstance()
//        calendar.time = selectedDate
//
//        val year = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
//        val month = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
//        val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
//
//        Column(
//            modifier = Modifier
//                .padding(8.dp)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Text("Select Date", fontSize = 20.sp, color = Color.Black)
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.toggleable(
//                    value = false,
//                    onValueChange = { }
//                )
//            ) {
//                OutlinedTextField(
//                    value = year.value.toString(),
//                    onValueChange = { year.value = it.toIntOrNull() ?: year.value },
//                    label = { Text("Year", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = month.value.toString(),
//                    onValueChange = { month.value = it.toIntOrNull() ?: month.value },
//                    label = { Text("Month", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = day.value.toString(),
//                    onValueChange = { day.value = it.toIntOrNull() ?: day.value },
//                    label = { Text("Day", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//            }
//
//            Button(
//                onClick = {
//                    calendar.set(Calendar.YEAR, year.value)
//                    calendar.set(Calendar.MONTH, month.value)
//                    calendar.set(Calendar.DAY_OF_MONTH, day.value)
//                    onDateSelected(calendar.time)
//                },
//                modifier = Modifier.align(Alignment.End)
//
//            ) {
//                Text("Select", color = Color.Black)
//            }
//        }
//    }
//}
//
//class MainActivity : ComponentActivity() {
//
//    private val CITY: String = "dhaka,bd"
//    private val API: String = "09c02610bf1a5dee667fabf65480d078"
//
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    private var latitude by mutableStateOf("")
//    private var longitude by mutableStateOf("")
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//            getLocation()
//            PolishedUI()
//        }
//    }
//
//
//    private fun getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location != null) {
//                    latitude = location.latitude.toString()
//                    longitude = location.longitude.toString()
//                    CoroutineScope(Dispatchers.Main).launch {
//                        Toast.makeText(this@MainActivity, "Success!!", Toast.LENGTH_SHORT).show()
//                        // Update your UI here with the fetched data
//                    }
//                } else {
//                    Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .addOnFailureListener { e ->
//                Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    private fun fetchWeatherData(selectedDate: Date) {
//        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = URL("https://api.openweathermap.org/data/3.0/onecall/day_summary?lat=$latitude&lon=$longitude&date=$formattedDate&appid=$API").readText(
//                    Charsets.UTF_8
//                )
//                val jsonObj = JSONObject(response)
//                val temperature = jsonObj.getJSONObject("temperature")
//                val tempMin = "Min: " + temperature.getString("min") + "°C"
//                val tempMax = "Max: " + temperature.getString("max") + "°C"
//
//                // Update UI on the main thread
//                CoroutineScope(Dispatchers.Main).launch {
//                    // Update your UI here with the fetched data
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                // Handle error, show error message or retry mechanism
//            }
//        }
//    }
//
//    @Composable
//    fun PolishedUI() {
//        BoxWithConstraints(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            val maxWidth = constraints.maxWidth.toFloat()
//            val maxHeight = constraints.maxHeight.toFloat()
//
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.back),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    contentScale = ContentScale.FillBounds
//                )
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = "Weather",
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//
//                    var location by remember { mutableStateOf("Location") }
//                    var maxTemp by remember { mutableStateOf("Max") }
//                    var minTemp by remember { mutableStateOf("Min") }
//                    var selectedDate by remember { mutableStateOf(Date()) }
//
//                    Text(text = "Location: $location", fontSize = 20.sp, color = Color.White)
//
//                    Surface(
//                        color = Color.White,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
//                        shape = MaterialTheme.shapes.medium
//                    ) {
//                        DatePicker(selectedDate = selectedDate, onDateSelected = { newDate ->
//                            selectedDate = newDate
//                            fetchWeatherData(selectedDate)
//                        })
//                    }
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Surface(
//                            color = Color.Transparent,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f),
//                            shape = MaterialTheme.shapes.medium
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(8.dp),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    text = "Max Temp",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text(
//                                    text = maxTemp,
//                                    fontSize = 32.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
//
//                        Surface(
//                            color = Color.Transparent,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .weight(1f),
//                            shape = MaterialTheme.shapes.medium
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(8.dp),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Text(
//                                    text = "Min Temp",
//                                    fontSize = 20.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    color = Color.White
//                                )
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text(
//                                    text = minTemp,
//                                    fontSize = 32.sp,
//                                    color = Color.White
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun DatePicker(
//        selectedDate: Date,
//        onDateSelected: (Date) -> Unit
//    ) {
//        val calendar = Calendar.getInstance()
//        calendar.time = selectedDate
//
//        val year = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
//        val month = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
//        val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }
//
//        Column(
//            modifier = Modifier
//                .padding(8.dp)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Text("Select Date", fontSize = 20.sp, color = Color.Black)
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.toggleable(
//                    value = false,
//                    onValueChange = { }
//                )
//            ) {
//                OutlinedTextField(
//                    value = year.value.toString(),
//                    onValueChange = { year.value = it.toIntOrNull() ?: year.value },
//                    label = { Text("Year", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = month.value.toString(),
//                    onValueChange = { month.value = it.toIntOrNull() ?: month.value },
//                    label = { Text("Month", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                OutlinedTextField(
//                    value = day.value.toString(),
//                    onValueChange = { day.value = it.toIntOrNull() ?: day.value },
//                    label = { Text("Day", color = Color.Black) },
//                    modifier = Modifier.weight(1f)
//                )
//            }
//
//            Button(
//                onClick = {
//                    calendar.set(Calendar.YEAR, year.value)
//                    calendar.set(Calendar.MONTH, month.value)
//                    calendar.set(Calendar.DAY_OF_MONTH, day.value)
//                    onDateSelected(calendar.time)
//                },
//                modifier = Modifier.align(Alignment.End)
//
//            ) {
//                Text("Select", color = Color.Black)
//            }
//        }
//    }
//}


class MainActivity : ComponentActivity() {

    private val CITY: String = "dhaka,bd"
    private val API: String = "b23a65505e9a407c95d8eaab57e778d7"

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude by mutableStateOf("")
    private var longitude by mutableStateOf("")
    private var maxTemp by mutableStateOf("Max")
    private var minTemp by mutableStateOf("Min")


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            getLocation()
            PolishedUI()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePicker(
        selectedDate: Date,
        onDateSelected: (Date) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate

        val year = remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
        val month = remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
        val day = remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Select Date", fontSize = 20.sp, color = Color.Black)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.toggleable(
                    value = false,
                    onValueChange = { }
                )
            ) {
                OutlinedTextField(
                    value = year.value.toString(),
                    onValueChange = { newValue ->
                        year.value = newValue.toIntOrNull() ?: calendar.get(Calendar.YEAR)
                    },
                    label = { Text("Year", color = Color.Black) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = (month.value + 1).toString(),
                    onValueChange = { newValue ->
                        val parsedValue = newValue.toIntOrNull()
                        if (parsedValue != null && parsedValue in 1..12) {
                            month.value = parsedValue - 1
                        }
                    },
                    label = { Text("Month", color = Color.Black) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = day.value.toString(),
                    onValueChange = { newValue ->
                        val parsedValue = newValue.toIntOrNull()
                        if (parsedValue != null && parsedValue in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            day.value = parsedValue
                        }
                    },
                    label = { Text("Day", color = Color.Black) },
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = {
                    calendar.set(Calendar.YEAR, year.value)
                    calendar.set(Calendar.MONTH, month.value)
                    calendar.set(Calendar.DAY_OF_MONTH, day.value)
                    onDateSelected(calendar.time)
                },
                modifier = Modifier.align(Alignment.End)

            ) {
                Text("Select", color = Color.Black)
            }
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                } else {
                    Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchWeatherData(selectedDate: Date) {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(this@MainActivity, formattedDate, Toast.LENGTH_SHORT).show()
                    // Update your UI here with the fetched data
                }
                // Assuming formattedDate represents the start date
                val startDate = LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE)

// Calculate the end date by adding one day to the start date
                val endDate = startDate.plusDays(1)

// Format the end date to match the required format (assuming ISO_DATE format)
                val formattedEndDate = endDate.format(DateTimeFormatter.ISO_DATE)

// Update the API request with the new end date
                val response = URL("https://api.weatherbit.io/v2.0/history/daily?lat=$latitude&lon=$longitude&start_date=$formattedDate&end_date=$formattedEndDate&key=$API").readText(Charsets.UTF_8)
                val jsonObj = JSONObject(response)
                val dataArray = jsonObj.getJSONArray("data")

// Assuming you want to retrieve max and min temperature from the first entry of data array
                val firstDataObject = dataArray.getJSONObject(0)
                val tempMin = firstDataObject.getDouble("min_temp")
                val tempMax = firstDataObject.getDouble("max_temp")

                // Update UI on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    minTemp = "Min: $tempMin°C"
                    maxTemp = "Max: $tempMax°C"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error, show error message or retry mechanism
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun PolishedUI() {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val maxWidth = constraints.maxWidth.toFloat()
            val maxHeight = constraints.maxHeight.toFloat()

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Weather",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    var location by remember { mutableStateOf("Location") }
                    var selectedDate by remember { mutableStateOf(Date()) }

                    Text(text = "Location: $location", fontSize = 20.sp, color = Color.White)

                    Surface(
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        DatePicker(selectedDate = selectedDate, onDateSelected = { newDate ->
                            selectedDate = newDate
                            fetchWeatherData(selectedDate)
                        })
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = maxTemp,
                                    fontSize = 32.sp,
                                    color = Color.White
                                )
                            }
                        }

                        Surface(
                            color = Color.Transparent,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = minTemp,
                                    fontSize = 32.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
