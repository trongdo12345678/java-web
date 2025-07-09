function change(event) {
	  var item = event.target;
	  var id = item.getAttribute("data-id");
	  var title = item.getAttribute("data-title");
	  var price = item.getAttribute("data-price");
	  var _value = item.value;
	  if (item.checked == true) {
	    _value = 1;
	  } else {
	    _value = 0;
	  }
	  item.value = _value;
	  var data = { id: parseInt(id), title: title,
			  price: parseFloat(price), status: _value };
	  var dataReturn = "";
	  console.log(data);
	  
	  $.ajax({
		    url: "/admin/product/active",
		    type: 'POST',
		    dataType: "json",
		    contentType: "application/json; charset=utf-8",
		    data: JSON.stringify(data),
		    success: function() {
		        alert("update success");
		    }
		});
	}
	window.onload=()=>{
	    var cbNo=document.querySelector("#status");
	    cbNo.addEventListener("change",(event)=>{
	        var item_target=event.currentTarget;
	        if (item_target.checked) {
	            item_target.value="1";
	        } else {
	            item_target.value="0";
	        }
	    });
	};
	document.addEventListener("DOMContentLoaded", function () {
	    const imageInput = document.getElementById("images");
	    const imagePreviewsContainer = document.getElementById("imagePreviews");

	    imageInput.addEventListener("change", function (event) {
	        const files = event.target.files; // Lấy danh sách file

	        // Xóa ảnh cũ trước khi hiển thị ảnh mới
	        imagePreviewsContainer.innerHTML = "";

	        // Giới hạn tối đa 10 hình
	        const maxFiles = Math.min(files.length, 10);

	        // Xử lý từng file
	        Array.from(files).slice(0, maxFiles).forEach((file) => {
	            if (!file.type.startsWith("image/")) {
	                alert("Please select only image files.");
	                return;
	            }

	            const fileReader = new FileReader();

	            fileReader.onload = function (e) {
	                // Tạo div cho xem trước ảnh
	                const previewDiv = document.createElement("div");
	                previewDiv.className = "image-preview";

	                // Tạo thẻ img để hiển thị ảnh
	                const img = document.createElement("img");
	                img.src = e.target.result; // Cập nhật src khi file đã được đọc
	                img.style.maxWidth = "100px"; // Giới hạn kích thước ảnh

	                // Tạo nút xóa
	                const removeBtn = document.createElement("button");
	                removeBtn.className = "remove-btn";
	                removeBtn.textContent = "×";
	                removeBtn.addEventListener("click", function () {
	                    previewDiv.remove(); // Xóa xem trước khi nhấn nút
	                });

	                // Thêm img và nút xóa vào previewDiv
	                previewDiv.appendChild(img);
	                previewDiv.appendChild(removeBtn);

	                // Thêm previewDiv vào container
	                imagePreviewsContainer.appendChild(previewDiv);
	            };

	            fileReader.readAsDataURL(file); // Đọc file dưới dạng Data URL
	        });
	    });
	});
