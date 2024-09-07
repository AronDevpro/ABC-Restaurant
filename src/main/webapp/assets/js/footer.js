// update footer settings
fetch('<%= request.getContextPath() %>/settings')
    .then(response => response.json())
    .then(data => {

        // Update logo image
        const footerLogo = document.getElementById('footerLogo');
        if (data.logoPath) {
            const logoUrl = '<%= request.getContextPath() %>/assets' + data.logoPath.replace(/\\/g, '/');
            footerLogo.src = logoUrl;
            footerLogo.style.display = 'block'; // Show image
        } else {
            footerLogo.style.display = 'none'; // Hide image if path is empty
        }

        // Update address
        const footerAddress = document.getElementById('footerAddress');
        if (data.siteStreetAddress || data.siteZip || data.siteCity) {
            const addressParts = [data.siteStreetAddress, data.siteZip, data.siteCity].filter(part => part).join(', ');
            footerAddress.textContent = addressParts || 'No address available';
        } else {
            footerAddress.textContent = 'No address available';
        }
        // Update email
        const footerEmail = document.getElementById('footerEmail');
        footerEmail.textContent = data.siteEmail ? data.siteEmail : '';

        // Update desc
        const footerDESC = document.getElementById('footerDesc');
        footerDESC.textContent = data.description ? data.description : '';
    })
    .catch(error => console.error('Error fetching settings:', error));