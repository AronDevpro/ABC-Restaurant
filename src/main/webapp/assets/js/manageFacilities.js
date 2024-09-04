document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('td[id^="restaurantName-"]').forEach(function(td) {
        const facilityId = td.id.split('-')[1];
        fetchRestaurantName(facilityId, td);
    });

    function fetchRestaurantName(facilityId, tdElement) {
        fetch('<%= request.getContextPath() %>/admin/restaurant/view?id=' + facilityId)
            .then(response => response.json())
            .then(data => {
                tdElement.textContent = data.name;
            })
            .catch(error => {
                console.error('Error fetching restaurant details:', error);
                tdElement.textContent = 'Error';
            });
    }

    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateFacilityModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-facility-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');

            fetch('<%= request.getContextPath() %>/admin/facilities/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('select[name="restaurantId"]').value = data.restaurantId;
                    updateModel.querySelector('input[name="name"]').value = data.name;
                    updateModel.querySelector('input[name="description"]').value = data.description;
                    updateModel.querySelector('select[name="category"]').value = data.category;
                    updateModel.querySelector('select[name="status"]').value = data.status;

                    // Set current image source if available
                    const imageUrl = data.imagePath ? '<%= request.getContextPath() %>/assets' + data.imagePath.replace(/\\/g, '/') : '';
                    const currentImage = updateModel.querySelector('#currentImage');
                    if (imageUrl) {
                        currentImage.src = imageUrl;
                        currentImage.style.display = 'block'; // Show image
                    } else {
                        currentImage.style.display = 'none'; // Hide image if path is empty
                    }

                    updateFacilityModelInstance.show();
                })
                .catch(error => console.error('Error fetching facility details:', error));
        });
    });
});