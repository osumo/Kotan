-- data.sql
-- Kotan プロジェクト 初期データ投入 (DML)

-- 1. カテゴリ
INSERT INTO categories (name) VALUES ('農産物'), ('水産物'), ('畜産物'), ('その他');

-- 2. 商品・アイテムマスタ
-- 農産物 (購入すると種が付与される)
INSERT INTO products (category_id, name, description, price, item_type, growth_days, stock) 
VALUES (1, '夕張メロン', '北海道が誇る高級メロンです。', 5000, 'FOOD', 7, 100);
INSERT INTO products (category_id, name, description, price, item_type, growth_days, stock) 
VALUES (1, '十勝じゃがいも', 'ホクホクとした食感が特徴です。', 500, 'FOOD', 3, 500);

-- 水産物 (重さがある対象)
INSERT INTO products (category_id, name, description, price, item_type, weight, stock) 
VALUES (2, '天然秋鮭', '脂の乗った旬の鮭です。', 3000, 'FISH', 3.5, 200);

-- その他 (ガチャコイン・報酬家具など)
INSERT INTO products (category_id, name, description, price, item_type, stock) 
VALUES (4, 'ガチャコイン', 'Farmで交換できるガチャ専用コインです。', 0, 'COIN', 9999);
INSERT INTO products (category_id, name, description, price, item_type, stock) 
VALUES (4, 'アイヌ文様のラグ', '【実績報酬】伝統的な文様のラグです。', 0, 'FURNITURE', 0);
INSERT INTO products (category_id, name, description, price, item_type, stock) 
VALUES (4, '木彫りの熊', '【実績報酬】北海道の定番置物です。', 0, 'FURNITURE', 0);

-- 3. 実績マスタ (報酬アイテムと紐付け)
INSERT INTO achievements (title, description, reward_product_id) 
VALUES ('初めての注文', 'ECサイトで初めて商品を購入した', 5); -- ラグ
INSERT INTO achievements (title, description, reward_product_id) 
VALUES ('ベテラン農家', 'Gardenで植物を10回育てた', 6); -- 木彫りの熊

-- 4. クーポン
INSERT INTO coupons (code, name, discount_rate, min_amount) 
VALUES ('WELCOME10', '初回限定10%OFFクーポン', 10, 0);

-- 5. テストユーザー
INSERT INTO users (email, password, display_name, total_spent) 
VALUES ('test@example.com', 'password', 'テストユーザー', 15000);

-- 6. ユーザー所持アイテム
INSERT INTO inventory (user_id, product_id, quantity) VALUES (1, 1, 2); -- メロン
INSERT INTO inventory (user_id, product_id, quantity) VALUES (1, 4, 10); -- ガチャコイン

-- 7. アクアリウムイベント
INSERT INTO aquarium_events (title, target_weight, current_weight, end_at) 
VALUES ('鮭を100kg集めよう！', 100.0, 15.5, '2026-12-31 23:59:59');