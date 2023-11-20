Scenario: Caminho feliz
Dado que eu acesso o app de cadastro
E preencho todos os campos com dados válidos
E clico botão verificar email
E digito o código correto
Quando clico no botao validar codigo
Então o email é validado com sucesso

#validar campo 1a tela
Scenario: Validar campo nome invalido
Dado que eu acesso o app de cadastro
Quando digitar nome invalido
Entao erro de nome invalido é exibido

Scenario: Validar campo cep invalido
Dado que eu acesso o app de cadastro
Quando digitar cep invalido
Entao erro de cep invalido é exibido

Scenario: Validar campo cep valido
Dado que eu acesso o app de cadastro
Quando digito cep valido
Entao os campos Rua, Numero, Complemento, Bairro, Cidade e Estado são exibidos
E Rua, Bairro cidade e estado sao preenchidos automaticamente

Scenario: Validar campo DDD invalido
Dado que eu acesso o app de cadastro
Quando digitar DDD invalido
Entao erro de DDD invalido é exibido

Scenario: Validar campo celular invalido
Dado que eu acesso o app de cadastro
Quando digitar celular invalido
Entao erro de celular invalido é exibido

Scenario: Validar campo email invalido
Dado que eu acesso o app de cadastro
Quando digitar email invalido
Entao erro de email invalido é exibido

#segunda tela
Scenario: Validar botao validar codigo invalido
Dado esteja na segunda tela
E digito um codigo invalido
Quando clico no botao validar codigo
Entao erro de codigo invalido é exibido 

Scenario: Validar botao reenviar codigo
Dado esteja na segunda tela
Quando clico no botao reenviar codigo
Entao sou redirecionado para a primeira tela

#terceira tela
Scenario: Validar botao voltar
Dado esteja na terceira tela
Quando clico no botao voltar
Entao sou redirecionado para a primeira tela

