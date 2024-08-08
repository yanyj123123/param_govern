package com.ghrk.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menu;

    private String auth;

}
