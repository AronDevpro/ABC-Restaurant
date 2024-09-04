function updateOrderModal(data) {
    document.getElementById('name').textContent = "Name: " + data.firstName + " " + data.lastName;
    document.getElementById('email').textContent = "Email: " + data.email;
    document.getElementById('phoneNumber').textContent = "Contact: " + data.phoneNumber;
    document.getElementById('deliverDateTime').textContent = "Deliver Date/Time: " + data.deliverDate + " " + data.deliverTime;
    document.getElementById('deliverMethod').textContent = "Deliver Method: " + data.deliveryMethod;
    document.getElementById('total').textContent = "Total: " + data.total;

    if (data.streetAddress && data.streetAddress.trim() !== "") {
        document.getElementById('streetAddress').textContent = "Address: " + data.streetAddress + " " + data.zip + " " + data.city;
        document.getElementById('addressArea').style.display = 'block';
    } else {
        document.getElementById('addressArea').style.display = 'none';
    }

    if (data.restaurantSelect && data.restaurantSelect.trim() !== "") {
        document.getElementById('restaurant').textContent = "Restaurant: " + data.restaurantSelect;
        document.getElementById('restaurantArea').style.display = 'block';
    } else {
        document.getElementById('restaurantArea').style.display = 'none';
    }

    const statusButtons = {
        confirm: document.getElementById('confirmButton'),
        cancel: document.getElementById('cancelButton'),
        delivered: document.getElementById('deliveredButton')
    };

    if (data.status === "pending") {
        Object.keys(statusButtons).forEach(key => statusButtons[key].style.display = 'block');
    } else {
        Object.keys(statusButtons).forEach(key => statusButtons[key].style.display = 'none');
    }

    const baseUrl = '<%= request.getContextPath() %>/staff/orders/status?id=' + data.id + '&status=';
    statusButtons.confirm.href = baseUrl + 'confirm';
    statusButtons.cancel.href = baseUrl + 'cancel';
    statusButtons.delivered.href = baseUrl + 'delivered';
}

function updateOrderItemsList(items) {
    const orderItemsList = document.getElementById('orderItemsList');
    orderItemsList.innerHTML = '';

    items.forEach(item => {
        const listItem = document.createElement('li');
        listItem.classList.add('list-group-item');
        listItem.textContent = item.quantity + "x " + item.item;
        orderItemsList.appendChild(listItem);
    });
}

document.querySelectorAll('.view-order-btn').forEach(function (button) {
    button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');

        // Fetch order details
        fetch('<%= request.getContextPath() %>/staff/view?id=' + id)
            .then(response => response.json())
            .then(data => {
                updateOrderModal(data);
            })
            .catch(error => console.error('Error fetching order details:', error));

        // Fetch order items
        fetch('<%= request.getContextPath() %>/customer/view?id=' + id)
            .then(response => response.json())
            .then(data => {
                updateOrderItemsList(data);
            })
            .catch(error => console.error('Error fetching order items:', error));
    });
});