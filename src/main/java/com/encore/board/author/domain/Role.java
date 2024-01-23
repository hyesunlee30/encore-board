package com.encore.board.author.domain;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN","관리자"),USER("USER","관리자");

    @Getter
    private String name;
    private String label;


    Role(String name, String label) {
        this.name = name;
        this.label = label;
    }

}
