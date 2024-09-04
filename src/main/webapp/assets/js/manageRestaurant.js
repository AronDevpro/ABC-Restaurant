document.addEventListener('DOMContentLoaded', function() {

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-user-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');

            fetch('<%= request.getContextPath() %>/admin/restaurant/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('input[name="name"]').value = data.name;
                    updateModel.querySelector('input[name="description"]').value = data.description;
                    updateModel.querySelector('input[name="openTime"]').value = data.openTime;
                    updateModel.querySelector('input[name="closeTime"]').value = data.closeTime;
                    updateModel.querySelector('input[name="address"]').value = data.address || '';
                    updateModel.querySelector('input[name="phoneNumber"]').value = data.phoneNumber || '';
                    updateModel.querySelector('input[name="capacity"]').value = data.capacity || '';

                    // Set current image source if available
                    const imageUrl = data.image ? '<%= request.getContextPath() %>/assets' + data.image.replace(/\\/g, '/') : '';
                    const currentImage = updateModel.querySelector('#currentImage');
                    if (imageUrl) {
                        currentImage.src = imageUrl;
                        currentImage.style.display = 'block';
                    } else {
                        currentImage.style.display = 'none';
                    }

                    updateModelInstance.show();
                })
                .catch(error => console.error('Error fetching restaurant details:', error));
        });
    });

});