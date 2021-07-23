SELECT 'CREATE DATABASE db_aluraflix'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'db_aluraflix')\gexec