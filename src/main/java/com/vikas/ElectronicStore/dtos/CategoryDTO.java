package com.vikas.ElectronicStore.dtos;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {


    private String categoryId;

    @NotBlank
    @Size(min = 4, max = 20, message = "Invalid Title!!")
    private String title;

    @NotBlank(message = "Description is required !!")
    private String description;
    private String coverImage;
    //other attributes if you have
}
