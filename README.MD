# DCC171 Trabalho 1 2017/3

### Identificação

Felipe Inhan Matos - 201335007

Ciência da Computação


### Descrição

O protótipo foi desenvolvido para um Restaurante onde todos os Pedidos estão associados à uma mesa, ou seja, não existe "Drive thru" ou Pedidos "para levar".

O sistema deve permitir que, enquanto o pedido não estiver fechado, os clientes adicionem e alterem os itens pedidos. A qualquer momento o sistema deve emitir um relatório detalhado do pedido para melhor visualização, esteja ele aberto ou não.

O sistema deve manter um histórico dos pedidos disponível para consulta.

Enquanto itens são adicionados ao Pedido, o sistema deve fornecer, em tempo real, o valor total do Pedido.

É importante garantir que apenas um Pedido esteja aberto por Mesa, ou seja, um novo Pedido só pode ser aberto para uma Mesa que não tem Pedidos abertos. Ao mesmo tempo, é também claro que os Pedidos de uma Mesa são independentes de Pedidos de outra mesa.

### Modelo de Dados

O modelo de dados é simples:

!(Diagrama de Classes)[https://www.dropbox.com/s/bokcpyth4mk6qdl/Diagrama%20de%20Classes.png?dl=0]


### Campos na UI
Seão usadas três Janelas: uma para visualização de Pedidos; outra para manutenção dos Pedidos; e outra para detalhes do pedido.

Na tela principal, chamada *JanelaMain*, os seguintes campos serão usados:

* 3 Button
	* Criação de Novo Pedido
	* Edição de Pedido selecionado
	* Fechamento de Pedido selecionado
* 1 List
	* Mesas do restaurante. Item selecionado é usado como filtro de Pedidos.
* 1 Table
	* Cada linha é uma visão geral do estado dos Pedidos da Mesa selecionada.

Na tela de Edição de Pedidos, chamada *JanelaPedidoItem*, serão usados os campos:
* 2 ComboBox
	* Seleção de Mesa para abertura do Pedido, filtrada por Mesas disponíveis
	* Seleção de Item para ser adicionado ao Pedido
* 1 TextField
	* Informa a quantidade de determinado Item vão ser incluídos no Pedido.
* 1 Table
	* Cada linha é uma representação de um objeto PedidoItem, contendo o nome do Item, a quantidade pedida e o valor do Item.
* 4 Button
	* Adição de Item no Pedido
	* Remoção de Item no Pedido
	* Fechar tela de Edição de Pedido, sem fechar o Pedido
	* Fechar tela de Edição de Pedido, fechando o pedido e vendo detalhes.
* 1 Label
	* Mostra em tempo real o valor total do Pedido.

Na tela de Detalhes de Pedido, *JanelaPedidoDetalhe* apenas um campo é usado:
* 1 TextArea
	* Todo o texto de detalhe do pedido.

### Principais comportamentos da UI

Na *JanelaMain*, os seguintes aspectos são essenciais:
* Listagem de Pedidos deve ser atualizada dinamicamente em resposta à mudança de seleção de Mesa;
* Listagem de Pedidos deve ser atualizada dinamicamente em resposta à alteração de estado de Pedido: fechamento ou alteração de valor total;
* Pedido deve ser selecionável para Edição ou Fechamento.

Na *JanelaPedidoItem*, os seguintes aspectos são essenciais:
* Texto de valor total deve estar sempre atualizado, respondendo à alteração dos itens do Pedido;
* Depois de aberto, não deve ser possível alterar a Mesa de um Pedido;
* Depois de fechado, não deve ser possível fazer alterações em nenhum aspecto do Pedido;
* O mesmo item não deve aparecer mais de uma vez na lista: apenas as quantidades devem ser atualizadas.
* Itens devem ser selecionáveis para Edição ou Remoção.

### Dificuldades

O principal ponto de dificuldade foi a parte superior da *JanelaPedidoItem*. O alinhamento de labels com campos de input (ComboBox, TextField) mostrou-se difícil. Considerou-se usar o *GridBagLayout*, porém a complexidade do problema rapidamente cresceu e a legibilidade diminuiu, principalmente considerando que era só parte do layout da janela. 
Considerando isso, foi optado pela remoção dos labels, deixando os campos de input auto-explicativos e empilhando-os com um *BoxLayout* com orientação vertical.

Outro ponto de indecisão foi o relacionamento entre dados e janelas. Como visto anteriormente, é necessário que a tabela na *JanelaMain* responda à alterações feitas na *JanelaPedidoItem*. Por isso, a instância de JanelaMain é passada como parâmetro para JanelaPedidoItem. Ao mesmo tempo a JanelaPedidoItem faz alterações em um determinado Pedido, às vezes selecionado na JanelaMain. Ou seja, a mesma informação [Pedido] está presente e ativa nas duas janelas ao mesmo tempo, e fiquei indeciso sobre qual a melhor estratégia nesse caso. Por fim, optei em passar tanto a JanelaMain quanto o Pedido como parâmetro para JanelaPedidoItem, visto que conceitualmente, os dados sendo modificados são independentes do estado da JanelaMain.

### Melhorias

Um ponto crítico é a melhoria da interface da janela *JanelaPedidoItem* de acordo com as dificuldades levantadas no item anterior.
Além disso, as janelas e vários elementos tem tamanho fixo, o que torna o redimensionamento imprevisível e a modificação de elementos difícil. Decidir dinamicamente o tamanho dos elementos é um passo importante para a melhoria do sistema.
Mais abstratamente, também seria interessante uma melhor separação entre dados e interface, generalizando as classes de dados e permitindo que a implementação das regras de negócio independa da interface.
