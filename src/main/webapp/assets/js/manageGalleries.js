document.addEventListener('DOMContentLoaded', function() {

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateGalleryModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-gallery-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');

            fetch('<%= request.getContextPath() %>/admin/galleries/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('input[name="title"]').value = data.title;
                    updateModel.querySelector('input[name="description"]').value = data.description;
                    updateModel.querySelector('input[name="category"]').value = data.category;
                    updateModel.querySelector('select[name="status"]').value = data.status;

                    // Set current image source if available
                    const imageUrl = data.imagePath ? '<%= request.getContextPath() %>/assets' + data.imagePath.replace(/\\/g, '/') : ''; // Convert backslashes to forward slashes
                    const currentImage = updateModel.querySelector('#currentImage');
                    if (imageUrl) {
                        currentImage.src = imageUrl;
                        currentImage.style.display = 'block'; // Show image
                    } else {
                        currentImage.style.display = 'none'; // Hide image if path is empty
                    }

                    updateGalleryModelInstance.show();
                })
                .catch(error => console.error('Error fetching gallery details:', error));
        });
    });
});