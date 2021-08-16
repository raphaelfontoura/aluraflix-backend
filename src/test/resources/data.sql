INSERT INTO tb_categories (titulo,cor)
SELECT * FROM (SELECT 'LIVRE', '#FFF') AS tmp
WHERE NOT EXISTS (
    SELECT id FROM tb_categories WHERE id = 1
) LIMIT 1;
----------- SEED FOR TESTS -----------
INSERT INTO tb_categories (titulo,cor) VALUES ('SERIES','#AAA');

INSERT INTO tb_videos (titulo, descricao, url, category_id)
    VALUES ('Alura challenge', 'Video apresentando o challenge de backend da Alura', 'http://youtube.com/alura', 1),
    ('Conhecendo o Spring Boot', 'Spring Boot de uma maneira fácil e tranquila', 'http://youtube.com/springboot', 1),
    ('O mandaloriano', 'Primeiro episódio de uma série Star Wars.', 'http://youtube.com/mandaloriano', 2),
    ('Alura JPA e JPQL', 'Entenda um pouco mais sobre JPA e JPQL.', 'http://youtube.com/aluraJpa', 1);