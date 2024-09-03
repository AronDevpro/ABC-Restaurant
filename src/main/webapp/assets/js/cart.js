// Increment
function incrementQuantity(productId) {
    const quantityElement = document.getElementById(productId);
    if (quantityElement) {
        let quantity = parseInt(quantityElement.innerText);
        quantity += 1;
        quantityElement.innerText = quantity;
    }
}

// Decrement
function decrementQuantity(productId) {
    const quantityElement = document.getElementById(productId);
    let quantity = parseInt(quantityElement.innerText);
    if (quantity > 0) {
        quantity -= 1;
        quantityElement.innerText = quantity;
    }
}

// Add the item to the cart
function addToCart(productId, name, price) {
    const quantity = document.getElementById(productId).innerText;

    if (parseInt(quantity) > 0) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "addToCart", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                document.getElementById(productId).innerText = 0;
                updateCartDropdown();
            }
        };
        xhr.send(`productId=\${productId}&quantity=\${quantity}&name=\${name}&price=\${price}`);
    } else {
        alert("Please select a quantity greater than 0.");
    }
}

// dropdown
function updateCartDropdown() {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/menu/cart", true);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const cartItems = JSON.parse(xhr.responseText);
            let cartContent = '';

            if (Object.keys(cartItems).length === 0) {
                cartContent = '<li class="dropdown-item">Your cart is empty</li>';
                document.getElementById('cartDropdown').innerHTML = cartContent;
            } else {
                const fetchPromises = [];

                for (const [productId, cart] of Object.entries(cartItems)) {
                    const { quantity } = cart;
                    const promise = fetchProductDetails(productId).then(data => {
                        cartContent += `
                            <li class="dropdown-item d-flex justify-content-between">
                                <span> <i class="fa-solid fa-trash" onclick="removeFromCart(\${productId})"></i> x\${quantity} \${data.name}</span>
                                <span>Rs.\${data.price}</span>
                            </li>`;
                    });
                    fetchPromises.push(promise);
                }

                Promise.all(fetchPromises).then(() => {
                    cartContent += '<li class="dropdown-divider"></li>';
                    cartContent += '<li class="dropdown-item"><a href="/checkout">View Cart</a></li>';
                    document.getElementById('cartDropdown').innerHTML = cartContent;
                }).catch(error => {
                    console.error('Error fetching product details:', error);
                    document.getElementById('cartDropdown').innerHTML = '<li class="dropdown-item">Error loading cart</li>';
                });
            }
        }
    };
    xhr.send();
}

// get product detail by id
function fetchProductDetails(productId) {
    return fetch(`/admin/products/view?id=\${productId}`)
        .then(response => response.json())
        .catch(error => {
            console.error('Error fetching product details:', error);
            return { name: 'Unknown', price: 'N/A' };
        });
}

document.addEventListener("DOMContentLoaded", updateCartDropdown);

// remove from cart
function removeFromCart(productId) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/menu/removeFromCart", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            updateCartDropdown();
        }
    };
    xhr.send(`productId=\${productId}`);
}