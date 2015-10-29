SELECT 
  nome, exercicio_id, enunciado, tipo
FROM 
  tbexercicios, tbexerciciostags, tbtags
WHERE 
  tbexerciciostags.exercicio_id = tbexercicios.id AND
  tbtags.id = tbexerciciostags.tag_id AND
  tbtags.nome in ('comida');
