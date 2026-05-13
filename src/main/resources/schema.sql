-- schema.sql
-- 目的: データベースのテーブル作成（DDL）
-- 今後、ここに CREATE TABLE 文などを記述してください。

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100)
);