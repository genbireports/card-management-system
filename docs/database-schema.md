# Database Tables

## `cards`
* `card_id` (UUID, PK)
* `card_number` (String, Encrypted)
* `status` (Enum: ACTIVE, FROZEN, BLOCKED)
* `product_tier` (String: BLUE, GOLD, PLATINUM)
* `expiration_date` (Date)