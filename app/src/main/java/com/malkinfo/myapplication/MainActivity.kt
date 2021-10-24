package com.malkinfo.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.malkinfo.myapplication.ui.theme.TabColorOne
import com.malkinfo.myapplication.ui.theme.TabLayoutSTheme
import com.malkinfo.myapplication.view.TabScreenOne
import com.malkinfo.myapplication.view.TabScreenThree
import com.malkinfo.myapplication.view.TabScreenTwo
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TabLayoutSTheme {
                // A surface container using the 'background' color from the theme
               Column {
                   Toolbar()
                   TabScreen()
               }
            }
        }
    }
}


@Composable
fun Toolbar(){
    TopAppBar(
        title = { Text(text = "TabLayout",color = Color.White)},
        backgroundColor = TabColorOne,
        navigationIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                tint = Color.White
            )
        }
    )
}

@ExperimentalPagerApi
@Composable
fun TabScreen(){
    val pagerState = rememberPagerState(pageCount = 3)
    
    Column (
        modifier = Modifier.background(Color.White)
            )
    {
        Tabs(pagerState= pagerState)
        TabsContent(pagerState = pagerState)
        
    }
    
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {

    val list = listOf("Home", "Market","Films")
    val lists = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.AccountBox

    )
    val scope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage,
        backgroundColor = TabColorOne,
        contentColor = Color.White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.White
            )
        },
        indicator = { tabPositions ->
        TabRowDefaults.Indicator(
            Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
            height = 3.dp,
            color = Color.Red
        )

        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(text = list[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index ,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(imageVector = lists[index], contentDescription = null)
                }
            )
        }

    }



}


@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState) {

    HorizontalPager(state = pagerState) { page ->
        when (page){
            0->TabScreenOne(tabName = "This is a Home Tab Layout")
            1-> TabScreenTwo(tabName = "This is a Market Tab Layout")
            2-> TabScreenThree(tabName = "This is a Films Tab Layout")
        }

    }

}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TabLayoutSTheme {
        Greeting("Android")
    }
}