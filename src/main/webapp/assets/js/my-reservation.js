document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('td[id^="restaurantName-"]').forEach(function (td) {
        const id = td.id.split('-')[1];
        fetchRestaurantName(id, td);
    });

    function fetchRestaurantName(id, tdElement) {
        fetch('<%= request.getContextPath() %>/customer/get-restaurant?id=' + id)
            .then(response => response.json())
            .then(data => {
                tdElement.textContent = data.name;
            })
            .catch(error => {
                console.error('Error fetching restaurant details:', error);
                tdElement.textContent = 'Error';
            });
    }
})