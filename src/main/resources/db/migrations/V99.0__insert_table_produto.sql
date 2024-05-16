DO $$
BEGIN
    -- Inclusão de Lanches
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Big Fiap', 'Dois hambúrgueres, alface americana, queijo cheddar, maionese, cebola, picles e pão com gergelim', 1, 26.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Quadra com Queijo', 'Um hambúrguer, queijo cheddar, picles, cebola, ketchup, mostarda e pão com gergelim', 1, 27.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Cheddar FiapMelt', 'Um hambúrguer, molho cremoso sabor cheddar, cebola ao molho shoyu e pão escuro com gergelim', 1, 27.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapChicken', 'Frango empanado, maionese, alface americana e pão com gergelim', 1, 24.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapChicken Bacon', 'Frango empanado, maionese, bacon, alface americana e pão com gergelim', 1, 26.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Fiap Bacon Salad', 'Composto pelo nosso pão tipo brioche, hambúrguer de carne 100% bovina, maionese, alface, tomate, fatias de bacon e queijo sabor cheddar', 1, 38.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Fiap Melt Crispy', 'Delicioso molho cremoso sabor cheddar, hambúrguer de carne 100% bovina, maionese, cebola crispy, fatias de bacon, queijo sabor cheddar e pão.', 1, 38.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Fiap Tasty Turbo Queijo', 'Um hambúrguer, queijo processado sabor emental, queijo processado sabor cheddar, molho com queijo mussarela, tomate, alface americana, cebola, e pão', 1, 40.9, 'IMAGEM');

    -- Inclusão de Bebidas
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapShake Ovomaltine 400ml', 'O novo FiapShake é feito com leite e batido na hora com o delicioso Ovomaltine', 2, 16.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapShake Chocolate 400ml', 'O novo FiapShake é feito com leite e batido na hora com o delicioso chocolate Kopenhagen', 2, 16.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapShake Morango 400ml', 'O novo FiapShake é feito com leite e batido na hora com calda de morango', 2, 16.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Coca-Cola 500ml', 'Bebida gelada na medida certa para matar sua sede. Refrescante Coca-Cola 500ml', 2, 12.9, 'IMAGEM');

    -- Inclusão de Acompanhamentos
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Molho FBO', 'O saboroso molho Fbo, agora no tamanho ideal para você mergulhar suas fiapzices', 3, 6.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('Molho Saboroso', 'O icônico molho Saboroso, agora no tamanho ideal para você mergulhar suas fiapzices', 3, 6.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapFritas Cheddar Bacon', 'As deliciosas batatas com molho cheddar e pedacinhos de bacon', 3, 18.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapFritas Grande', 'Deliciosas batatas selecionadas, fritas.', 3, 15.9, 'IMAGEM');

    -- Inclusão de Sobremesas
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapFlurry Chocrocante com Diamante Negro', 'Composto por bebida láctea sabor baunilha, pedaços de Diamante Negro e calda sabor chocolate com pedaços crocantes que fica durinha.', 4, 15.9, 'IMAGEM');
    INSERT INTO "tech-challenge"."produto" (nome, descricao, id_categoria, preco, imagem) VALUES ('FiapFlurry KitKat com Leite em Pó', 'Composto por bebida láctea sabor baunilha, cobertura sabor chocolate, leite em pó e chocolate Kitkat', 4, 14.9, 'IMAGEM');
END $$;