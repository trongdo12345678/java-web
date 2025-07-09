
    
    function updateDeleteButtons() {
        mainImageCount = document.querySelectorAll('input[name="mainImage"]:checked').length;
        totalImageCount = document.querySelectorAll('input[name="mainImage"]').length;
        deleteButtons.forEach(function(button) {
            if (totalImageCount === 1 || (mainImageCount === 1 && button.getAttribute('data-main-status') === '1')) {
                button.classList.add('disabled');
                button.addEventListener('click', function(event) {
                    event.preventDefault();
                });
            } else {
                button.classList.remove('disabled');
                button.removeEventListener('click', function(event) {
                    event.preventDefault();
                });
            }
        });
    }

function updateMainImage(radioButton) {
	  var id = radioButton.getAttribute('data-id');
	  var productId = radioButton.getAttribute('data-product-id');
	  if (!id || !productId) {
	      console.error('Invalid id or productId');
	      return;
	  }
	  if (confirm("Do you want to change this avatar?")) {
	      var data = { id: parseInt(id), productId: parseInt(productId) };

	      $.ajax({
	          url: '/admin/productImage/updateMainImage',
	          type: 'POST',
	          contentType: 'application/json',
	          data: JSON.stringify(data),
	          success: function(response) {
	              alert('Main image updated successfully');

	             
	              radioButton.checked = true;
	          },
	          error: function(xhr, status, error) {
	              console.error('Error updating main image:', xhr.responseText);
	          }
	      });
	  }
	}

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
	
	