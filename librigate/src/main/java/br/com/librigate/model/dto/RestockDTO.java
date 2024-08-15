package br.com.librigate.model.dto;

import java.util.List;

public record RestockDTO(
        Long id,
        String employeeCpf,
        String restockDate,
        List<RestockBookDTO> books
) { }
