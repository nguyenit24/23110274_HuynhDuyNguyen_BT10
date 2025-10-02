package vn.iot.enity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CategoryId;

    @NotBlank(message = "Category name must not be blank")
    @Column(name = "category_name", length = 200, nullable = false)
	private String name;

	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
	private List<ProductEntity> products;
	
}
