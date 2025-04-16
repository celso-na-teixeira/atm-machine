package com.nas.atm_machine.atm;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawalRequest {

    @Nonnull
    private String amount;
}
