package poly.edu.springshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import poly.edu.springshop.domain.CategoryStatus;

import java.io.Serializable;



@Data
public class CategoryDto implements Serializable {

    private Long id;
    @NotEmpty(message = " Bạn phải nhập Loại")
   private String name;
    private CategoryStatus status;
}