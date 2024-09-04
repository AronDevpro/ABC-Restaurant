document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('td[id^="customerName-"]').forEach(function (td) {
        const id = td.id.split('-')[1];
        fetchCustomerName(id, td);
    });

    function fetchCustomerName(id, tdElement) {
        fetch('<%= request.getContextPath() %>/admin/users/view?id=' + id)
            .then(response => response.json())
            .then(data => {
                tdElement.textContent = data.firstName;
            })
            .catch(error => {
                console.error('Error fetching customer details:', error);
                tdElement.textContent = 'Error';
            });
    }
})

document.querySelectorAll('.view-order-btn').forEach(function (button) {
    button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');
        document.getElementById('queryId').value = id;

        // Fetch order items and update the modal content
        fetch('<%= request.getContextPath() %>/customer/view-query?id=' + id)
            .then(response => response.json())
            .then(data => {
                document.getElementById('subject').textContent = data.subject;
                document.getElementById('description').textContent = data.description;
                if (data.response && data.response.trim() !== "") {
                    document.getElementById('responseText').textContent = data.response;
                    document.getElementById('responseTextArea').style.display = 'none';
                    document.getElementById('replyButton').style.display = 'none';
                } else {
                    document.getElementById('responseTextArea').style.display = 'block';
                    document.getElementById('replyButton').style.display = 'inline-block';
                }
            })
            .catch(error => console.error('Error fetching order item details:', error));
    });
});