document.querySelectorAll('.view-order-btn').forEach(function (button) {
    button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');
        const orderItemsList = document.getElementById('orderItemsList');

        orderItemsList.innerHTML = '';

        // Fetch order items and update the modal content
        fetch('<%= request.getContextPath() %>/customer/view?id=' + id)
            .then(response => response.json())
            .then(data => {
                data.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.classList.add('list-group-item');
                    listItem.textContent = item.item + " - Quantity: " + item.quantity;
                    orderItemsList.appendChild(listItem);
                });
            })
            .catch(error => console.error('Error fetching order item details:', error));
    });
});