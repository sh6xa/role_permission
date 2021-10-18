package uz.isaev.approlepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    @NotNull
    private String fullName;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull(message = "Lavozim bo'sh bo'lmasligi kerak!")
    private Long roleId;

    @NotNull
    private boolean enabled;
}
