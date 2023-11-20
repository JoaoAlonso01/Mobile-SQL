package com.example.ac_04_capturar_dados_de_cadastro

import android.content.Context
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.ac_04_capturar_dados_de_cadastro.ui.theme.Ac_04_capturar_dados_de_cadastroTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ac_04_capturar_dados_de_cadastroTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Tela()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela() {
//Criando variaveis para controle das TextFields
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
    var exibir by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("")
    }
    var emailValido by remember { mutableStateOf("") }

//variavel para se der algum erro
    var error = ""

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
        mutableStateOf(1)
    }

//Criando um surface para preencher a tela inteira (Tela de fundo)
    Surface(
        color = Color.DarkGray, modifier = Modifier
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
            if (etapa == 1) {

                Text(
                    text = "Capturar Dados de Cadastro",
                    color = Color.White,
                    fontSize = 23.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(10.dp))

//Criando a text field do nome
                OutlinedTextField(value = nome,
                    onValueChange = {
//verifica se a string nao contem uma quebra de linha se for verdadeiro executa
// o que esta dentro do if
                        if (!it.contains('\n')) {
                            nome = it.uppercase()
                        }
//Definindo uma label para o usuario saber oque terá de ser digitado
                    },
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
                    )
                )

                if (nome.length > 0 && !validateName(nome)) {
                    Text(
                        "Nome inválido", color = Color.Red

                    )
                }

//Criando uma variavel para loading das informaçoes do cep
                var loading by remember { mutableStateOf(false) }

//Criando o textfield do cep
                OutlinedTextField(value = cep,
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

//Criando uma Label e alterando o teclado para somente aparecer numeros
                    },
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
                    )
                )

//Se a variavel loading for verdadeira ele mostra uma bolinha carregando
                if (loading) {
                    CircularProgressIndicator()
                }

//se a variavel exibir for verdadeira ela retorna um erro se tiver erro e se estiver tudo certo retorna as textfields com as informações do via cep
                if (exibir) {
                    (Text(error))

                    OutlinedTextField(value = endereco,
                        onValueChange = { endereco = it },
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
                        label = { Text("Rua") })

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = numero,
                            onValueChange = { numero = it },
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
                            label = { Text("N°") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        OutlinedTextField(value = complemento,
                            onValueChange = { complemento = it },
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
                            label = { Text("Complemento") })

                    }

                    OutlinedTextField(value = bairro,
                        onValueChange = { bairro = it },
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
                        label = { Text("Bairro") })

                    Row() {
                        OutlinedTextField(value = cidade,
                            onValueChange = { cidade = it },
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
                            label = { Text("Cidade") })

                        Spacer(modifier = Modifier.padding(4.dp))

                        OutlinedTextField(value = estado,
                            onValueChange = { estado = it },
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
                            label = { Text("UF") })
                    }

                }

//Criando uma row para deixar os itens um do lado do outro
                Row() {
                    OutlinedTextField(value = ddd,
                        onValueChange = {
                            ddd = it
                        },
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
                        )
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    OutlinedTextField(value = celular,
                        onValueChange = {
                            celular = it
                        },
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
                        )
                    )
                }

//Se o ddd não estiver na lista de ddds e o ddd nao for vazio envia a mensagem de erro ddd invalido
                if (ddd !in ddds && ddd.isNotBlank()) {
                    Text(
                        "DDD inválido",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

//Se o tamanho do celular for maior que 9 e celular for entre 1 e 8 da a mensagem numero de celular invalido
                if (celular.length > 9 || celular.length in 1..8) {
                    Text(
                        "Número de celular inválido",
                        color = Color.Red,
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
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }

// Criando a textField para email
                OutlinedTextField(value = email,
                    onValueChange = { email = it.replace(" ", "").lowercase() },
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
                        focusedLabelColor = LightGray,
                        unfocusedLabelColor = LightGray
                    )
                )

//Se o email nao for vazio e o email nao for valido
                if (email.isNotBlank() && !isValid(email)) {
//Deixa uma mensagem email invalido na tela até for valido
                    Text("Email inválido", color = Color.Red)
                }

                Spacer(modifier = Modifier.padding(16.dp))

//Criando um botao para enviar o codigo de verificação e verificar algumas informações preenchidas
                OutlinedButton(
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = LightGray
                    ), onClick = {
// Se todas essas validações passar ele muda para a tela 2
                        if (ddd in ddds && ddd.isNotBlank() && cep.length == 8 && exibir && validateName(
                                nome
                            ) && celular.length == 9 && celular[0] == '9'
                        ) {
                            etapa++
                        }

                    },
//Habilita o botão somente se o email for valido
                    enabled = isValid(email)
                ) {
                    Text("Verifica E-Mail", color = Color.Black)
                }
            }

//Se a etapa for igual a 2 roda essas funções
            if (etapa == 2) {
// Gerar um numero aleatorio
                var numeroAleatorio = gerarNumero()
// Disparar um e-mail
                if (dispararEmail(numeroAleatorio = numeroAleatorio, email = emailValido)) {
// Aguardar digitação do código correto
                    CapturaRetorno(emailValido, numeroAleatorio) {
                        if (it) {
                            etapa++
                        } else {
                            etapa--
                        }
                    }
                } else {
                    var myContext = LocalContext.current
                    showToast(myContext, "Erro ao disparar email. Tente mais tarde.")
                    etapa--
                }
            }

            if (etapa == 3) {
// Exibir mensagem de sucesso
                CapturaComSucesso(emailValido) {
                    emailValido = ""
                    etapa = 1
                }
            }
        }
    }
}


//Função para a etapa 3
@Composable
fun CapturaComSucesso(email: String, trataRetorno: () -> Unit) {
    Text(
        "Email $email validado com sucesso",
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )

    Spacer(modifier = Modifier.padding(16.dp))

    Button(onClick = {
        trataRetorno()
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

//Gera um numero aleatorio para o codigo de verificação
fun gerarNumero(): String {
    var auxiliar = ""

    for (i in 1..6) {
        auxiliar += Random.nextInt(10)
    }
    return auxiliar
}


//Utiliza a api da sendgrid para disparar o email para o usuario
fun dispararEmail(email: String, numeroAleatorio: String): Boolean {
    // Implementação da Rotina de envio do SendGrid
    val myApiKey = "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"
    val url = URL("https://api.sendgrid.com/v3/mail/send")
    val urlConnection = url.openConnection() as HttpURLConnection
    var response = ""
    var bOk = true

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Configurar a conexão para o método POST
            urlConnection.requestMethod = "POST"
            urlConnection.doOutput = true

            // Adicionar cabeçalhos
            urlConnection.setRequestProperty("Authorization", "Bearer $myApiKey")
            urlConnection.setRequestProperty("Content-Type", "application/json")

            // Adicionar o corpo da requisição
            val postData = """
               {
                   "personalizations": [
                   {
                       "to": [
                               {
                                   "email": "$email"
                               }
                           ],
                       "subject": "Confirmação de E-Mail"
                   }
                   ],
                   "content": [
                   {
                       "type": "text/plain",
                       "value": "Seu código de validação é $numeroAleatorio"
                   }
               ]   ,
               "from": {
                   "email": "alexandre.ricardo@gmail.com",
                   "name": "Validador de E-Mais"
               },
               "reply_to": {
                   "email": "noreply@gmail.com",
                   "name": "No Reply"
               }
           }
           """.trimIndent()

            val out = BufferedOutputStream(urlConnection.outputStream)
            val writer = OutputStreamWriter(out, "UTF-8")
            writer.write(postData)
            writer.flush()
            writer.close()
            out.close()

            // Obter a resposta do servidor
            val inputStream = urlConnection.inputStream
            response =
                inputStream.bufferedReader().use { it.readText() } // Lidar com a resposta aqui
        } catch (e: Exception) {
            bOk = false
        } finally {
            urlConnection.disconnect()
        }
    }
    return bOk
}

//Tela 2 da aplicação onde pede o codigo de verificação para a pessoa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapturaRetorno(email: String, numeroAleatorio: String, resultadoCaptura: (Boolean) -> Unit) {
    val myContext = LocalContext.current

    var codigoDigitado by remember {
        mutableStateOf("")
    }
    Text(
        "Codigo de Validação Enviado.",
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )

    Spacer(modifier = Modifier.padding(16.dp))

    Text(
        "Foi enviado um código de verificação para o e-mail $email. Verifique sua caixa postal e informe abaixo o código recebido",
        fontSize = 12.sp,
        textAlign = TextAlign.Center,
        color = Color.White
    )

    Spacer(modifier = Modifier.padding(16.dp))

    TextField(value = codigoDigitado,
        onValueChange = {
            if (it.length <= 6) {
                codigoDigitado = it
            }
        },
        label = { Text("Código de Verificação") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )

    Spacer(modifier = Modifier.padding(16.dp))

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = {
            if (codigoDigitado == numeroAleatorio) {
                resultadoCaptura(true)
            } else {
                showToast(myContext, "Código inválido. $numeroAleatorio")
            }
        }) {
            Text("Validar Código")
        }
        Button(onClick = {
            resultadoCaptura(false)
        }) {
            Text("Reenviar Código")
        }
    }
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
    Ac_04_capturar_dados_de_cadastroTheme {
        Tela()
    }
}
