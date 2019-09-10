package com.griddynamics.ngolovin.cwai.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    private String uniqId;

    private String sku;

    private String nameTitle;

    private String description;

    private String listPrice;

    private String salePrice;

    private String category;

    private String categoryTree;

    private String averageRating;

    private String url;

    private String imageUrls;

    private String brand;

    private String totalNumberReviews;

    private String reviews;
}
