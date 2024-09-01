document.addEventListener('DOMContentLoaded', function () {
    const deliveryRadios = document.querySelectorAll('input[name="delivery"]');
    const addressContainer = document.getElementById('addressContainer');
    const takeAwayContainer = document.getElementById('takeAwayContainer');

    deliveryRadios.forEach(radio => {
        radio.addEventListener('change', function () {
            if (document.getElementById('takeAway').checked) {
                takeAwayContainer.style.display = 'block';
                addressContainer.style.display = 'none';
            } else if (document.getElementById('deliver').checked) {
                takeAwayContainer.style.display = 'none';
                addressContainer.style.display = 'block';
            }
        });
    });

    const paymentRadios = document.querySelectorAll('input[name="paymentMethod"]');
    const cardPaymentContainer = document.getElementById('cardPaymentContainer');

    paymentRadios.forEach(radio => {
        radio.addEventListener('change', function () {
            if (document.getElementById('card').checked) {
                cardPaymentContainer.style.display = 'block';
            } else if (document.getElementById('cash').checked) {
                cardPaymentContainer.style.display = 'none';
            }
        });
    });
});

function toggleDeliveryFields(method) {
    const takeAwayContainer = document.getElementById('takeAwayContainer');
    const addressContainer = document.getElementById('addressContainer');
    const cardPaymentContainer = document.getElementById('cardPaymentContainer');

    if (method === 'takeAway') {
        takeAwayContainer.style.display = 'block';
        addressContainer.style.display = 'none';
    } else if (method === 'deliver') {
        takeAwayContainer.style.display = 'none';
        addressContainer.style.display = 'block';
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const selectedMethod = document.querySelector('input[name="deliveryMethod"]:checked');
    if (selectedMethod) {
        toggleDeliveryFields(selectedMethod.value);
    }
});