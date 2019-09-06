package com.griddynamics.ngolovin.cwai.entities;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("products")
@Data
public class Product {
    @PrimaryKey("uniq_id")
    private String uniqId;

    @Column("sku")
    private String sku;

    @Column("name_title")
    private String nameTitle;

    @Column("description")
    private String description;

    @Column("list_price")
    private String listPrice;

    @Column("sale_price")
    private String salePrice;

    @Column("category")
    private String category;

    @Column("category_tree")
    private String categoryTree;

    @Column("average_rating")
    private String averageRating;

    @Column("url")
    private String url;

    @Column("image_urls")
    private String imageUrls;

    @Column("brand")
    private String brand;

    @Column("total_number_reviews")
    private String totalNumberReviews;

    @Column("reviews")
    private String reviews;
}
