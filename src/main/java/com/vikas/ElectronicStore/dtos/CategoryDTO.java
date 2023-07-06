package com.vikas.ElectronicStore.dtos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {


    private String categoryId;

    @NotBlank
    @Min(value = 4, message = "title must be of minimum 4 characters !!")
    private String title;

    @NotBlank(message = "Description is required !!")
    private String description;
    private String coverImage;
    //other attributes if you have
}
