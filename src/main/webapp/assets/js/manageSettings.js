document.addEventListener('DOMContentLoaded', function () {
    fetch('<%= request.getContextPath() %>/admin/settings/view')
        .then(response => response.json())
        .then(data => {
            document.querySelector('input[name="siteName"]').value = data.siteName || '';
            document.querySelector('input[name="description"]').value = data.description || '';
            document.querySelector('input[name="siteEmail"]').value = data.siteEmail || '';
            document.querySelector('input[name="siteStreetAddress"]').value = data.siteStreetAddress || '';
            document.querySelector('input[name="siteZip"]').value = data.siteZip || '';
            document.querySelector('input[name="siteCity"]').value = data.siteCity || '';
            document.querySelector('input[name="serverEmail"]').value = data.serverEmail || '';
            document.querySelector('input[name="serverPassword"]').value = data.serverPassword || '';

            // Set current image source if available
            const imageUrl = data.logoPath ? '<%= request.getContextPath() %>/assets' + data.logoPath.replace(/\\/g, '/') : '';
            const currentImage = document.querySelector('#currentImage');

            if (imageUrl) {
                currentImage.src = imageUrl;
                currentImage.style.display = 'block';
            } else {
                currentImage.style.display = 'none';
            }
        })
        .catch(error => console.error('Error fetching setting details:', error));
});