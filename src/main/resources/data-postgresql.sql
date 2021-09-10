INSERT INTO tb_categories (titulo,cor)
SELECT * FROM (SELECT 'LIVRE', '#FFF') AS tmp
WHERE NOT EXISTS (
    SELECT id FROM tb_categories WHERE id = 1
) LIMIT 1;

INSERT INTO users VALUES ('alurauser', true, '$2a$10$NvX8zp9lyxWTB4/LQ7YqyOUqQnLry0CE0Uhg8IvVv4pYSfjMJqJ4K') ON CONFLICT DO NOTHING;
INSERT INTO authorities VALUES ('ROLE_ADMIN', 'alurauser') ON CONFLICT DO NOTHING;
