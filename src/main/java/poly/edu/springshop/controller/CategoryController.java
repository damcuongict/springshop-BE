package poly.edu.springshop.controller;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import poly.edu.springshop.domain.Category;
import poly.edu.springshop.dto.CategoryDto;
import poly.edu.springshop.service.CategoryService;
import poly.edu.springshop.service.MapValidationErrorService;
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?>  createCategory(@Valid @RequestBody CategoryDto dto,BindingResult result) {
                ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
                if(responseEntity != null){
                    return responseEntity;
                }

        Category entity = new Category();   // sử dụng kí thuật tạo ra entity

        BeanUtils.copyProperties(dto,entity);  // copy từ dto sang entity
        entity = categoryService.save(entity);  // sử dụng entity sử dụng phương thức lưu vào
        dto.setId(entity.getId());        // cập nhật lại đthông tin của iD cho đối tượng dto

      return new ResponseEntity<>(dto, HttpStatus.CREATED);  // và  tra về thông tin cho phía clien

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?>  updateCategory(@PathVariable("id") Long id,@RequestBody CategoryDto dto) {
        Category entity = new Category();   // sử dụng kí thuật tạo ra entity

        BeanUtils.copyProperties(dto,entity);  // copy từ dto sang entity
        entity = categoryService.update(id,entity);  // sử dụng entity sử dụng phương thức lưu vào
        dto.setId(entity.getId());        // cập nhật lại đthông tin của iD cho đối tượng dto

        return new ResponseEntity<>(dto, HttpStatus.CREATED);  // và  tra về thông tin cho phía clien

    }

    @GetMapping
    public ResponseEntity<?> getCategories(){
      return new  ResponseEntity<>(categoryService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<?> getCategories(
            @PageableDefault(size = 5, sort = "name", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return new ResponseEntity<>(categoryService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}/get")
    public  ResponseEntity<?>getCategories(@PathVariable("id") long id){
            return new ResponseEntity<>  (categoryService.findById(id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoty(@PathVariable("id")Long id){
        categoryService.deleteById(id);
        return new ResponseEntity<>(" Category with id "+ id + " was delete",HttpStatus.OK);
    }

}
