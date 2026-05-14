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
    coupons ||--o{ user_coupons : "assigned_to"
    orders ||--o{ order_items : "contains"
    orders ||--o{ applied_coupons : "applies"
    user_coupons ||--o{ applied_coupons : "used_as"

    products ||--o{ order_items : "ordered_as"
    products ||--o{ inventory : "stored_as"
    products ||--o{ room_placements : "placed_as"
    products ||--o{ garden_states : "grown_as"
    products |o--o{ achievements : "reward_for"

    categories ||--o{ products : "classifies"

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

## リレーションシップの定義

本システムにおけるテーブル間の関係性を「1対多（1:N）」と「多対多（N:M）」に分けて詳しく解説します。

### 1. 1対多 (1:N)
親テーブルの1レコードに対して、子テーブルの複数のレコードが紐づく形式です。

| 親テーブル (1) | 子テーブル (N) | 説明 |
| :--- | :--- | :--- |
| `users` | `orders` | 1人のユーザーは複数の注文を行うことができます。 |
| `categories` | `products` | 1つのカテゴリには複数の商品が属します。 |
| `orders` | `order_items` | 1つの注文には複数の商品明細が含まれます。 |
| `orders` | `applied_coupons` | 1つの注文に複数のクーポンを適用できます。 |
| `coupons` | `user_coupons` | 1つのクーポン種別は複数のユーザーに配布されます。 |
| `user_coupons` | `applied_coupons` | 1つの所持クーポンは特定の注文に適用されます。 |

### 2. 多対多 (N:M)
中間テーブル（Junction Table）を介して、両方のテーブルが互いに複数のレコードと紐づく形式です。

| テーブル A | テーブル B | 中間テーブル | 説明 |
| :--- | :--- | :--- | :--- |
| `users` | `products` | `inventory` | ユーザーは複数の商品を所持し、商品は複数のユーザーに所持されます。 |
| `users` | `products` | `room_placements` | ユーザーは複数の家具を配置し、家具は複数のユーザーの部屋に配置されます。 |
| `users` | `products` | `garden_states` | ユーザーは複数の種を植え、種は複数のユーザーの庭で成長します。 |
| `users` | `coupons` | `user_coupons` | ユーザーは複数のクーポンを持ち、クーポンは複数のユーザーに保持されます。 |
| `orders` | `products` | `order_items` | 1つの注文に複数の商品が入り、1つの商品は複数の注文に含まれます。 |
| `orders` | `user_coupons` | `applied_coupons` | 1つの注文に複数の所持クーポンが使われ、1つの所持クーポンは複数の注文に使われる（分割利用等想定）。 |
