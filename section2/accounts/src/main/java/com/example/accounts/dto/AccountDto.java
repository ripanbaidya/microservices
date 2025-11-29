package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Note: While working with dto's in spring boot, make sure do not write any business logic inside dto's.
// Serialization logic you can write if required. We can use "record" for dto's instead of class.
// for "class" based dto's,
// required - private fields, getters, setters, no-args constructor(jackson requirement), validation annotation
// option   - all-args constructor(convenience), equals/hashcode(collection/ maps), toString(logging/ debugging)
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
@Getter @Setter @NoArgsConstructor
public class AccountDto {

    @Schema(
            description = "Account Number of the Bank account", example = "3454433243"
    )
    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Account number must be 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Account type of the Bank account", examples = {"Savings", "Current"}
    )
    @NotBlank(message = "Account type cannot be empty")
    private String accountType;

    @Schema(
            description = "Branch address of the Bank account", example = "123, Main Street, Cityville"
    )
    @NotBlank(message = "Branch address cannot be empty")
    private String branchAddress;
}
