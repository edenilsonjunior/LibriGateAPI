package br.com.librigate.model.dto;

import java.util.List;

public record RestockDTO(
        Long id,
        double price,
        String restockDate,
        List<RestockBookDTO> books

) { }
