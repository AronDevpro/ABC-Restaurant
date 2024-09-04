document.addEventListener('DOMContentLoaded', function() {

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateProductModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-facility-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');

            fetch('<%= request.getContextPath() %>/admin/products/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('input[name="name"]').value = data.name;
                    updateModel.querySelector('input[name="description"]').value = data.description;
                    updateModel.querySelector('select[name="category"]').value = data.category;
                    updateModel.querySelector('input[name="price"]').value = data.price;
                    updateModel.querySelector('select[name="status"]').value = data.status;

                    // Set current image source if available
                    const imageUrl = data.imagePath ? '<%= request.getContextPath() %>/assets' + data.imagePath.replace(/\\/g, '/') : '';
                    const currentImage = updateModel.querySelector('#currentImage');
                    if (imageUrl) {
                        currentImage.src = imageUrl;
                        currentImage.style.display = 'block';
                    } else {
                        currentImage.style.display = 'none';
                    }

                    updateProductModelInstance.show();
                })
                .catch(error => console.error('Error fetching product details:', error));
        });
    });
});