-- Kotan Test Data

-- 1. users
INSERT INTO users (email, password, display_name, postal_code, address, phone_number, total_spent, converted_spent) VALUES
('test@example.com', '$2a$10$vW9mYh4wF.S.mU.zUuGv.e', 'テストユーザー', '100-0001', '東京都千代田区千代田1-1', '090-1234-5678', 5000, 3000),
('user2@example.com', '$2a$10$vW9mYh4wF.S.mU.zUuGv.e', 'サトシ', '060-0808', '北海道札幌市北区北8条西5丁目', '080-9876-5432', 12000, 10000),
('aaa@example.com', 'aaa', 'AAA', '530-0001', '大阪府大阪市北区梅田1-1-1', '06-1234-5678', 0, 0),
('hba.masumura@gmail.com', 'aaa', 'マスムラ', '150-0002', '東京都渋谷区渋谷2-2-2', '090-9999-8888', 25000, 15000);

-- 2. categories
INSERT INTO categories (name) VALUES
('農産物'),
('水産物'),
('畜産物'),
('その他');

-- 3. coupons
INSERT INTO coupons (code, name, discount_rate, min_amount) VALUES
('WELCOME10', 'ウェルカムクーポン', 10, 1000),
('BIRTHDAY20', 'お誕生日クーポン', 20, 0),
('SPECIAL500', '特別割引クーポン', 5, 5000);

-- 4. user_coupons
INSERT INTO user_coupons (user_id, coupon_id, expire_at) VALUES
(1, 1, '2026-12-31 23:59:59'),
(1, 3, '2026-06-30 23:59:59'),
(2, 2, '2026-12-31 23:59:59'),
(3, 1, '2026-12-31 23:59:59'),
(4, 1, '2026-12-31 23:59:59'),
(4, 2, '2026-12-31 23:59:59');

-- 5. products
INSERT INTO products (category_id, name, description, price, image_url, item_type, stock, growth_days, weight) VALUES
-- 農産物
(1, '北海道産じゃがいも 5kg', 'ホクホクでおいしいじゃがいもです。', 2500, '/images/potato_box.png', 'FOOD', 100, 0, 0),
(1, 'じゃがいもの種', '庭に植えるとじゃがいもが育ちます。', 500, '/images/potato_seed.png', 'PLANT', 0, 7, 0),
-- 水産物
(2, '特選毛ガニ', '身がぎっしり詰まった毛ガニです。', 8000, '/images/crab.png', 'FOOD', 50, 0, 0),
(2, 'サケ（稚魚）', 'アクアリウムで育てるサケの稚魚です。', 1000, '/images/salmon_fry.png', 'FISH', 0, 0, 1.5),
-- 畜産物
(3, '十勝和牛 ステーキ用', 'とろけるような食感の和牛です。', 12000, '/images/wagyu_steak.png', 'FOOD', 30, 0, 0),
-- その他・家具
(4, '木製の机', 'シンプルで使いやすい家具です。', 5000, '/images/desk.png', 'FURNITURE', 0, 0, 0),
(4, '雪だるまの置物', '冬にぴったりの可愛い置物です。', 2000, '/images/snowman.png', 'FURNITURE', 0, 0, 0);

-- 6. inventory
INSERT INTO inventory (user_id, product_id, quantity) VALUES
(1, 2, 5),
(1, 6, 1),
(2, 4, 3);

-- 7. room_placements
INSERT INTO room_placements (user_id, product_id, pos_x, pos_y) VALUES
(1, 6, 10.5, 5.2);

-- 8. garden_states
INSERT INTO garden_states (user_id, slot_index, product_id, planted_at) VALUES
(1, 0, 2, NOW() - INTERVAL '3 days');

-- 9. aquarium_events
INSERT INTO aquarium_events (title, target_weight, current_weight, end_at) VALUES
('春のサケ祭り', 100.0, 45.5, '2026-05-31 23:59:59');

-- 10. achievements
INSERT INTO achievements (title, description, reward_product_id) VALUES
('最初の一歩', '初めてログインする', 6), -- 机を報酬に変更
('庭師の卵', '初めて種を植える', 6),
('爆買い王', '累計購入金額が10,000円を突破', 7);

-- 11. user_achievements
INSERT INTO user_achievements (user_id, achievement_id) VALUES
(1, 1),
(2, 1),
(2, 3);

-- 12. orders
INSERT INTO orders (user_id, total_price) VALUES
(1, 2250),
(2, 12000);

-- 13. applied_coupons
INSERT INTO applied_coupons (order_id, coupon_id) VALUES
(1, 1);

-- 14. order_items
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES
(1, 1, 1, 2500),
(2, 5, 1, 12000);