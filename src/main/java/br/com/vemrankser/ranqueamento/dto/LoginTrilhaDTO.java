package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginTrilhaDTO {
    private List<Integer> idTrilha;
    private String login;
}
