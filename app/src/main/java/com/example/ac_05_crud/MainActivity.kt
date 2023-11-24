package com.example.ac_05_crud

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.ac_05_crud.ui.theme.Ac_05_crudTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ac_05_crudTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var dbHelper = TaskDBHelper(this)
                    TelaOpcoes(dbHelper)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaOpcoes(db: TaskDBHelper?) {
//Variaveis de controle de tela
    var opcao by remember {
        mutableStateOf("")
    }
    var buscar by remember {
        mutableStateOf(false)
    }
    var inserir by remember {
        mutableStateOf(false)
    }
    var atualizar by remember {
        mutableStateOf(false)
    }
    var deletar by remember {
        mutableStateOf(false)
    }
    var telaOpcoes by remember {
        mutableStateOf(true)
    }

    if (telaOpcoes) {
        //Criando um surface para preencher a tela inteira (Tela de fundo)
        Surface(
            color = Color(0xFF006064),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
//Definindo o aplicativo para deixar os elementos em coluna (um de baixo do outro)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Capturar Dados de Cadastro",
                    color = Color.White,
                    fontSize = 23.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(40.dp))


                Row {
                    OutlinedButton(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
                            opcao = "buscar"
                            telaOpcoes = false
                            buscar = false
                            inserir = false
                            atualizar = false
                            deletar = false
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text(
                            "BUSCAR DADOS",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,
                            style = TextStyle(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedButton(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
                            opcao = "inserir"
                            telaOpcoes = false
                            buscar = false
                            inserir = true
                            atualizar = false
                            deletar = false
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text(
                            "INSERIR DADOS",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,
                            style = TextStyle(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(4.dp))

                Row {
                    OutlinedButton(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
                            opcao = "atualizar"
                            telaOpcoes = false
                            buscar = false
                            inserir = false
                            atualizar = false
                            deletar = false
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text(
                            "ATUALIZAR DADOS",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,
                            style = TextStyle(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedButton(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
                            opcao = "deletar"
                            telaOpcoes = false
                            buscar = false
                            inserir = false
                            atualizar = false
                            deletar = false
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text(
                            "DELETAR DADOS",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.SansSerif,
                            style = TextStyle(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    } else {
        if (opcao == "buscar") {
            Tela(db, opcao, buscar, inserir, atualizar, deletar) {
                telaOpcoes = it
            }
        }
        if (opcao == "inserir") {
            Tela(db, opcao, buscar, inserir, atualizar, deletar) {
                telaOpcoes = it
            }
        }
        if (opcao == "atualizar") {
            Tela(db, opcao, buscar, inserir, atualizar, deletar) {
                telaOpcoes = it
            }
        }
        if (opcao == "deletar") {
            Tela(db, opcao, buscar, inserir, atualizar, deletar) {
                telaOpcoes = it
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela(
    db: TaskDBHelper?,
    opcao: String,
    buscar: Boolean,
    inserir: Boolean,
    atualizar: Boolean,
    deletar: Boolean,
    //telaInicial: Boolean,
    trataRetorno: (Boolean) -> Unit
) {
//Criando variaveis para controle das TextFields
    var value by remember {
        mutableStateOf("")
    }
    var idToShow by remember {
        mutableStateOf(0)
    }
    var id = 0

    var nome by remember {
        mutableStateOf("")
    }
    var cep by remember {
        mutableStateOf("")
    }
    var endereco by remember {
        mutableStateOf("")
    }
    var cidade by remember {
        mutableStateOf("")
    }
    var estado by remember {
        mutableStateOf("")
    }
    var bairro by remember {
        mutableStateOf("")
    }
    var complemento by remember {
        mutableStateOf("")
    }
    var numero by remember {
        mutableStateOf("")
    }
    var celular by remember {
        mutableStateOf("")
    }
    var ddd by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var exibir by remember {
        mutableStateOf(false)
    }

//variavel para se der algum erro
    var error = ""


    var nomeValido by remember {
        mutableStateOf("")
    }

    var idField by remember {
        mutableStateOf(false)
    }

    var buscarBtn by remember {
        mutableStateOf(buscar)
    }
    var atualizarBtn by remember {
        mutableStateOf(atualizar)
    }
    var deletarBtn by remember {
        mutableStateOf(deletar)
    }
    var dadosField by remember {
        mutableStateOf(true)
    }

//lista com todos os DDDS validos para o Brasil
    val ddds = arrayOf(
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "21",
        "22",
        "24",
        "27",
        "28",
        "31",
        "32",
        "33",
        "34",
        "35",
        "37",
        "38",
        "41",
        "42",
        "43",
        "44",
        "45",
        "46",
        "47",
        "48",
        "49",
        "51",
        "53",
        "54",
        "55",
        "61",
        "62",
        "63",
        "64",
        "65",
        "66",
        "67",
        "68",
        "69",
        "71",
        "73",
        "74",
        "75",
        "77",
        "79",
        "81",
        "82",
        "83",
        "84",
        "85",
        "86",
        "87",
        "88",
        "89",
        "91",
        "92",
        "93",
        "94",
        "95",
        "96",
        "97",
        "98",
        "99"
    )

//Criando a variavel de contexto para a funcao de toast
    val myContext = LocalContext.current

// criando a variavel para controlar a tela
    var etapa by remember {
        mutableStateOf("inicio")
    }


//Criando um surface para preencher a tela inteira (Tela de fundo)
    Surface(
        color = Color(0xFF006064),
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)

    ) {
//Definindo o aplicativo para deixar os elementos em coluna (um de baixo do outro)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(53.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (etapa == "inicio") {
                trataRetorno(false)

                Text(
                    text = "Capturar Dados de Cadastro",
                    color = Color.White,
                    fontSize = 23.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(10.dp))

                if (opcao == "buscar" || opcao == "atualizar" || opcao == "deletar") {
                    idField = true
                }

                if (idField) {
                    OutlinedTextField(
                        enabled = true,
                        value = value,
                        placeholder = { Text("ID") },
                        label = { Text("ID") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .align(Alignment.Start),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            focusedBorderColor = Black,
                            containerColor = White,
                            textColor = Black,
                            focusedLabelColor = White,
                            unfocusedLabelColor = White
                        ),
                        onValueChange = {
                            value = it

                            if (value.isEmpty()) {
                                buscarBtn = false
                                atualizarBtn = false
                                deletarBtn = false
                            }

                            if (value.isNotBlank() && opcao == "buscar") {
                                buscarBtn = true
                            }
                            if (value.isNotBlank() && opcao == "atualizar") {
                                atualizarBtn = true
                            }
                            if (value.isNotBlank() && opcao == "deletar") {
                                deletarBtn = true
                            }
                        },
                    )
                }

                if (opcao == "buscar" || opcao == "deletar") {
                    dadosField = false
                }

//Criando a text field do nome
                OutlinedTextField(
                    enabled = dadosField,
                    value = nome,
                    placeholder = { Text("Nome Completo") },
                    label = { Text("Nome Completo") },
                    modifier = Modifier
                        .height(70.dp)
                        .width(301.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Black,
                        focusedBorderColor = Black,
                        containerColor = White,
                        textColor = Black,
                        focusedLabelColor = White,
                        unfocusedLabelColor = White
                    ),
                    onValueChange = {
//verifica se a string nao contem uma quebra de linha se for verdadeiro executa
// o que esta dentro do if
                        if (!it.contains('\n')) {
                            nome = it.uppercase()
                        }
                    },
                )

                if (nome.length > 0 && !validateName(nome)) {
                    Text(
                        "Nome inválido", color = Color.Red,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

//Criando uma variavel para loading das informaçoes do cep
                var loading by remember { mutableStateOf(false) }

//Criando o textfield do cep
                OutlinedTextField(
                    enabled = dadosField,
                    value = cep,
                    placeholder = { Text("12345678") },
                    label = { Text("CEP") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .height(70.dp)
                        .width(301.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Black,
                        focusedBorderColor = Black,
                        containerColor = White,
                        textColor = Black,
                        focusedLabelColor = White,
                        unfocusedLabelColor = White
                    ),
                    onValueChange = {
//Se a string digitada for somente numeros o cep pega o valor da digitacao
                        if (it.isDigitsOnly()) {
                            cep = it
                        }
                        if (cep.length <= 8) {
                            exibir = false
                        }

//Se o Cep tiver 8 numeros exemplo (04346000) ele vai ativar a variavel loading e
//Buscar as informaçoes na função findAddress passando o parametro cep
// e quando houver um retorno ele volta a variavel loading para false
//a variavel exibir mostra os campos preenchidos pelas informações retornadas pela função
//e faz a variavel cep retornar para vazia
                        if (cep.length == 8 && !exibir) {
                            loading = true
                            findAddress(cep) { resultado, res, res2, res3, erro ->
                                endereco = resultado
                                cidade = res
                                bairro = res2
                                estado = res3
                                error = erro
                                loading = false
                                exibir = true
                            }
                        }
                    }
                )

//Se a variavel loading for verdadeira ele mostra uma bolinha carregando
                if (loading) {
                    CircularProgressIndicator()
                }
                if (error != "") {
                    exibir = false
                    Text(
                        "CEP inválido",
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

//se a variavel exibir for verdadeira ela retorna um erro se tiver erro e se estiver tudo certo retorna as textfields com as informações do via cep
                if (exibir) {
//                    (Text(error))

                    OutlinedTextField(
                        value = endereco,
                        label = { Text("Rua") },
                        enabled = false,
                        modifier = Modifier
                            .height(70.dp)
                            .width(301.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            focusedBorderColor = Black,
                            containerColor = White,
                            textColor = Black,
                            focusedLabelColor = White,
                            unfocusedLabelColor = White,
                            disabledLabelColor = White
                        ),
                        onValueChange = { endereco = it },

                        )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = numero,
                            placeholder = { Text("Numero") },
                            label = { Text("N°") },
                            modifier = Modifier
                                .height(70.dp)
                                .width(97.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Black,
                                focusedBorderColor = Black,
                                containerColor = White,
                                textColor = Black,
                                focusedLabelColor = White,
                                unfocusedLabelColor = White,
                            ),
                            onValueChange = {
                                if (it.isDigitsOnly()) {
                                    numero = it
                                }
                            },

                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        OutlinedTextField(value = complemento,
                            placeholder = { Text("Apt, Lote, Etc...") },
                            label = { Text("Complemento") },
                            modifier = Modifier
                                .height(70.dp)
                                .width(197.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Black,
                                focusedBorderColor = Black,
                                containerColor = White,
                                textColor = Black,
                                focusedLabelColor = White,
                                unfocusedLabelColor = White,
                            ),
                            onValueChange = { complemento = it }

                        )

                    }

                    OutlinedTextField(value = bairro,
                        label = { Text("Bairro") },
                        enabled = false,
                        modifier = Modifier
                            .height(70.dp)
                            .width(301.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            focusedBorderColor = Black,
                            containerColor = White,
                            textColor = Black,
                            focusedLabelColor = White,
                            unfocusedLabelColor = White,
                            disabledLabelColor = White
                        ),
                        onValueChange = { bairro = it }

                    )
                    Row() {
                        OutlinedTextField(value = cidade,
                            label = { Text("Cidade") },

                            enabled = false,
                            modifier = Modifier
                                .height(70.dp)
                                .width(197.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Black,
                                focusedBorderColor = Black,
                                containerColor = White,
                                textColor = Black,
                                focusedLabelColor = White,
                                unfocusedLabelColor = White,
                                disabledLabelColor = White
                            ),
                            onValueChange = { cidade = it }
                        )
                        Spacer(modifier = Modifier.padding(4.dp))

                        OutlinedTextField(value = estado,
                            label = { Text("UF") },
                            enabled = false,
                            modifier = Modifier
                                .height(70.dp)
                                .width(97.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Black,
                                focusedBorderColor = Black,
                                containerColor = White,
                                textColor = Black,
                                focusedLabelColor = White,
                                unfocusedLabelColor = White,
                                disabledLabelColor = White
                            ),
                            onValueChange = { estado = it }

                        )
                    }

                }

//Criando uma row para deixar os itens um do lado do outro
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        enabled = dadosField,
                        value = ddd,
                        placeholder = { Text("00") },
                        label = { Text("DDD") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .width(97.dp)
                            .height(70.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            focusedBorderColor = Black,
                            containerColor = White,
                            textColor = Black,
                            focusedLabelColor = White,
                            unfocusedLabelColor = White
                        ),
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                ddd = it
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedTextField(
                        enabled = dadosField,
                        value = celular,
                        placeholder = { Text("123456789") },
                        label = { Text("Celular") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .height(70.dp)
                            .width(197.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            focusedBorderColor = Black,
                            containerColor = White,
                            textColor = Black,
                            focusedLabelColor = White,
                            unfocusedLabelColor = White
                        ),
                        onValueChange = {
                            if (it.isDigitsOnly()) {
                                celular = it
                            }
                        }
                    )
                }

//Se o ddd não estiver na lista de ddds e o ddd nao for vazio envia a mensagem de erro ddd invalido
                if (ddd !in ddds && ddd.isNotBlank()) {
                    Text(
                        "DDD inválido",
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

//Se o tamanho do celular for maior que 9 e celular for entre 1 e 8 da a mensagem numero de celular invalido
                if (celular.length > 9 || celular.length in 1..8) {
                    Text(
                        "Número de celular inválido",
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

// se o tamanho do celular for == 9 e se o primeiro numero do celular nao for 9
//da a mensagem de erro celular deve começar com 9
                if (celular.length == 9) {
                    if (celular[0] != '9') {
                        Text(
                            "Celular deve começar com 9 ",
                            color = Color.Red,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }

// Criando a textField para email
                OutlinedTextField(
                    enabled = dadosField,
                    value = email,
                    placeholder = { Text("email@dominio.com.br") },
                    label = { Text("E-mail") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .height(70.dp)
                        .width(301.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Black,
                        focusedBorderColor = Black,
                        containerColor = White,
                        textColor = Black,
                        focusedLabelColor = White,
                        unfocusedLabelColor = White
                    ),
                    onValueChange = { email = it.replace(" ", "").lowercase() },
                )

//Se o email nao for vazio e o email nao for valido
                if (email.isNotBlank() && !isValid(email)) {
//Deixa uma mensagem email invalido na tela até for valido
                    Text(
                        "Email inválido", color = Color.Red,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.padding(16.dp))

//Criando um botao para enviar o codigo de verificação e verificar algumas informações preenchidas

                Row {
//                    if (value.isBlank() ) {
//                        buscarBtn = false
//                    }

                    OutlinedButton(
                        enabled = buscarBtn,
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
                            etapa = "buscarOK"
                            id = value.toInt()
                            idToShow = id
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text("Buscar Dados", color = Color.Black)
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedButton(
                        enabled = inserir,
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
// Se todas essas validações passar ele muda para a tela 2

                            if (ddd in ddds && ddd.isNotBlank() && cep.length == 8 && exibir && validateName(
                                    nome
                                ) && celular.length == 9 && celular[0] == '9'
                            ) {
                                etapa = "inserirOK"
                            }
                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text("Inserir Dados", color = Color.Black)
                    }
                }

                Row {
                    OutlinedButton(
                        enabled = atualizarBtn,
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
// Se todas essas validações passar ele muda para a tela 2

                            if (ddd in ddds && ddd.isNotBlank() && cep.length == 8 && exibir && validateName(
                                    nome
                                ) && celular.length == 9 && celular[0] == '9'
                            ) {
                                etapa = "atualizarOK"
                            }
                            id = value.toInt()
                            idToShow = id

                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text("Atualizar Dados", color = Color.Black)
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedButton(
                        enabled = deletarBtn,
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF26A69A)
                        ),
                        onClick = {
// Se todas essas validações passar ele muda para a tela 2

//                            if (ddd in ddds && ddd.isNotBlank() && cep.length == 8 && exibir && validateName(
//                                    nome
//                                ) && celular.length == 9 && celular[0] == '9'
//                            ) {
                            etapa = "deletarOK"
//                            }
                            id = value.toInt()
                            idToShow = id

                        },
//Habilita o botão somente se o email for valido
                    ) {
                        Text("Deletar Dados", color = Color.Black)
                    }
                }

                OutlinedButton(
                    enabled = true,
                    modifier = Modifier.width(305.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF26A69A)
                    ),
                    onClick = {
                        etapa = "buscarAllOK"
                    },
//Habilita o botão somente se o email for valido
                ) {
                    Text("Buscar Todos Dados", color = Color.Black)
                }

                OutlinedButton(
                    enabled = true,
                    modifier = Modifier.width(305.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF26A69A)
                    ),
                    onClick = {
// Se todas essas validações passar ele muda para a tela 2
                        nome = ""
                        cep = ""
                        ddd = ""
                        numero = ""
                        complemento = ""
                        celular = ""
                        email = ""
                        exibir = false

                    },
//Habilita o botão somente se o email for valido
                ) {
                    Text("Limpar Dados", color = Color.Black)
                }

                OutlinedButton(
//                    enabled = true,
                    modifier = Modifier.width(305.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF26A69A)
                    ),
                    onClick = {
                        trataRetorno(true)
                    },
//Habilita o botão somente se o email for valido
                ) {
                    Text("Voltar", color = Color.Black)
                }
            }


//Se a etapa for igual a "buscar" roda essas funções
            if (etapa == "buscarOK") {
//                nomeValido = nome
                BuscaDados(db, idToShow) {
                    trataRetorno(true)
                }
            }

            if (etapa == "buscarAllOK") {
                BuscaTodosDados(db) {
                    trataRetorno(true)
                }
            }

//Se a etapa for igual a "inserir" roda essas funções
            if (etapa == "inserirOK") {
                nomeValido = nome
                InsereDados(
                    db,
                    nomeValido,
                    cep,
                    endereco,
                    numero,
                    complemento,
                    cidade,
                    estado,
                    bairro,
                    ddd,
                    celular,
                    email
                ) {
                    trataRetorno(true)
                }
            }

//Se a etapa for igual a "atualizar" roda essas funções
            if (etapa == "atualizarOK") {
                nomeValido = nome

                AtualizaDados(
                    db,
                    idToShow,
                    nomeValido,
                    cep,
                    endereco,
                    numero,
                    complemento,
                    cidade,
                    estado,
                    bairro,
                    ddd,
                    celular,
                    email
                ) {
                    trataRetorno(true)
                }
            }


//Se a etapa for igual a "deletar" roda essas funções
            if (etapa == "deletarOK") {
                DeletaDados(db, idToShow) {
                    trataRetorno(true)
                }
            }
        }
    }
}


@Composable
fun BuscaDados(db: TaskDBHelper?, idToShow: Int, trataRetorno: (Boolean) -> Unit) {
    if (db != null) {
        var item = db.getSingle(idToShow.toLong())

        if (item.id.toInt() == -1) {
            Text(
                text = "ID inválido ou removido",
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.Red,
                fontWeight = FontWeight.ExtraBold
            )
        } else {
            Text(
                text = """
                |${item.id}
                |${item.nome}
                |${item.cep}
                |${item.rua}, ${item.numero}, ${item.complemento}
                |${item.bairro}
                |${item.cidade}-${item.uf}
                |${item.ddd + item.celular}
                |${item.email}
                |""".trimMargin(),
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno(true)
    }) {
        Text("Voltar")
    }
}

@Composable
fun BuscaTodosDados(db: TaskDBHelper?, trataRetorno: (Boolean) -> Unit) {
    if (db != null) {
        var lista = db.getAllTasks()
        for (item in lista) {
            Text(
                text = """
                |${item.id}
                |${item.nome}
                |${item.cep}
                |${item.rua}
                |${item.numero}
                |${item.complemento}
                |${item.bairro}
                |${item.cidade}, ${item.uf}
                |${item.ddd + item.celular}
                |${item.email}
                |""".trimMargin(),
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno(true)
    }) {
        Text("Voltar")
    }
}

@Composable
fun InsereDados(
    db: TaskDBHelper?,
    nome: String,
    cep: String,
    endereco: String,
    numero: String,
    complemento: String,
    cidade: String,
    estado: String,
    bairro: String,
    ddd: String,
    celular: String,
    email: String,
    trataRetorno: (Boolean) -> Unit
) {

    if (db != null) {
        db.addTaks(
            TaskDBHelper.Task(
                -1,
                nome,
                cep,
                endereco,
                numero,
                complemento,
                bairro,
                cidade,
                estado,
                ddd,
                celular,
                email
            )
        )
    }
    Text(
        "$nome, seus dados foram salvos",
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno(true)
    }) {
        Text("Voltar")
    }
}

@Composable
fun AtualizaDados(
    db: TaskDBHelper?,
    idToShow: Int,
    nome: String,
    cep: String,
    endereco: String,
    numero: String,
    complemento: String,
    cidade: String,
    estado: String,
    bairro: String,
    ddd: String,
    celular: String,
    email: String,
    trataRetorno: (Boolean) -> Unit
) {
    if (db != null) {
        var item = db.getSingle(idToShow.toLong())

        if (item.id.toInt() == -1) {
            Text(
                text = "ID inválido ou removido. Faça uma busca por ID antes de atualizar os dados.",
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.Red,
                fontWeight = FontWeight.ExtraBold
            )
        } else {
            db.updateTask(
                TaskDBHelper.Task(
                    idToShow.toLong(),
                    nome,
                    cep,
                    endereco,
                    numero,
                    complemento,
                    bairro,
                    cidade,
                    estado,
                    ddd,
                    celular,
                    email
                )
            )
            Text(
                "$nome, seus dados foram atualizados",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }


    }

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno(true)
    }) {
        Text("Voltar")
    }
}

@Composable
fun DeletaDados(db: TaskDBHelper?, idToShow: Int, trataRetorno: (Boolean) -> Unit) {
    if (db != null) {
        var item = db.getSingle(idToShow.toLong())

        if (item.id.toInt() == -1) {
            Text(
                text = "ID inválido ou removido. Faça uma busca por ID antes de deletar os dados.",
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                color = Color.Red,
                fontWeight = FontWeight.ExtraBold
            )
        } else {
            db.deleteTask(idToShow.toLong())

            Text(
                "Os dados do ID: $idToShow foram deletados com sucesso",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno(true)
    }) {
        Text("Voltar")
    }
}

//Função que valida se o nome é valido ou não
fun validateName(nome: String): Boolean {
    var isValide = true;

// acessa a função para verificar se nao tem apenas letras e acentos
    if (!containsOnlyLettersAndAccents(nome)) {

        isValide = false;
    }

//se o nome nao tiver um espaço exemplo 2 nomes (Joao Vitor) ele retorna falso
    if (!nome.trim().contains(' ')) {
        isValide = false
    }

//se tudo passar retorna true
    return isValide;
}

//Função para verificar se o nome tem apenas letras e acentos
fun containsOnlyLettersAndAccents(input: String): Boolean {
    // Usando REGULAR EXPRESSIONS
    val pattern = Regex("^[a-zA-ZÀ-ÿ ]+\$")
    return pattern.matches(input)
}

//Funcao para achar o cep
fun findAddress(cep: String, callback: (String, String, String, String, String) -> Unit) {
    var rua = ""
    var city = ""
    var district = ""
    var state = ""
    var retorno = ""
    //acessa a api viacep com o parametro cep no meio
    val baseURL = "https://viacep.com.br/ws/$cep/json/"

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val json = withContext(Dispatchers.IO) {
                URL(baseURL).readText()
            }
            var data = JSONObject(json)

            if (json.contains("erro")) {
                retorno = "Cep não encontrado"
            } else {
                var endereco = data.getString("logradouro")
                var cidade = data.getString("localidade")
                var bairro = data.getString("bairro")
                var estado = data.getString("uf")

                rua = endereco
                city = cidade
                district = bairro
                state = estado
            }
        } catch (e: Exception) {
            retorno = "Erro na busca de CEP - ${e.message}"
        }

        callback(rua, city, district, state, retorno)
    }
}

//Verifica se o email é valido
fun isValid(email: String): Boolean {
    if (email.isEmpty()) {
        return false
    }
    //Quebrar a string pelo @
    val parts = email.split("@")
    if (parts.size != 2) {
        return false
    }
    var userName = parts[0]
    var domain = parts[1]
    if (userName.isEmpty() || domain.isEmpty()) {
        return false
    }
    if (!domain.dropLast(1).contains(".")) {
        return false
    }
    val partDomain = domain.split(".")
    for (item in partDomain) {
        if (item.isBlank()) {
            return false
        }
    }
    return true
}

//Funcao para mostrar um elemento de erro padrão do android
fun showToast(context: Context, message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.TOP, 0, 0) // Faz com que a notificacao apareca por cima do teclado
    toast.show()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ac_05_crudTheme {
        TelaOpcoes(null)
    }
}

@Composable
fun crudFunction(db: TaskDBHelper?) {
    if (db != null) {
        Column()
        {
            db.addTaks(
                TaskDBHelper.Task(
                    -1,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
            db.updateTask(
                TaskDBHelper.Task(
                    2,
                    "Bruno",
                    "60192022",
                    "Rua",
                    "123",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
            db.deleteTask(4)

            var lista = db.getAllTasks()
            for (item in lista) {
                Text(
                    text = """${item.id} - ${item.nome} - CEP: ${item.cep}
                    | ${item.rua}", ${item.numero}, ${item.complemento}
                    | ${item.bairro}
                    | ${item.cidade}, ${item.uf}
                    | ${item.ddd + item.celular}
                    | ${item.email}""".trimMargin()
                )
            }

            var item = db.getSingle(1)
            Text(
                text = """${item.id} - ${item.nome} - CEP: ${item.cep}
                    | ${item.rua}", ${item.numero}, ${item.complemento}
                    | ${item.bairro}
                    | ${item.cidade}, ${item.uf}
                    | ${item.ddd + item.celular}
                    | ${item.email}"""
            )
        }
    }
}

class TaskDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    data class Task(
        val id: Long,
        val nome: String,
        val cep: String,
        val rua: String,
        val numero: String,
        val complemento: String,
        val bairro: String,
        val cidade: String,
        val uf: String,
        val ddd: String,
        val celular: String,
        val email: String
    )

    companion object {
        const val DATABASE_NAME = "task.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "task"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_CEP = "cep"
        const val COLUMN_RUA = "rua"
        const val COLUMN_NUMERO = "numero"
        const val COLUMN_COMPLEMENTO = "complemento"
        const val COLUMN_BAIRRO = "bairro"
        const val COLUMN_CIDADE = "cidade"
        const val COLUMN_UF = "uf"
        const val COLUMN_DDD = "ddd"
        const val COLUMN_CELULAR = "celular"
        const val COLUMN_EMAIL = "email"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOME TEXT, " +
                "$COLUMN_CEP TEXT," +
                "$COLUMN_RUA TEXT," +
                "$COLUMN_NUMERO INTEGER," +
                "$COLUMN_COMPLEMENTO TEXT," +
                "$COLUMN_BAIRRO TEXT," +
                "$COLUMN_CIDADE TEXT," +
                "$COLUMN_UF TEXT," +
                "$COLUMN_DDD TEXT," +
                "$COLUMN_CELULAR TEXT," +
                "$COLUMN_EMAIL TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addTaks(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOME, task.nome)
        values.put(COLUMN_CEP, task.cep)
        values.put(COLUMN_RUA, task.rua)
        values.put(COLUMN_NUMERO, task.numero)
        values.put(COLUMN_COMPLEMENTO, task.complemento)
        values.put(COLUMN_BAIRRO, task.bairro)
        values.put(COLUMN_CIDADE, task.cidade)
        values.put(COLUMN_UF, task.uf)
        values.put(COLUMN_DDD, task.ddd)
        values.put(COLUMN_CELULAR, task.celular)
        values.put(COLUMN_EMAIL, task.email)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllTasks(): List<Task> {
        val tasks = mutableListOf<Task>()
        val query = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
                val cep = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEP))
                val rua = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUA))
                val numero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMERO))
                val complemento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPLEMENTO))
                val bairro = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BAIRRO))
                val cidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIDADE))
                val uf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UF))
                val ddd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DDD))
                val celular = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CELULAR))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                tasks.add(
                    Task(
                        id,
                        nome,
                        cep,
                        rua,
                        numero,
                        complemento,
                        bairro,
                        cidade,
                        uf,
                        ddd,
                        celular,
                        email
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return tasks
    }

    fun getSingle(taskId: Long): Task {
        var tasks = Task(-1, "", "", "", "", "", "", "", "", "", "", "")
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"
        val db = this.readableDatabase
        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME))
            val cep = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CEP))
            val rua = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUA))
            val numero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NUMERO))
            val complemento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMPLEMENTO))
            val bairro = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BAIRRO))
            val cidade = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIDADE))
            val uf = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UF))
            val ddd = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DDD))
            val celular = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CELULAR))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))

            tasks = Task(
                id,
                nome,
                cep,
                rua,
                numero,
                complemento,
                bairro,
                cidade,
                uf,
                ddd,
                celular,
                email
            )
        }

        cursor.close()
        db.close()
        return tasks
    }

    fun updateTask(task: Task) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_NOME, task.nome)
        values.put(COLUMN_CEP, task.cep)
        values.put(COLUMN_RUA, task.rua)
        values.put(COLUMN_NUMERO, task.numero)
        values.put(COLUMN_COMPLEMENTO, task.complemento)
        values.put(COLUMN_BAIRRO, task.bairro)
        values.put(COLUMN_CIDADE, task.cidade)
        values.put(COLUMN_UF, task.uf)
        values.put(COLUMN_DDD, task.ddd)
        values.put(COLUMN_CELULAR, task.celular)
        values.put(COLUMN_EMAIL, task.email)

        db.update(
            TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(
                task.id.toString()
            )
        )
        db.close()
    }

    fun deleteTask(taskId: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }
}