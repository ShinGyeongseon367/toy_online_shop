package org.toy.jpashop.web;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookForm {

    private long id;

    @NotEmpty(message = "이름 필수")
    private String name;

    @NotEmpty(message = "가격 필수")
    private int price;

    @NotEmpty(message = "재고 필수")
    private int stockQuantity;

    private String author;
    private String isbn;
}
