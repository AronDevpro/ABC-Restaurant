document.addEventListener('DOMContentLoaded', function() {
    //   update model
    const updateModel = document.getElementById('updateModel');
    const updateOfferModelInstance = bootstrap.Modal.getOrCreateInstance(updateModel);

    document.querySelectorAll('.view-offer-btn').forEach(function (button) {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');

            fetch('<%= request.getContextPath() %>/admin/offers/view?id=' + id)
                .then(response => response.json())
                .then(data => {
                    updateModel.querySelector('input[name="id"]').value = data.id;
                    updateModel.querySelector('input[name="offerName"]').value = data.offerName;
                    updateModel.querySelector('input[name="promoCode"]').value = data.promoCode;
                    updateModel.querySelector('input[name="discountPercentage"]').value = data.discountPercentage;
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

                    updateOfferModelInstance.show();
                })
                .catch(error => console.error('Error fetching offer details:', error));
        });
    });
});