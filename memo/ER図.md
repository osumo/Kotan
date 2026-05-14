# Kotan プロジェクト ER図

このER図は、`テーブル定義書.md` に基づいて作成されています。
1対多（1:N）および多対多（N:M）の関係を視覚化しています。

![ER図](file:///c:/pleiades/2025-12/workspace/Kotan/memo/ER図.png)

## Mermaid ダイアグラム

```mermaid
erDiagram
    users ||--o{ orders : "places"
    users ||--o{ user_coupons : "holds"
    users ||--o{ inventory : "owns"
    users ||--o{ room_placements : "arranges"
    users ||--o{ garden_states : "plants"
    users ||--o{ user_achievements : "earns"

    coupons ||--o{ user_coupons : "assigned_to"
    coupons ||--o{ applied_coupons : "used_in"

    orders ||--o{ order_items : "contains"
    orders ||--o{ applied_coupons : "applies"

    products ||--o{ order_items : "ordered_as"
    products ||--o{ inventory : "stored_as"
    products ||--o{ room_placements : "placed_as"
    products ||--o{ garden_states : "grown_as"
    products |o--o{ achievements : "reward_for"

    categories ||--o{ products : "classifies"

    achievements ||--o{ user_achievements : "completed_by"

    users {
        int id PK
        string email
        string display_name
        int total_spent
    }

    products {
        int id PK
        int category_id FK
        string name
        int price
        string item_type
    }

    orders {
        int id PK
        int user_id FK
        int total_price
    }

    inventory {
        int id PK
        int user_id FK
        int product_id FK
        int quantity
    }

    user_coupons {
        int id PK
        int user_id FK
        int coupon_id FK
        boolean is_used
    }

    order_items {
        int id PK
        int order_id FK
        int product_id FK
        int quantity
    }
```

## 関係性の要約

- **1対多 (1:N)**: `users` と `orders`、`categories` と `products` など。
- **多対多 (N:M)**: 中間テーブル（`inventory`, `order_items`, `user_coupons` 等）を介して実現されています。
    - ユーザー ↔ 商品 (所持品、配置、庭の状態)
    - ユーザー ↔ クーポン (所持クーポン)
    - 注文 ↔ 商品 (注文明細)
    - 注文 ↔ クーポン (適用クーポン)
