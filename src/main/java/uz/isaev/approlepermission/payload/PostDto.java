package uz.isaev.approlepermission.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @NotBlank
    private String url;
}
