package ru.healthy

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.preferredHeightIn
import androidx.compose.foundation.layout.Box
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@androidx.compose.runtime.Composable
fun myHelp(model: MyViewModel) {
    ScrollableColumn {
        Box(modifier = mod_card {}) {
            Column(modifier = mod_padd) {
                //Text("Как это работает", style = typography.body1)
                Box(modifier = mod_list()) {
                    Row {
                        Column(modifier = mod_padd) {
                            Text("Программа лишь выполняет запросы в Регистратуры (к серверам поликлиник) и отображает их ответы.", style = typography.body2)
                            Text("\nКлиники ведут себя по-разному: некоторые включают запись в 8:00 и выключают в 20:00, стоматологии часто показывают талоны информационно, без возможности записи и т.п.", style = typography.body2)
                            Text("\nОтложенные ранее талоны видны по нажатию иконки \"Календарь\" внутри \"Выбрать специальность\". Нажмите на талон, чтобы отменить.", style = typography.body2)
                            Text("\nЗапись возможна, лишь когда ФИО и дата совпадают с данными карточки, ранее заведенной в Регистратуре выбранной поликлиники. Иногда нужно сходить/позвонить и свериться.", style = typography.body2)
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.clickable(onClick = { model.isAdmin = !model.isAdmin })) {
            Text("              ")
        }
    }
}

@Composable
fun myBar(model: MyViewModel) {
    TopAppBar(
        title = { Text("${model.currentState.value}", maxLines = 1) },
        navigationIcon = {
            IconButton(onClick = { model.currentState.postValue("Выбрать пациента") }) {
                androidx.compose.material.Icon(Icons.Filled.Person)
            }
        },
        actions = {
            IconButton(onClick = { model.currentState.postValue("Информация") }) {
                androidx.compose.material.Icon(Icons.Filled.Info)
            }
        }
    )
}

@Composable
fun myFab(model: MyViewModel) {
    if (model.currentState.value.equals("Выбрать пациента")) {
        FloatingActionButton(
            onClick = {
                val newuser = model.createUser()
                model.addUser(newuser)
                model.currentUsr = newuser
                model.currentState.postValue("Изменить")
                //DataLoader().execute()
            }
        ) {
            androidx.compose.material.Icon(Icons.Filled.Add)
        }
    } else if (model.currentState.value.equals("Выбрать специальность")) {
        FloatingActionButton(onClick = { model.currentState.postValue("Отложенные талоны") }) {
            androidx.compose.material.Icon(Icons.Filled.DateRange)
        }
    }
}

@Composable
fun showCurrentInfo(model: MyViewModel) {
    //Box() {
    Column(modifier = mod_padd) {
        if (!model.currentUsr["lastError"].isNullOrEmpty()) {
            Box(modifier = err_info()) {
                Column(modifier = mod_padd) {
                    Text("${model.currentUsr["lastError"]}", style = typography.overline)
                }
            }
            Spacer(modifier = Modifier.preferredHeightIn(padd))
        }
        Text(model.infoString, style = typography.body1)
    }
}
