INSERT INTO tb_categories (titulo,cor)
SELECT * FROM (SELECT 'LIVRE', '#FFF') AS tmp
WHERE NOT EXISTS (
    SELECT id FROM tb_categories WHERE id = 1
) LIMIT 1;

INSERT INTO tb_videos (titulo, descricao, url, category_id)
    VALUES ('O que faz uma desenvolvedora front-end?',
     'O que é Front-end? Trabalhando na área os termos HTML, CSS e JavaScript fazem parte da rotina das desenvolvedoras e desenvolvedores. Mas o que eles fazem, afinal? Descubra com a Vanessa!',
      'https://www.youtube.com/watch?v=ZY3-MFxVdEw', 1),
    ('O que é REST?', '', 'https://www.youtube.com/watch?v=weQ8ssA6iBU', 1),
    ('Java, Spring, Microserviços e vagas na Hotmart',
     'Nessa live, Paulo Silveira conversa sobre a evolução do Java em microsserviços e sobre o caso da Hotmart com Marco Aurélio Ribeiro, especialista em desenvolvimento com 17 anos de carreira e trooper da Hotmart há mais de seis anos.',
      'https://www.youtube.com/watch?v=f46tw7lOt_0', 1),
    ('Aprenda tudo sobre Data Science, seus primeiros passos',
     'Você é entusiasta de Inteligência artificial, é do mundo Python, de BI etc e quer aprender sobre Data Science? Preparamos este curso pra você!',
      'https://www.youtube.com/watch?v=IQdISZCosAE', 1);