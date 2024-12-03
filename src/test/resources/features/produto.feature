#language: pt
  Funcionalidade: Produto

    Cenario: Buscar produto
      Dado que recebo os parametros de consulta de produto valido
      Quando realizar a busca do produto
      E o produto nao existir
      Entao os detalhes do produto nao devem ser retornados
      E o codigo 404 deve ser apresentado