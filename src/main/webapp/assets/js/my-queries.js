document.querySelectorAll('.view-order-btn').forEach(function (button) {
    button.addEventListener('click', function () {
        const id = this.getAttribute('data-id');

        // Fetch order items and update the modal content
        fetch('<%= request.getContextPath() %>/customer/view-query?id=' + id)
            .then(response => response.json())
            .then(data => {
                document.getElementById('subject').textContent = data.subject;
                document.getElementById('description').textContent = data.description;
                document.getElementById('response').textContent = data.response;
            })
            .catch(error => console.error('Error fetching order item details:', error));
    });
});