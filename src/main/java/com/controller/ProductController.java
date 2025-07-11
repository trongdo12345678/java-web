package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.models.Product;
import com.models.Product_Image;
import com.repositories.ProductRepository;
import com.utils.FileUtils;
import com.utils.Views;

@Controller
@RequestMapping("/")
public class ProductController {
	@Autowired
	ProductRepository rep;

	@GetMapping
	public String home() {
		return "redirect:/admin/product/index";
	}

	@GetMapping("admin/product/index")
	public String Index(Model model) {
		try {
			List<Product> products = rep.finAllPro();
			List<Product_Image> lsImage = rep.finAllProI();

			System.out.println("Số lượng sản phẩm: " + products.size());
			System.out.println("Số lượng ảnh: " + lsImage.size());

			for (Product product : products) {
				boolean hasMainImage = false;
				for (Product_Image image : lsImage) {
					if (image.getId_pro() == product.getId() && image.getMain_status() == 1) {
						hasMainImage = true;
						break;
					}
				}
				if (!hasMainImage) {
					Product_Image noImage = new Product_Image();
					noImage.setId_pro(product.getId());
					noImage.setFile_name("noimage.jpg");
					noImage.setMain_status(1);
					lsImage.add(noImage);
				}
			}

			model.addAttribute("products", products);
			model.addAttribute("lsImage", lsImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Views.PRODUCT_INDEX;
	}

	@GetMapping("admin/product/add")
	public String add(Model model) {
		try {
			Product pro = new Product();
			model.addAttribute("new_item", pro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Views.PRODUCT_ADD;
	}

	@PostMapping("/addPro")
	public String addPro(@ModelAttribute Product new_item) {
		try {
			rep.addPro(new_item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:admin/product/index";
	}

	@PostMapping("admin/product/active")
	public ResponseEntity<Product> chageStatusAjax(@RequestBody Product data) {
		try {
			rep.UpdateStatus(data.getId(), data.getStatus());
			return ResponseEntity.ok(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("admin/productImage/viewImage/{id}")
	public String viewImage(@PathVariable("id") int id, Model model) {
		try {
			List<Product_Image> productImages = rep.findImagesByProductId(id);
			List<Product> product = rep.findImagesBy(id);
			if (!product.isEmpty()) {
				model.addAttribute("productTitle", product.get(0).getTitle());
			}
			model.addAttribute("productImages", productImages);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Views.PRODUCT_IMAGE_VIEW;
	}

	@GetMapping("/admin/productImage/add")
	public String addImage(@RequestParam("idproI") String idproI, Model model) {
		try {
			int idproo = Integer.parseInt(idproI);
			model.addAttribute("up_item", new Product_Image());
			model.addAttribute("id_pro", idproo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Views.PRODUCT_IMAGE_ADD;
	}

	@PostMapping("/addProI")
	public String addProI(@RequestParam("id_pro") int idPro, @RequestParam("main_status") int MStatus,
			@RequestParam("file_name") MultipartFile images) {
		try {
			Product_Image proI = new Product_Image();
			proI.setId_pro(idPro);
			proI.setMain_status(MStatus);
			proI.setFile_name(FileUtils.uploadFileImage(images, "uploads"));
			rep.addProI(proI);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:admin/product/index";
	}

	@PostMapping("/admin/productImage/updateMainImage")
	public ResponseEntity<String> updateMainImage(@RequestBody Map<String, Object> payload) {
		try {
			int id = ((Number) payload.get("id")).intValue();
			int productId = ((Number) payload.get("productId")).intValue();

			rep.updateAllImagesToSecondary(productId);
			rep.updateImageToMain(id);
			return ResponseEntity.ok("success");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating main image");
		}
	}

	@GetMapping("/admin/productImage/deleteProI")
	public String rm_proI(@RequestParam("id") int id) {
		try {
			String result = rep.deleteProI(id);
			if ("success".equals(result)) {
				return "redirect:/admin/productImage/viewImage/" + id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/productImage/error";
	}

}
