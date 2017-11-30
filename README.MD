# DCC171 Trabalho 2 2017/3

### Identificação

Felipe Inhan Matos - 201335007

Ciência da Computação

### Protocolo de Persistência

É utilizado apenas um arquivo para persistência. Todos os dados da aplicação são armazenados juntos.
Todos os dados do programa se resumem a dados da Mesas. Portanto, o formato de armazenamento é simplesmente

```
begin MESA
[dados da mesa n]
end MESA
begin MESA
[dados da mesa n+1]
end MESA
[...]
```

Mesas podem ter vários pedidos, então analogamente, dentro de `[dados da mesa]` cada pedido da mesa é armazenado em sequência no formato:

```
begin PEDIDO
[dados do pedido]
end PEDIDO
```

Cada Pedido pode ter vários itens, e é importante que seja armazenado o preço dos itens no momento que o Pedido foi feito.
Portanto, cada item de um determinado  pedido é armazenado em uma linha, dentro de `[dados do pedido]`

```
X-Bacon	9.00	3
[nome]	[preco histórico]	[quantidade]
```

Dessa forma, o preço atual do produto é irrelevante para pedidos já fechados, e os dados persistidos são corretos.

### Pontos de Melhorias

Todo o arquivo é reescrito a cada alteração, o que significa que dados que não foram alterados também são reescritos. É importante otimizar isso.

Outro ponto é um sistema de indexação, considerando que o número de objetos no sistema pode crescer indefinidamente. Esse ponto está associado ao anterior.

Como é importante que o arquivo de persistência seja legível por humanos, também seria interessante um melhor espaçamento entre os dados, separando mais claramente o nível a relação entre os dados.