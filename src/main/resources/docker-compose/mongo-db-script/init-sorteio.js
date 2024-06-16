db = db.getSiblingDB('bingo'); // Nome do banco de dados

db.sorteio.insertOne({
    _id: ObjectId('666eba856ec8517e8b88481b'),
    createAt: new Date(),
    local: 'Fenda do Biquini',
    numeros_sorteados_qtd: 0,
    tipoSorteio: 'AUTOMATICO',
    cartelasQtd: 0,
    ganharamQuadra: 0,
    ganharamQuina: 0,
    ganharamFull: 0,
    TiraTeima: 'AUTOMATICO',
    status: 'ATIVO',
    _class: 'br.com.fabioalvaro.sorteiocore.dominio.Sorteio'
});

db.vendedor.insertOne({
    _id: ObjectId("666eb9296ec8517e8b884815"),
    nome: "Fatima",
    createdAt: new Date()
});
db.jogador.insertOne({
    _id: ObjectId("666eba2b6ec8517e8b884818"),
    nome: "TatianaFavoretti",
    saldo: 0.0,
    createdAt: new Date()

});

db.cartela.insertOne({

    _id: ObjectId('666ebce33816ce25572dad0f'),
    createdAt: new Date(),
    jogadorId: '666eba2b6ec8517e8b884818',
    linha01: [
        1,
        2,
        3,
        4,
        5
    ],
    linha02: [
        6,
        7,
        8,
        9,
        10
    ],
    linha03: [
        11,
        12,
        13,
        14,
        15
    ],
    sorteioId: '666eba856ec8517e8b88481b',
    vendedorId: '666eb9296ec8517e8b884815',
    ganhouQuadra: false,
    ganhouQuina: false,
    ganhouCheia: false,
    _class: 'br.com.fabioalvaro.sorteiocore.dominio.Cartela'

});