package com.serverside.dt.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeDTO {
    private Integer accountTypeId;
    private String typeName;
}
