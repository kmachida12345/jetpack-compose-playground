package com.github.kmachida12345.jetpackcomposeplayground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.github.kmachida12345.jetpackcomposeplayground.ui.theme.JetpackComposePlaygroundTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposePlaygroundTheme {


                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Page title", maxLines = 2)
                            },
                            navigationIcon = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(Icons.Filled.Home, contentDescription = null)
                                }

                            },
                            actions = {
                                IconButton(onClick = { /* doSomething() */ }) {
                                    Icon(Icons.Filled.Favorite, contentDescription = null)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        Surface(color = MaterialTheme.colors.background) {
                            Greeting(name = "Android")
                        }

                        PhotographerCard()

                        SimpleList()


                    }
                }
            }
        }
    }

}


@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(
        modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colors.primary)

            .clickable {
                Log.d("machida", "PhotographerCard: hogehoge")
            }
            .padding(120.dp)

    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {


        }
        Column(
            modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("Alfred Sisley", fontWeight = FontWeight.Bold)
            // LocalContentAlpha is defining opacity level of its children
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    JetpackComposePlaygroundTheme {
        PhotographerCard()
    }
}


@Composable
fun Greeting(modifier: Modifier = Modifier, name: String) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.abc_vector_test),
            contentDescription = "test desu"
        )
        Column {
            Text(text = "Hello $name!")
            Text(text = "hogehoge!!")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposePlaygroundTheme {
        Greeting(name = "Android")
    }
}

@Composable
fun SimpleList() {
    val listSize = 100
    // We save the scrolling position with this state
    val scrollState = rememberLazyListState()
    // We save the coroutine scope where our animated scroll will be executed
    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {

                Text("Scroll to the top")

            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }

        }
        LazyColumn(state = scrollState) {
            items(100) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        Log.d(
                            "machida",
                            "SimpleList: list item $it clicked."
                        )
                    }
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = "https://developer.android.com/images/brand/Android_Robot.png"
                        ),
                        contentDescription = "thumbnail desu",
                        modifier = Modifier.size(50.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Item #$it", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }

}