-- Kotan Database Schema

-- 1. ユーザー・基盤系
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS applied_coupons;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user_achievements;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS aquarium_events;
DROP TABLE IF EXISTS garden_states;
DROP TABLE IF EXISTS room_placements;
DROP TABLE IF EXISTS inventory;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS user_coupons;
DROP TABLE IF EXISTS coupons;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(100),
    total_spent INTEGER DEFAULT 0,
    converted_spent INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE coupons (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    discount_rate INTEGER,
    min_amount INTEGER
);

CREATE TABLE user_coupons (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    coupon_id INTEGER REFERENCES coupons(id),
    expire_at TIMESTAMP,
    acquired_at TIMESTAMP DEFAULT NOW()
);

-- 2. EC・商品マスタ系
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    category_id INTEGER REFERENCES categories(id),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price INTEGER NOT NULL,
    image_url VARCHAR(255),
    item_type VARCHAR(20), -- FOOD, FURNITURE, PLANT, FISH
    weight REAL DEFAULT 0,
    growth_days INTEGER DEFAULT 0,
    stock INTEGER DEFAULT 0
);

-- 3. ユーザー所持・配置系
CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    product_id INTEGER REFERENCES products(id),
    quantity INTEGER DEFAULT 1
);

CREATE TABLE room_placements (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    product_id INTEGER REFERENCES products(id),
    pos_x REAL,
    pos_y REAL
);

-- 4. アクティビティ専用
CREATE TABLE garden_states (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    slot_index INTEGER,
    product_id INTEGER REFERENCES products(id),
    planted_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE aquarium_events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100),
    target_weight REAL,
    current_weight REAL,
    end_at TIMESTAMP
);

-- 5. 実績・報酬系
CREATE TABLE achievements (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    reward_product_id INTEGER REFERENCES products(id)
);

CREATE TABLE user_achievements (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    achievement_id INTEGER REFERENCES achievements(id),
    achieved_at TIMESTAMP DEFAULT NOW()
);

-- 6. 注文・決済系
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    total_price INTEGER NOT NULL,
    ordered_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE applied_coupons (
    id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(id),
    user_coupon_id INTEGER REFERENCES user_coupons(id)
);

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INTEGER REFERENCES orders(id),
    product_id INTEGER REFERENCES products(id),
    quantity INTEGER NOT NULL,
    price_at_purchase INTEGER NOT NULL
);