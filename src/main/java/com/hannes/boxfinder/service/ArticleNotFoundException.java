package com.hannes.boxfinder.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleNotFoundException extends Exception {
    String message;
}
