DROP KEYSPACE IF EXISTS jcpenney;

CREATE KEYSPACE jcpenney WITH REPLICATION = {
    'class': 'SimpleStrategy',
    'replication_factor': '1'
};

CREATE TABLE jcpenney.products(
    uniq_id TEXT PRIMARY KEY,
    sku TEXT,
    name_title TEXT,
    description TEXT,
    list_price TEXT,
    sale_price TEXT,
    category TEXT,
    category_tree TEXT,
    average_rating TEXT,
    url TEXT,
    image_urls TEXT,
    brand TEXT,
    total_number_reviews TEXT,
    reviews TEXT
);

COPY jcpenney.products(uniq_id, sku, name_title, description, list_price, sale_price, category, category_tree, average_rating, url, image_urls, brand, total_number_reviews, reviews)
FROM '/cassandra/jcpenney_com-ecommerce_sample.csv'
WITH ESCAPE = '"' AND HEADER = TRUE;

